package com.ecommerce.grupo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.CategoriesActivity;
import com.ecommerce.grupo.ImageCacheManager;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.Model.ProductModel;
import com.ecommerce.grupo.ProductsActivity;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Product;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder>{
    ArrayList<CategoryModel> categoryModels;
    Context mContext;
    APIInterface apiInterface;
    ArrayList<ProductModel> productModels;
    ProductAdapter productAdapter;
    int i=1;
    int selectedPosition = 0;

    public CategoryAdapter(ArrayList<CategoryModel> categoryModels, Context mContext) {
        this.categoryModels = categoryModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_category_items, parent, false);
        return new CategoryAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        CategoryModel modal = categoryModels.get(position);
        Glide.with(mContext).load(modal.getImageUrl()).placeholder(R.drawable.placeholder).into(holder.categoryImage);
        holder.categoryText.setText(modal.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CategoriesActivity.class);
                intent.putExtra("parentCategoryId",modal.getParentCategoryId());
                intent.putExtra("id",modal.getId());
                mContext.startActivity(intent);
            }
        });
        /*if (i == 1) {
            Call<Product> call = apiInterface.doGetProduct(id, categoryModels.get(0).getId());
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    productModels = response.body().productModels;
                    if (productModels.isEmpty()) {
                        productsRecyclerView.setVisibility(View.GONE);
                        moreItems.setVisibility(View.VISIBLE);
                    } else {
                        productsRecyclerView.setVisibility(View.VISIBLE);
                        moreItems.setVisibility(View.GONE);
                        for (int i = 0; i < productModels.size(); i++) {
                            productAdapter = new ProductAdapter(productModels, mContext,id,categoryModels.get(0).getId());
                            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                            productsRecyclerView.setLayoutManager(manager);
                            productsRecyclerView.setAdapter(productAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                i=2;
                Call<Product> call = apiInterface.doGetProduct(id,modal.getId());
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        productModels = response.body().productModels;
                        if (productModels.isEmpty()){
                            productsRecyclerView.setVisibility(View.GONE);
                            moreItems.setVisibility(View.VISIBLE);
                        }else{
                            productsRecyclerView.setVisibility(View.VISIBLE);
                            moreItems.setVisibility(View.GONE);
                            for (int i = 0; i < productModels.size(); i++) {
                                productAdapter = new ProductAdapter(productModels, mContext,id, modal.getId());
                                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                                productsRecyclerView.setLayoutManager(manager);
                                productsRecyclerView.setAdapter(productAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                selectedPosition = position;
                notifyDataSetChanged();
                */
        /*holder.categoryText.setTextColor(view.getResources().getColor(R.color.white));*/
        /*
               */
        /* Intent intent = new Intent(mContext, ProductsActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("id1",modal.getId());
                intent.putExtra("title",modal.getTitle());
                mContext.startActivity(intent);*//*
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryText;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryText = itemView.findViewById(R.id.category_text);
        }
    }
}
