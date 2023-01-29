package com.ecommerce.grupo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.grupo.R;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.RecyclerViewHolder>{

    ArrayList<String> colorModels;
    Context mContext;

    public ColorAdapter(ArrayList<String> colorModels, Context mContext) {
        this.colorModels = colorModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sizes_layout, parent, false);
        return new ColorAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.sizeTv.setText(colorModels.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return colorModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView sizeTv;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeTv = itemView.findViewById(R.id.sizeTv);
        }
    }
}
