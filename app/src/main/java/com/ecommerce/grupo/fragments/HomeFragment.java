package com.ecommerce.grupo.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.Adapter.AllProductsAdapter;
import com.ecommerce.grupo.CategoriesActivity;
import com.ecommerce.grupo.Model.AddressModel;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.Model.SliderItem;
import com.ecommerce.grupo.ProductActivity;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.ShippingAddressActivity;
import com.ecommerce.grupo.Adapter.SliderAdapterExample;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.UserAddress;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    LinearLayout shoes,footwear,jewellery,clothing,beauty;
    ConstraintLayout addAddress;
    RecyclerView allItemsRecyclerView;
    SearchView searchView;
    AllProductsAdapter allProductsAdapter;
    ArrayList<AllProducts> allItemsArrayList;
    ArrayList<AddressModel> addressModelArrayList;
    ConstraintLayout layout_remove;
    FrameLayout constraintLayout;
    APIInterface apiInterface;
    TextView location,viewAll,suggestion;
    ImageView bagImage,wishListImage;
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
                String urlString = "https://docs.google.com/forms/d/e/1FAIpQLSdEgYGI4PNFK843-EK-83PsnlHYTB6rBBmgX8MC9MSP1i2-qg/viewform";
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
        wishListImage = view.findViewById(R.id.wishlist_image);
        wishListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                bottomNavigationView.setSelectedItemId(R.id.saved);
                Fragment fragment = new WishListFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right, R.anim.exit_left_to_right);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        jewellery = view.findViewById(R.id.linearLayout3);
        jewellery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoriesActivity.class);
                intent.putExtra("id",3);
                intent.putExtra("title","Jewellery");
                startActivity(intent);
            }
        });
        clothing = view.findViewById(R.id.linearLayout2);
        clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoriesActivity.class);
                intent.putExtra("id",4);
                intent.putExtra("title","Clothing");
                startActivity(intent);
            }
        });
        footwear = view.findViewById(R.id.linearLayout4);
        footwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoriesActivity.class);
                intent.putExtra("id",1);
                intent.putExtra("title","Footwear");
                startActivity(intent);

            }
        });
        beauty = view.findViewById(R.id.beauty);
        beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CategoriesActivity.class);
                intent.putExtra("id",5);
                intent.putExtra("title","Beauty");
                startActivity(intent);
            }
        });
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
        searchView.setQueryHint("Search by name or id");
        searchView.setIconified(false);
        allItemsRecyclerView = view.findViewById(R.id.allItemsRecyclerView);
        layout_remove = view.findViewById(R.id.container);
        SharedPreferences sp = getContext().getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        Call<UserAddress> call2 = apiInterface.getUserAddress(id);
        call2.enqueue(new Callback<UserAddress>() {
            @Override
            public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                addressModelArrayList = response.body().data;
                if (addressModelArrayList.isEmpty()){

                }else {
                    for (int i = 0; i < addressModelArrayList.size(); i++) {
                        if (addressModelArrayList.get(i).getCity().equals("")){
                            location.setText("Add your Delivery Location");
                        }else {
                            location.setText(addressModelArrayList.get(i).getCity()+", "+addressModelArrayList.get(i).getState()+", "+addressModelArrayList.get(i).getPostalCode());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserAddress> call, Throwable t) {
                Log.d("response",t.getMessage());
            }
        });
        Call<AllProductsPojo> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<AllProductsPojo>() {
            @Override
            public void onResponse(Call<AllProductsPojo> call, Response<AllProductsPojo> response) {
                allItemsArrayList = response.body().data;
                // below line we are running a loop to add data to our adapter class.
                for (int i = 0; i < allItemsArrayList.size(); i++) {
                    allProductsAdapter = new AllProductsAdapter(allItemsArrayList, getContext());

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
                    layout_remove.setVisibility(View.GONE);
                    allItemsRecyclerView.setVisibility(View.VISIBLE);
                    filterList(s);
                } else {
                    // Do something when there's no input
                    hideKeyboard(getActivity());
                    layout_remove.setVisibility(View.VISIBLE);
                    allItemsRecyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });
        /////////////////Search View
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ShippingAddressActivity.class));
            }
        });
        shoes =view.findViewById(R.id.shoes);
        return view;
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
            Toast.makeText(getContext(), "Item Not Available..!", Toast.LENGTH_SHORT).show();
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