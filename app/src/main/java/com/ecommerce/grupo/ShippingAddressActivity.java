package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ecommerce.grupo.Adapter.AddressAdapter;
import com.ecommerce.grupo.Model.AddressModel;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.PatchAddress;
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
    ArrayList<AddressModel> addressModelArrayList;
    AddressAdapter addressAdapter;
    ConstraintLayout add_address,addressList;

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
        addressRecyclerView = findViewById(R.id.address_recycler_view);
        Call<UserAddress> call = apiInterface.getUserAddress(id);
        call.enqueue(new Callback<UserAddress>() {
            @Override
            public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                addressModelArrayList = response.body().data;
                for (int i = 0; i < addressModelArrayList.size(); i++) {
                    addressAdapter = new AddressAdapter(getApplicationContext(),addressModelArrayList,id);
                    if (addressModelArrayList.get(i).getAddress1().isEmpty()){
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
            public void onFailure(Call<UserAddress> call, Throwable t) {
                Log.d("response",t.getMessage());
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PatchAddress patchAddress = new PatchAddress(address.getText().toString(),city.getText().toString(),state.getText().toString(),postal.getText().toString());
                Call<PatchAddress> call1 = apiInterface.patchAddress(id,patchAddress);
                call1.enqueue(new Callback<PatchAddress>() {
                    @Override
                    public void onResponse(Call<PatchAddress> call, Response<PatchAddress> response) {
                        Toast.makeText(ShippingAddressActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShippingAddressActivity.this,HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<PatchAddress> call, Throwable t) {

                    }
                });
            }
        });
    }
}