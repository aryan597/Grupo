package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ecommerce.grupo.Adapter.EnquiryAdapter;
import com.ecommerce.grupo.Adapter.ParentCategoryAdapter;
import com.ecommerce.grupo.Model.EnquiryModel;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Enquiry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiriesActivity extends AppCompatActivity {
    RecyclerView enquiriesRecyclerview;
    APIInterface apiInterface;
    ArrayList<EnquiryModel> enquiryModelArrayList;
    EnquiryAdapter enquiryAdapter;
    ConstraintLayout removeLayout;
    ImageView back;
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiries);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        SharedPreferences sp1 = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp1.getInt("id",0);
        refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Call<ArrayList<EnquiryModel>> enquiryCall = apiInterface.getEnquiries(id);
                enquiryCall.enqueue(new Callback<ArrayList<EnquiryModel>>() {
                    @Override
                    public void onResponse(Call<ArrayList<EnquiryModel>> call, Response<ArrayList<EnquiryModel>> response) {
                        enquiryModelArrayList = response.body();
                        if (enquiryModelArrayList.isEmpty()){
                            Toast.makeText(EnquiriesActivity.this, "List Empty", Toast.LENGTH_SHORT).show();
                            enquiriesRecyclerview.setVisibility(View.GONE);
                            removeLayout.setVisibility(View.VISIBLE);
                        }else{
                            // below line we are running a loop to add data to our adapter class.
                            enquiriesRecyclerview.setVisibility(View.VISIBLE);
                            removeLayout.setVisibility(View.GONE);
                            for (int i = 0; i < enquiryModelArrayList.size(); i++) {
                                enquiryAdapter = new EnquiryAdapter(enquiryModelArrayList, EnquiriesActivity.this,enquiriesRecyclerview,enquiryAdapter,removeLayout);

                                // below line is to set layout manager for our recycler view.
                                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

                                // setting layout manager for our recycler view.
                                enquiriesRecyclerview.setLayoutManager(manager);

                                // below line is to set adapter to our recycler view.
                                enquiriesRecyclerview.setAdapter(enquiryAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<EnquiryModel>> call, Throwable t) {

                    }
                });
                enquiryAdapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });
        back =findViewById(R.id.imageView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        enquiriesRecyclerview = findViewById(R.id.enquiries_recyclerview);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        removeLayout = findViewById(R.id.remove_layout);
        Call<ArrayList<EnquiryModel>> enquiryCall = apiInterface.getEnquiries(id);
        enquiryCall.enqueue(new Callback<ArrayList<EnquiryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<EnquiryModel>> call, Response<ArrayList<EnquiryModel>> response) {
                enquiryModelArrayList = response.body();
                if (enquiryModelArrayList.isEmpty()){
                    Toast.makeText(EnquiriesActivity.this, "List Empty", Toast.LENGTH_SHORT).show();
                    enquiriesRecyclerview.setVisibility(View.GONE);
                    removeLayout.setVisibility(View.VISIBLE);
                }else{
                    // below line we are running a loop to add data to our adapter class.
                    enquiriesRecyclerview.setVisibility(View.VISIBLE);
                    removeLayout.setVisibility(View.GONE);
                    for (int i = 0; i < enquiryModelArrayList.size(); i++) {
                        enquiryAdapter = new EnquiryAdapter(enquiryModelArrayList, EnquiriesActivity.this,enquiriesRecyclerview,enquiryAdapter,removeLayout);

                        // below line is to set layout manager for our recycler view.
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

                        // setting layout manager for our recycler view.
                        enquiriesRecyclerview.setLayoutManager(manager);

                        // below line is to set adapter to our recycler view.
                        enquiriesRecyclerview.setAdapter(enquiryAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<EnquiryModel>> call, Throwable t) {

            }
        });
    }
}