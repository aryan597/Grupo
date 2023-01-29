package com.ecommerce.grupo.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.UserAddress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressSelectionAdapter extends RecyclerView.Adapter<AddressSelectionAdapter.RecyclerViewHolder>{

    ArrayList<UserAddress> addressModelArrayList;
    Context context;
    Dialog dialog;
    int data;
    APIInterface apiInterface;
    TextView address,location,addressId;

    public AddressSelectionAdapter(ArrayList<UserAddress> addressModelArrayList, Context context, Dialog dialog, int data, TextView address, TextView location, TextView addressId) {
        this.addressModelArrayList = addressModelArrayList;
        this.context = context;
        this.dialog = dialog;
        this.data = data;
        this.address = address;
        this.location = location;
        this.addressId = addressId;
    }

    @NonNull
    @Override
    public AddressSelectionAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_selection_item_layout, parent, false);
        return new AddressSelectionAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressSelectionAdapter.RecyclerViewHolder holder, int position) {
        apiInterface = APIClient.getClient().create(APIInterface.class);
        UserAddress addressModel = addressModelArrayList.get(position);
        holder.name.setText(addressModel.getType());
        holder.address1.setText(addressModel.getAddress());
        String city = addressModel.getCity();
        String state = addressModel.getState();
        String postal = addressModel.getPostalCode();
        holder.cityStatePincode.setText(city+", "+state+" - "+postal);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = Integer.parseInt(addressModel.getId());
                Call<UserAddress> call2 = apiInterface.getParticularAddress(data);
               call2.enqueue(new Callback<UserAddress>() {
                   @Override
                   public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                       address.setText(response.body().getAddress());
                       location.setText(response.body().getCity() + ", " + response.body().getState() + ", " + response.body().getPostalCode());
                       addressId.setText(response.body().getId());
                   }

                   @Override
                   public void onFailure(Call<UserAddress> call, Throwable t) {

                   }
               });
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView name,address1,cityStatePincode,addressId;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.address_edit);
            address1 = itemView.findViewById(R.id.address1);
            cityStatePincode = itemView.findViewById(R.id.cityStatePincode);
        }
    }
}
