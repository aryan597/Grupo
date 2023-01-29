package com.ecommerce.grupo.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.Adapter.CategoryAdapter;
import com.ecommerce.grupo.Adapter.ParentCategoryAdapter;
import com.ecommerce.grupo.CategoriesActivity;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Category;
import com.ecommerce.grupo.pojo.MultipleResource;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {
    RecyclerView parentCategoryRecyclerView;
    ArrayList<ParentCategoryModel> parentCategoryModelArrayList;
    ParentCategoryAdapter parentCategoryRecyclerViewAdapter;
    APIInterface apiInterface;
    ImageView bagImage,amim;
    ConstraintLayout layoutRemove;
    ArrayList<CategoryModel> categoryArrayList;
    CategoryAdapter categoryAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
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
        bagImage = view.findViewById(R.id.imageView3);
        bagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.bag);
                Fragment fragment = new BagFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        apiInterface = APIClient.getClient().create(APIInterface.class);
        parentCategoryModelArrayList = new ArrayList<>();
        parentCategoryRecyclerView = view.findViewById(R.id.parent_category_recycler_view);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        parentCategoryRecyclerView.setNestedScrollingEnabled(false);
        layoutRemove = view.findViewById(R.id.layout_remove);

        Call<Category> call1 = apiInterface.getCategory();
        call1.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                categoryArrayList = response.body().categoryModels;
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                parentCategoryRecyclerView.setVisibility(View.VISIBLE);
                amim = view.findViewById(R.id.amim);
                amim.setVisibility(View.VISIBLE);
                for (int i = 0; i < categoryArrayList.size(); i++) {
                    categoryAdapter = new CategoryAdapter(categoryArrayList, getContext());
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    parentCategoryRecyclerView.setLayoutManager(manager);
                    parentCategoryRecyclerView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
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