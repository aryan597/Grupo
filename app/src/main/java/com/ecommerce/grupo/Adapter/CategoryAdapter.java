package com.ecommerce.grupo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.CategoriesActivity;
import com.ecommerce.grupo.ImageCacheManager;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.ProductsActivity;
import com.ecommerce.grupo.R;

import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder>{
    ArrayList<CategoryModel> categoryModels;
    Context mContext;
    int id;

    public CategoryAdapter(ArrayList<CategoryModel> categoryModels, Context mContext,int id) {
        this.categoryModels = categoryModels;
        this.mContext = mContext;
        this.id = id;
    }

    @NonNull
    @Override
    public CategoryAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items, parent, false);
        return new CategoryAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.RecyclerViewHolder holder, int position) {
        CategoryModel modal = categoryModels.get(position);
        try {
            Bitmap bitmap = ImageCacheManager.getBitmap(mContext, modal);
            if (bitmap == null) {
                Glide.with(mContext).load(modal.getImageUrl()).into(holder.categoryImage);
                URL url = new URL(modal.getImageUrl());
                ImageCacheManager.putBitmap(mContext,modal,  BitmapFactory.decodeStream(url.openConnection().getInputStream()));
            }
            else {
                Toast.makeText(mContext, "Already", Toast.LENGTH_SHORT).show();
                holder.categoryImage.setImageBitmap(bitmap);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        holder.categoryText.setText(modal.getTitle());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductsActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("id1",modal.getId());
                intent.putExtra("title",modal.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryText;
        CardView container;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryText = itemView.findViewById(R.id.category_text);
            container = itemView.findViewById(R.id.constraintLayout4);
        }
    }
}
