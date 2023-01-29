package com.ecommerce.grupo.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.CheckoutGrupoActivity;
import com.ecommerce.grupo.HomeActivity;
import com.ecommerce.grupo.Model.CartItems;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.RemoveCartItems;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<CartItems> cartItemsArrayList;
    Context context;
    APIInterface apiInterface;
    int userId;

    public CartAdapter(ArrayList<CartItems> cartItemsArrayList, Context context, int userId) {
        this.cartItemsArrayList = cartItemsArrayList;
        this.context = context;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(cartItemsArrayList.get(position).getImg().get1()).placeholder(R.drawable.placeholder).into(holder.productImage);
        if (cartItemsArrayList.get(position).getTitle().length()>15) {
            holder.productName.setText(cartItemsArrayList.get(position).getTitle().substring(0, 15) + "...");
        }else{
            holder.productName.setText(cartItemsArrayList.get(position).getTitle());
        }if (cartItemsArrayList.get(position).getDescription().length()>50) {
            holder.productDescription.setText(cartItemsArrayList.get(position).getDescription().substring(0, 50) + "...");
        }else{
            holder.productDescription.setText(cartItemsArrayList.get(position).getDescription());
        }
        holder.productPrice.setText("₹ "+cartItemsArrayList.get(position).getMaximumRetailPrice());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to remove this item from your cart?")
                        .setTitle("Warning")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RemoveCartItems removeCartItems = new RemoveCartItems(userId,cartItemsArrayList.get(position).getId());
                                Call<RemoveCartItems> removeCartItemsCall = apiInterface.deleteCart(removeCartItems);
                                removeCartItemsCall.enqueue(new Callback<RemoveCartItems>() {
                                    @Override
                                    public void onResponse(Call<RemoveCartItems> call, Response<RemoveCartItems> response) {
                                        if (response.isSuccessful()){
                                            dialogInterface.dismiss();
                                            Toast.makeText(context, "Product removed from cart successfully.", Toast.LENGTH_SHORT).show();
                                            context.startActivity(new Intent(context, HomeActivity.class));
                                            notifyItemRemoved(position);
                                            notifyDataSetChanged();
                                        }else {
                                            Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<RemoveCartItems> call, Throwable t) {
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setCancelable(true)
                        .show();
            }
        });
        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CheckoutGrupoActivity.class);
                intent.putExtra("productName",cartItemsArrayList.get(position).getTitle());
                intent.putExtra("productPrice",cartItemsArrayList.get(position).getMaximumRetailPrice());
                intent.putExtra("productId",cartItemsArrayList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage,deleteImage;
        TextView productName,productDescription,productPrice;
        Button buy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imageView27);
            deleteImage = itemView.findViewById(R.id.imageView28);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.textView14);
            productPrice = itemView.findViewById(R.id.textView48);
            buy = itemView.findViewById(R.id.buy);
        }
    }
}
