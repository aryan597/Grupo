package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.Adapter.AllProductsAdapter;
import com.ecommerce.grupo.Adapter.CategoryAdapter;
import com.ecommerce.grupo.Adapter.ParentCategoryAdapter;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.Category;
import com.ecommerce.grupo.pojo.MultipleResource;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {
    int id;
    String title;
    TextView ttittle;
    ImageView back;
    APIInterface apiInterface;
    RecyclerView allItemsRecyclerView;
    SearchView searchView;
    AllProductsAdapter allProductsAdapter;
    ArrayList<AllProducts> allItemsArrayList;
    ArrayList<CategoryModel> categoryArrayList;
    CategoryAdapter categoryAdapter;
    RecyclerView categoryRecyclerView;
    ConstraintLayout layout_remove;
    ArrayList<ParentCategoryModel> parentCategoryModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        apiInterface = APIClient.getClient().create(APIInterface.class);
        back = findViewById(R.id.imageView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        /////////////////Search View
        searchView = findViewById(R.id.editText2);
        searchView.setQueryHint("Search by name or id");
        searchView.setIconified(false);
        allItemsRecyclerView = findViewById(R.id.allItemsRecyclerView);
        layout_remove = findViewById(R.id.container);
        Call<AllProductsPojo> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<AllProductsPojo>() {
            @Override
            public void onResponse(Call<AllProductsPojo> call, Response<AllProductsPojo> response) {
                allItemsArrayList = response.body().data;
                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < allItemsArrayList.size(); i++) {
                    allProductsAdapter = new AllProductsAdapter(allItemsArrayList,CategoriesActivity.this);

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
                    layout_remove.setVisibility(View.GONE);
                    allItemsRecyclerView.setVisibility(View.VISIBLE);
                    filterList(s);
                } else {
                    // Do something when there's no input
                    hideKeyboard(CategoriesActivity.this);
                    layout_remove.setVisibility(View.VISIBLE);
                    allItemsRecyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });
        /////////////////Search View
        id = getIntent().getIntExtra("id",0);
        title = getIntent().getStringExtra("title");
        ttittle = findViewById(R.id.imageView4);
        ttittle.setText(title);
        categoryRecyclerView = findViewById(R.id.category_recycler_view);
        Call<Category> call1 = apiInterface.doGetCategory(id);
        call1.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                categoryArrayList = response.body().categoryModels;
                int i1=0;
                for (int i = 0; i < categoryArrayList.size(); i++) {
                    i1 = i;
                    categoryAdapter = new CategoryAdapter(categoryArrayList, CategoriesActivity.this,id);
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    categoryRecyclerView.setLayoutManager(manager);
                    categoryRecyclerView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
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
            Toast.makeText(CategoriesActivity.this, "Item Not Available..!", Toast.LENGTH_SHORT).show();
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
}