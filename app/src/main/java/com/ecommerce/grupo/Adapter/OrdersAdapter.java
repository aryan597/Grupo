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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.Model.Orders;
import com.ecommerce.grupo.ProductActivity;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.RecyclerViewHolder>{

    ArrayList<Orders> ordersArrayList;
    Context mContext;
    APIInterface apiInterface;

    public OrdersAdapter(ArrayList<Orders> ordersArrayList, Context mContext) {
        this.ordersArrayList = ordersArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrdersAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new OrdersAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.RecyclerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        holder.quantity.setText("Quantity : "+ordersArrayList.get(position).getQuantity()+" units");
        int productId = ordersArrayList.get(position).getProductId();
        holder.orderId.setText("Order ID : GRUPO000"+ordersArrayList.get(position).getId());
        holder.productPrice.setText("₹ "+ordersArrayList.get(position).getProductTotalPrice());
        Call<Data> call = apiInterface.getProductDetails(productId);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()){
                    Glide.with(mContext).load(response.body().getImg().get1()).placeholder(R.drawable.placeholder).into(holder.productImage);
                    if (response.body().getTitle().length()>15) {
                        holder.productName.setText(response.body().getTitle().substring(0, 15) + "...");
                    }else{
                        holder.productName.setText(response.body().getTitle());
                    }if (response.body().getDescription().length()>15) {
                        holder.productDescription.setText(response.body().getDescription().substring(0,15)+"...");
                    }else{
                        holder.productDescription.setText(response.body().getDescription());
                    }
                }else{
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("id",ordersArrayList.get(position).getProductId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName,productDescription,productPrice,quantity,orderId;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            productImage = itemView.findViewById(R.id.imageView27);
            productDescription = itemView.findViewById(R.id.product_description);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.textView48);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
