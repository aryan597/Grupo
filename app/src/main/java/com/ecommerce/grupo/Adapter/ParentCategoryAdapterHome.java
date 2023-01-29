package com.ecommerce.grupo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.ecommerce.grupo.CategoriesActivity;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.R;

import java.util.ArrayList;

public class ParentCategoryAdapterHome extends RecyclerView.Adapter<ParentCategoryAdapterHome.RecyclerViewHolder>{
    private ArrayList<ParentCategoryModel> parentCategoryModels;
    private Context mcontext;

    public ParentCategoryAdapterHome(ArrayList<ParentCategoryModel> parentCategoryModels, Context mcontext) {
        this.parentCategoryModels = parentCategoryModels;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_category_items_home, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ParentCategoryModel modal = parentCategoryModels.get(position);
        holder.categoryText.setText(modal.getTitle());
        Glide.with(mcontext).load(modal.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.categoryImage);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, CategoriesActivity.class);
                intent.putExtra("parentCategoryId",modal.getParentCategoryId());
                intent.putExtra("id",modal.getId());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parentCategoryModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryText;
        ConstraintLayout container;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryText = itemView.findViewById(R.id.category_text);
            container = itemView.findViewById(R.id.constraintLayout4);
        }
    }
}
