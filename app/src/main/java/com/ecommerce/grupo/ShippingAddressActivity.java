package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ecommerce.grupo.Adapter.AddressAdapter;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.PatchAddress;
import com.ecommerce.grupo.pojo.PostAddress;
import com.ecommerce.grupo.pojo.UserAddress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressActivity extends AppCompatActivity {

    APIInterface apiInterface;
    EditText address,city,state,postal;
    Button saveButton;
    ImageView back;
    RecyclerView addressRecyclerView;
    ArrayList<UserAddress> addressModelArrayList;
    AddressAdapter addressAdapter;
    Button addAddressButton;
    ConstraintLayout add_address,addressList;
    Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        add_address = findViewById(R.id.add_address);
        addressList = findViewById(R.id.address_list);
        back =findViewById(R.id.imageView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        address = findViewById(R.id.address_edit);
        city = findViewById(R.id.gender);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        state = findViewById(R.id.occupation);
        postal = findViewById(R.id.mobile_number);
        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        saveButton = findViewById(R.id.save);
        typeSpinner = findViewById(R.id.type);
        String[] types = { "HOME", "WORK", "OTHER" };
        ArrayAdapter ad = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                types);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(ad);
        addressRecyclerView = findViewById(R.id.address_recycler_view);
        Call<ArrayList<UserAddress>> call = apiInterface.getUserAddress(id);
        call.enqueue(new Callback<ArrayList<UserAddress>>() {
           @Override
           public void onResponse(Call<ArrayList<UserAddress>> call, Response<ArrayList<UserAddress>> response) {
               if (response.body().isEmpty()){
                   add_address.setVisibility(View.VISIBLE);
                   addressList.setVisibility(View.GONE);
               }else {
                   addressModelArrayList = response.body();
                   for (int i = 0; i < addressModelArrayList.size(); i++) {
                       addressAdapter = new AddressAdapter(getApplicationContext(), addressModelArrayList, id);
                       ////if  error code is equal to 404 then call another method app crash
                       if (addressModelArrayList.get(i).getAddress().isEmpty()) {
                           add_address.setVisibility(View.VISIBLE);
                           addressList.setVisibility(View.GONE);
                       } else {
                           add_address.setVisibility(View.GONE);
                           addressList.setVisibility(View.VISIBLE);
                           StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                           addressRecyclerView.setLayoutManager(manager);
                           addressRecyclerView.setAdapter(addressAdapter);
                       }

                   }
               }
           }

           @Override
           public void onFailure(Call<ArrayList<UserAddress>> call, Throwable t) {

           }
       });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostAddress patchAddress = new PostAddress(address.getText().toString(),city.getText().toString(),state.getText().toString(),postal.getText().toString(),id,typeSpinner.getSelectedItem().toString());
                Call<PostAddress> postAddressCall = apiInterface.postAddress(patchAddress);
                postAddressCall.enqueue(new Callback<PostAddress>() {
                    @Override
                    public void onResponse(Call<PostAddress> call, Response<PostAddress> response) {
                        if (response.isSuccessful()){
                            Call<ArrayList<UserAddress>> call1 = apiInterface.getUserAddress(id);
                            call1.enqueue(new Callback<ArrayList<UserAddress>>() {
                                @Override
                                public void onResponse(Call<ArrayList<UserAddress>> call, Response<ArrayList<UserAddress>> response) {
                                    addressModelArrayList = response.body();
                                    for (int i = 0; i < addressModelArrayList.size(); i++) {
                                        addressAdapter = new AddressAdapter(getApplicationContext(),addressModelArrayList,id);
                                        ////if  error code is equal to 404 then call another method app crash
                                        if (addressModelArrayList.get(i).getAddress().isEmpty()){
                                            add_address.setVisibility(View.VISIBLE);
                                            addressList.setVisibility(View.GONE);
                                        }else{
                                            add_address.setVisibility(View.GONE);
                                            addressList.setVisibility(View.VISIBLE);
                                            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                                            addressRecyclerView.setLayoutManager(manager);
                                            addressRecyclerView.setAdapter(addressAdapter);
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<UserAddress>> call, Throwable t) {

                                }
                            });
                        }else{
                            Toast.makeText(ShippingAddressActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PostAddress> call, Throwable t) {
                        Toast.makeText(ShippingAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        addAddressButton = findViewById(R.id.add_address_button);
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_address.setVisibility(View.VISIBLE);
                addressList.setVisibility(View.GONE);
            }
        });
    }
}