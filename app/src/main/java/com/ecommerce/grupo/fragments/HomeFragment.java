package com.ecommerce.grupo.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.Adapter.AllProductsAdapter;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.Model.SliderItem;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.RequirementsActivity;
import com.ecommerce.grupo.SearchActivity;
import com.ecommerce.grupo.ShippingAddressActivity;
import com.ecommerce.grupo.Adapter.SliderAdapterExample;
import com.ecommerce.grupo.Adapter.ParentCategoryAdapterHome;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.Category;
import com.ecommerce.grupo.pojo.MultipleResource;
import com.ecommerce.grupo.pojo.UserAddress;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    ConstraintLayout viewAll,addAddress;
    RecyclerView parentCategoryRecyclerView,productRecyclerView,trendingRecyclerView;
    TextView searchView;
    ArrayList<ParentCategoryModel> parentCategoryModelArrayList;
    ParentCategoryAdapterHome parentCategoryRecyclerViewAdapter;
    ConstraintLayout layout_remove,requirements;
    ArrayList<UserAddress> addressModelArrayList;
    APIInterface apiInterface;
    TextView location,suggestion,viewMore,viewMore1;
    ArrayList<AllProducts> allItemsArrayList;
    AllProductsAdapter allProductsAdapter;
    ImageView bagImage;
    SwipeRefreshLayout swipeRefreshLayout;
    SliderView sliderView;
    SliderAdapterExample adapter;
    Button wholesalerJoin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        wholesalerJoin = view.findViewById(R.id.wholsaler_join);
        wholesalerJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString = "https://play.google.com/store/apps/details?id=com.ecommerce.grupomerchant";
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse(urlString));
                startActivity(i);
            }
        });
        viewMore = view.findViewById(R.id.view_more);
        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.categories);
                Fragment fragment = new CategoriesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        viewMore1 = view.findViewById(R.id.view_more1);
        viewMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.categories);
                Fragment fragment = new CategoriesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        suggestion = view.findViewById(R.id.suggestion);
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString = "https://docs.google.com/forms/d/e/1FAIpQLSdfpm0rJz6BI7Yv86jxX0J2ru0L6Xnf5ZfTPgr4h6WgEhIQhQ/viewform";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });
        requirements = view.findViewById(R.id.include);
        requirements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RequirementsActivity.class));
            }
        });
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
        //////////////////Adapter
        sliderView = view.findViewById(R.id.imageSlider);
        adapter = new SliderAdapterExample(getContext());
        renewItems(view);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        //////////////////Adapter
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
        /*wishListImage = view.findViewById(R.id.wishlist_image);
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
        viewAll = view.findViewById(R.id.view_all_categories);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.categories);
                Fragment fragment = new CategoriesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        addAddress = view.findViewById(R.id.constraintLayout3);
        location = view.findViewById(R.id.location);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        searchView = view.findViewById(R.id.editText2);
        layout_remove = view.findViewById(R.id.container);
        parentCategoryRecyclerView = view.findViewById(R.id.parent_category_recycler_view);
        parentCategoryRecyclerView.setNestedScrollingEnabled(false);
        parentCategoryModelArrayList = new ArrayList<>();
        productRecyclerView = view.findViewById(R.id.product_recycler_view);
        SharedPreferences sp = getContext().getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        Call<MultipleResource> call1 = apiInterface.getCategory1();
        call1.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {
                parentCategoryModelArrayList = response.body().data;
                parentCategoryRecyclerView.setVisibility(View.VISIBLE);
                // below line we are running a loop to add data to our adapter class.
                ArrayList<ParentCategoryModel> parentCategoryModelArrayList1 = new ArrayList<>();
                for (int i = 0; i < parentCategoryModelArrayList.size(); i++) {
                    if (parentCategoryModelArrayList.get(i).getTitle().equals("Home Appliances")||parentCategoryModelArrayList.get(i).getTitle().equals("Baby Care")||parentCategoryModelArrayList.get(i).getTitle().equals("Medicines")||parentCategoryModelArrayList.get(i).getTitle().equals("Furniture")||parentCategoryModelArrayList.get(i).getTitle().equals("Gifts & Toys")){
                        parentCategoryModelArrayList1.add(parentCategoryModelArrayList.get(i));
                    }
                    parentCategoryModelArrayList.removeAll(parentCategoryModelArrayList1);
                    parentCategoryRecyclerViewAdapter = new ParentCategoryAdapterHome(parentCategoryModelArrayList, getContext());
                    // below line is to set layout manager for our recycler view.
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
                    // setting layout manager for our recycler view.
                    parentCategoryRecyclerView.setLayoutManager(manager);
                    // below line is to set adapter to our recycler view.
                    parentCategoryRecyclerView.setAdapter(parentCategoryRecyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {

            }
        });
        Call<ArrayList<UserAddress>> call2 = apiInterface.getUserAddress(id);
        call2.enqueue(new Callback<ArrayList<UserAddress>>() {
            @Override
            public void onResponse(Call<ArrayList<UserAddress>> call, Response<ArrayList<UserAddress>> response) {
                addressModelArrayList = response.body();
                if (addressModelArrayList.isEmpty()) {

                } else {
                    for (int i = 0; i < addressModelArrayList.size(); i++) {
                        if (addressModelArrayList.get(i).getAddress().equals("")) {
                            location.setText("Add your Delivery Location");
                        } else {
                            location.setText(addressModelArrayList.get(i).getCity() + ", " + addressModelArrayList.get(i).getState() + ", " + addressModelArrayList.get(i).getPostalCode());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserAddress>> call, Throwable t) {

            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        Call<AllProductsPojo> call = apiInterface.getAllProductsTodaysChoice();
        call.enqueue(new Callback<AllProductsPojo>() {
            @Override
            public void onResponse(Call<AllProductsPojo> call, Response<AllProductsPojo> response) {
                allItemsArrayList = response.body().data;
                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < allItemsArrayList.size(); i++) {
                    allProductsAdapter = new AllProductsAdapter(allItemsArrayList, getContext(),1);

                    // below line is to set layout manager for our recycler view.
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

                    // setting layout manager for our recycler view.
                    productRecyclerView.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    productRecyclerView.setAdapter(allProductsAdapter);
                }
            }

            @Override
            public void onFailure(Call<AllProductsPojo> call, Throwable t) {

            }
        });
        trendingRecyclerView = view.findViewById(R.id.trending_recyclerview);
        Call<AllProductsPojo> allProductsPojoCall = apiInterface.getAllProductsTrending();
        allProductsPojoCall.enqueue(new Callback<AllProductsPojo>() {
            @Override
            public void onResponse(Call<AllProductsPojo> call, Response<AllProductsPojo> response) {
                allItemsArrayList = response.body().data;
                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < allItemsArrayList.size(); i++) {
                    allProductsAdapter = new AllProductsAdapter(allItemsArrayList, getContext(),1);

                    // below line is to set layout manager for our recycler view.
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);

                    // setting layout manager for our recycler view.
                    trendingRecyclerView.setLayoutManager(manager);

                    // below line is to set adapter to our recycler view.
                    trendingRecyclerView.setAdapter(allProductsAdapter);
                }
            }

            @Override
            public void onFailure(Call<AllProductsPojo> call, Throwable t) {

            }
        });
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ShippingAddressActivity.class));
            }
        });
        return view;
    }
    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 3; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i == 0) {
                sliderItem.setImageUrl("https://i.ibb.co/Hn1xwtc/My-project-1.png");
            } else if (i==1){
                sliderItem.setImageUrl("https://i.ibb.co/N1p2qhN/My-project-2.png");
            }else if (i==2){
                sliderItem.setImageUrl("https://i.ibb.co/bsfcGjd/My-project-3.png");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }

}