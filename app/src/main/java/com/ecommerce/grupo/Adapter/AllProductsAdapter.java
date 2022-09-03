package com.ecommerce.grupo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.ProductActivity;
import com.ecommerce.grupo.R;

import java.util.ArrayList;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ViewHolder> {
    ArrayList<AllProducts> allProductsArrayList;
    Context context;

    public AllProductsAdapter(ArrayList<AllProducts> allProductsArrayList, Context context) {
        this.allProductsArrayList = allProductsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AllProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new AllProductsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(allProductsArrayList.get(position).getTitle());
        Glide.with(context).load(allProductsArrayList.get(position).getImg().get1()).into(holder.productImage);
        holder.cartItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("id",allProductsArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allProductsArrayList.size();
    }

    public void setFilteredList(ArrayList<AllProducts> filteredList) {
        this.allProductsArrayList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout cartItemCard;
        ImageView productImage;
        TextView productName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productImage = itemView.findViewById(R.id.img);
            cartItemCard = itemView.findViewById(R.id.container);
        }
    }
}
