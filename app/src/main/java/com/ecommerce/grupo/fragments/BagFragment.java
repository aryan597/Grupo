package com.ecommerce.grupo.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.Adapter.AllProductsAdapter;
import com.ecommerce.grupo.Adapter.CartAdapter;
import com.ecommerce.grupo.CheckoutGrupoActivity;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.Model.CartItems;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.GetCartItems;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BagFragment extends Fragment {
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    ArrayList<CartItems> cartItemsArrayList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView itemsCount;
    APIInterface apiInterface;
    private ConstraintLayout layoutRemove;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bag, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
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
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        itemsCount = view.findViewById(R.id.items_count);
      /*  wishListImage = view.findViewById(R.id.wishlist_image);
        wishListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.saved);
                Fragment fragment = new OrdersFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/
        apiInterface = APIClient.getClient().create(APIInterface.class);
        layoutRemove = view.findViewById(R.id.layout__remove);
        SharedPreferences sp = getContext().getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        cartRecyclerView =view.findViewById(R.id.cart_recycler_view);
        Call<GetCartItems> getCartItemsCall = apiInterface.getCartItems(id);
        getCartItemsCall.enqueue(new Callback<GetCartItems>() {
            @Override
            public void onResponse(Call<GetCartItems> call, Response<GetCartItems> response) {
                if (response.isSuccessful()){
                    cartItemsArrayList = response.body().cart.Product;
                    if (cartItemsArrayList.isEmpty()){
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        itemsCount.setVisibility(View.GONE);
                        layoutRemove.setVisibility(View.VISIBLE);
                        cartRecyclerView.setVisibility(View.GONE);
                    }else {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        layoutRemove.setVisibility(View.GONE);
                        cartRecyclerView.setVisibility(View.VISIBLE);
                        itemsCount.setVisibility(View.VISIBLE);
                        itemsCount.setText(cartItemsArrayList.size()+" items");
                        cartAdapter = new CartAdapter(cartItemsArrayList, getContext(),id);
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                        cartRecyclerView.setLayoutManager(manager);
                        cartRecyclerView.setAdapter(cartAdapter);
                    }
                }else{
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCartItems> call, Throwable t) {

            }
        });
        /*checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* Intent intent = new Intent(getContext(), CheckoutGrupoActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)cartItemsArrayList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);*//*
                Toast.makeText(getContext(), "Multiple orders cannot be placed yet. It will be available soon.", Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;
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