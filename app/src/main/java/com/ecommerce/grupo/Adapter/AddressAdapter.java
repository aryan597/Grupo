package com.ecommerce.grupo.Adapter;

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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.UpdateAddressActivity;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.PatchAddress;
import com.ecommerce.grupo.pojo.UserAddress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter  extends RecyclerView.Adapter<AddressAdapter.RecyclerViewHolder>{
    Context mContext;
    ArrayList<UserAddress> addressModels;
    APIInterface apiInterface;
    int id;

    public AddressAdapter(Context mContext, ArrayList<UserAddress> addressModels,int id) {
        this.mContext = mContext;
        this.addressModels = addressModels;
        this.id = id;
    }

    @NonNull
    @Override
    public AddressAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_layout, parent, false);
        return new AddressAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.RecyclerViewHolder holder, int position) {
        UserAddress addressModel = addressModels.get(position);
        holder.name.setText(addressModel.getType());
        apiInterface = APIClient.getClient().create(APIInterface.class);
        holder.address1.setText(addressModel.getAddress());
        String city = addressModel.getCity();
        String state = addressModel.getState();
        String postal = addressModel.getPostalCode();
        holder.cityStatePincode.setText(city+", "+state+" - "+postal);
        holder.updateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateAddressActivity.class);
                intent.putExtra("address1",holder.address1.getText().toString());
                intent.putExtra("city",city);
                intent.putExtra("state",state);
                intent.putExtra("addressId",Integer.parseInt(addressModel.getId()));
                intent.putExtra("postal",postal);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
        holder.deleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure you want to delete this Address?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<UserAddress> deleteAddressCall  = apiInterface.deleteAddress(Integer.parseInt(addressModel.getId()));
                                deleteAddressCall.enqueue(new Callback<UserAddress>() {
                                    @Override
                                    public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                                        if (response.isSuccessful()){
                                            notifyDataSetChanged();
                                            dialogInterface.dismiss();
                                        }else{
                                            Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserAddress> call, Throwable t) {
                                        notifyDataSetChanged();
                                        dialogInterface.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No",null)
                        .setTitle("Warning")
                        .setCancelable(true)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name,address1,cityStatePincode;
        Button updateAddress;
        ImageView deleteAddress;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.address_edit);
            address1 = itemView.findViewById(R.id.address1);
            cityStatePincode = itemView.findViewById(R.id.cityStatePincode);
            updateAddress = itemView.findViewById(R.id.update_address);
            deleteAddress = itemView.findViewById(R.id.delete_address);
        }
    }
}
