package com.ecommerce.grupo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.Model.ProductModel;
import com.ecommerce.grupo.Model.images;
import com.ecommerce.grupo.ProductActivity;
import com.ecommerce.grupo.R;

import java.util.ArrayList;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.RecyclerViewHolder>{
    ArrayList<ProductModel> productModels;
    Context mContext;
    int pcId, cId;

    public ProductAdapter(ArrayList<ProductModel> productModels, Context mContext,int pcId,int cId) {
        this.productModels = productModels;
        this.mContext = mContext;
        this.cId = cId;
        this.pcId = pcId;
    }

    @NonNull
    @Override
    public ProductAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item, parent, false);
        return new ProductAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductModel modal = productModels.get(position);
        if (modal.getTitle().length()>10){
            holder.categoryText.setMaxEms(10);
            holder.categoryText.setText(modal.getTitle().substring(0,10)+"..");
        }else{
            holder.categoryText.setText(modal.getTitle());
        }
        holder.priceTv.setText("₹ "+modal.getPrice());
        Glide.with(mContext).load(modal.getImg().get1()).into(holder.categoryImage);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("id",modal.getId());
                intent.putExtra("pcId",String.valueOf(pcId));
                intent.putExtra("cId",String.valueOf(cId));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryText,priceTv;
        ConstraintLayout container;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryText = itemView.findViewById(R.id.product_name);
            container = itemView.findViewById(R.id.constraintLayout4);
            priceTv = itemView.findViewById(R.id.price_tv);
        }
    }
}
