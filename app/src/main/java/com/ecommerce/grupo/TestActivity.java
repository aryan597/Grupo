package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ecommerce.grupo.Adapter.AllProductsAdapter;
import com.ecommerce.grupo.Adapter.OrdersAdapter;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.Model.Orders;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.GetOrder;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    RecyclerView allItemsRecyclerView,ordersRecyclerView;
    SearchView searchView;
    AllProductsAdapter allProductsAdapter;
    ArrayList<AllProducts> allItemsArrayList;
    APIInterface apiInterface;
    SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout layoutRemove;
    ArrayList<Orders> ordersArrayList;
    OrdersAdapter ordersAdapter;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        shimmerFrameLayout = findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int userId = sp.getInt("id",0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AllProductsPojo> call1 = apiInterface.getAllProducts();
        call1.enqueue(new Callback<AllProductsPojo>() {
            @Override
            public void onResponse(Call<AllProductsPojo> call, Response<AllProductsPojo> response) {
                allItemsArrayList = response.body().data;
                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < allItemsArrayList.size(); i++) {
                    allProductsAdapter = new AllProductsAdapter(allItemsArrayList, TestActivity.this,0);

                    // below line is to set layout manager for our recycler view.
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

                    // setting layout manager for our recycler view.
                    allItemsRecyclerView.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    allItemsRecyclerView.setAdapter(allProductsAdapter);
                }
            }

            @Override
            public void onFailure(Call<AllProductsPojo> call, Throwable t) {

            }
        });
        searchView = findViewById(R.id.editText2);
        searchView.setQueryHint("Search by name or id");
        searchView.setIconified(false);
        allItemsRecyclerView = findViewById(R.id.search_recycler_view);
        layoutRemove = findViewById(R.id.layouter_remover);

        ordersRecyclerView = findViewById(R.id.orders_recycler_view);
        Call<GetOrder> getOrderCall = apiInterface.getOrdersById(userId);
        getOrderCall.enqueue(new Callback<GetOrder>() {
            @Override
            public void onResponse(Call<GetOrder> call, Response<GetOrder> response) {
                if (response.isSuccessful()){
                    ordersArrayList = response.body().ordersArrayList;
                    ordersAdapter = new OrdersAdapter(ordersArrayList,TestActivity.this);
                    if (ordersArrayList.isEmpty()){
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        layoutRemove.setVisibility(View.VISIBLE);
                        allItemsRecyclerView.setVisibility(View.GONE);
                    }else{
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        ordersRecyclerView.setVisibility(View.VISIBLE);
                        layoutRemove.setVisibility(View.GONE);
                        allItemsRecyclerView.setVisibility(View.GONE);
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                        ordersRecyclerView.setLayoutManager(manager);
                        ordersRecyclerView.setAdapter(ordersAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetOrder> call, Throwable t) {
                Toast.makeText(TestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /////////////////Search View
        searchView.clearFocus();
        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                searchView.setIconified(false);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 0) {
                    // Search
                    ordersRecyclerView.setVisibility(View.GONE);
                    layoutRemove.setVisibility(View.GONE);
                    allItemsRecyclerView.setVisibility(View.VISIBLE);
                    filterList(s);
                } else {
                    // Do something when there's no input
                    hideKeyboard(TestActivity.this);
                    Call<GetOrder> getOrderCall = apiInterface.getOrdersById(userId);
                    getOrderCall.enqueue(new Callback<GetOrder>() {
                        @Override
                        public void onResponse(Call<GetOrder> call, Response<GetOrder> response) {
                            if (response.isSuccessful()){
                                ordersArrayList = response.body().ordersArrayList;
                                ordersAdapter = new OrdersAdapter(ordersArrayList,TestActivity.this);
                                if (ordersArrayList.isEmpty()){
                                    layoutRemove.setVisibility(View.VISIBLE);
                                    allItemsRecyclerView.setVisibility(View.GONE);
                                }else{
                                    layoutRemove.setVisibility(View.GONE);
                                    allItemsRecyclerView.setVisibility(View.GONE);
                                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                                    ordersRecyclerView.setLayoutManager(manager);
                                    ordersRecyclerView.setAdapter(ordersAdapter);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<GetOrder> call, Throwable t) {
                            Toast.makeText(TestActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    allItemsRecyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });
        /////////////////Search View
    }
    private void filterList(String s) {
        ArrayList<AllProducts> filteredList = new ArrayList<>();
        for (AllProducts allItems:allItemsArrayList){
            if (allItems.getTitle().toLowerCase().contains(s.toLowerCase()) ){
                filteredList.add(allItems);
            }
        }
        if (filteredList.isEmpty()){
            allItemsRecyclerView.setVisibility(View.GONE);
            Toast.makeText(TestActivity.this, "Item Not Available..!", Toast.LENGTH_SHORT).show();
        }else{
            allProductsAdapter.setFilteredList(filteredList);
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TestActivity.this,HomeActivity.class));
        finish();
    }
}