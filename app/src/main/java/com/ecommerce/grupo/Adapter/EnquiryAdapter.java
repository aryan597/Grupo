package com.ecommerce.grupo.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.EnquiriesActivity;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.EnquiryModel;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Enquiry;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryAdapter extends RecyclerView.Adapter<EnquiryAdapter.RecyclerViewHolder> {
    APIInterface apiInterface;
    ArrayList<EnquiryModel> enquiryModels;
    Context mContext;
    RecyclerView enquiriesRecyclerview;
    EnquiryAdapter enquiryAdapter;
    ConstraintLayout removeLayout;


    public EnquiryAdapter(ArrayList<EnquiryModel> enquiryModels, Context mContext, RecyclerView enquiriesRecyclerview, EnquiryAdapter enquiryAdapter, ConstraintLayout removeLayout) {
        this.enquiryModels = enquiryModels;
        this.mContext = mContext;
        this.enquiriesRecyclerview = enquiriesRecyclerview;
        this.enquiryAdapter = enquiryAdapter;
        this.removeLayout = removeLayout;
    }

    @NonNull
    @Override
    public EnquiryAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enquiry_layout, parent, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        return new EnquiryAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnquiryAdapter.RecyclerViewHolder holder, int position) {
        EnquiryModel modal = enquiryModels.get(position);
        holder.title.setText(modal.getProductName());
        holder.quantity.setText("Quantity enquired : "+modal.getQuantity()+" Units");
        holder.price.setText("Total Price : ₹"+modal.totalPrice);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Warning")
                        .setMessage("Are you sure you want to delete your Enquiry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<Enquiry> deleteCall = apiInterface.deleteEnquiry(modal.getId());
                                deleteCall.enqueue(new Callback<Enquiry>() {
                                    @Override
                                    public void onResponse(Call<Enquiry> call, Response<Enquiry> response) {
                                        if (response.isSuccessful()){
                                            notifyDataSetChanged();
                                            Toast.makeText(mContext, "Enquiry Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            Call<ArrayList<EnquiryModel>> enquiryCall = apiInterface.getEnquiries(modal.getUserId());
                                            enquiryCall.enqueue(new Callback<ArrayList<EnquiryModel>>() {
                                                @Override
                                                public void onResponse(Call<ArrayList<EnquiryModel>> call, Response<ArrayList<EnquiryModel>> response) {
                                                    enquiryModels = response.body();
                                                    if (enquiryModels.isEmpty()){
                                                        Toast.makeText(mContext, "List Empty", Toast.LENGTH_SHORT).show();
                                                        enquiriesRecyclerview.setVisibility(View.GONE);
                                                        removeLayout.setVisibility(View.VISIBLE);
                                                    }else{
                                                        // below line we are running a loop to add data to our adapter class.
                                                        enquiriesRecyclerview.setVisibility(View.VISIBLE);
                                                        removeLayout.setVisibility(View.GONE);
                                                        for (int i = 0; i < enquiryModels.size(); i++) {
                                                            enquiryAdapter = new EnquiryAdapter(enquiryModels, mContext,enquiriesRecyclerview,enquiryAdapter,removeLayout);

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

                                    @Override
                                    public void onFailure(Call<Enquiry> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("No",null)
                        .setCancelable(true)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return enquiryModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title,quantity,price;
        Button delete;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView19);
            quantity = itemView.findViewById(R.id.textView20);
            price = itemView.findViewById(R.id.textView21);
            delete = itemView.findViewById(R.id.delete_enquiry_button);
        }
    }
}
