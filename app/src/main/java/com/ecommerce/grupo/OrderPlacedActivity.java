package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.Model.UserModel;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Data;
import com.ecommerce.grupo.pojo.UserAddress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderPlacedActivity extends AppCompatActivity {
    int productId,intentOrderId;
    APIInterface apiInterface;
    ImageView productImage;
    Button homeScreen;
    TextView productName,productDescription,address,cityStatePinCode,number,orderId;
    ArrayList<UserAddress> addressModelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productId = getIntent().getIntExtra("productId",0);
        intentOrderId = getIntent().getIntExtra("orderId",0);
        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int userId = sp.getInt("id",0);
        homeScreen = findViewById(R.id.home_screen);
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderPlacedActivity.this,HomeActivity.class));
                finish();
            }
        });
        address = findViewById(R.id.address);
        cityStatePinCode = findViewById(R.id.cityStatePincode);
        number = findViewById(R.id.number);
        productImage = findViewById(R.id.product_image);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        if (productId == 0){}
        else {
            Call<Data> call = apiInterface.getProductDetails(productId);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.isSuccessful()){
                        if (response.body().getTitle().length()>35){
                            productName.setText(response.body().getTitle().substring(0,35));
                        }else{
                            productName.setText(response.body().getTitle());
                        }if (response.body().getDescription().length()>30){
                            productDescription.setText(response.body().getDescription().substring(0,30));
                        }else{
                            productDescription.setText(response.body().getDescription());
                        }
                        Glide.with(OrderPlacedActivity.this).load(response.body().getImg().get1()).into(productImage);
                    }else{
                        Toast.makeText(OrderPlacedActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Toast.makeText(OrderPlacedActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        int addressId = getIntent().getIntExtra("addressId",0);
        Call<UserAddress> call2 = apiInterface.getParticularAddress(addressId);
        call2.enqueue(new Callback<UserAddress>() {
            @Override
            public void onResponse(Call<UserAddress> call, Response<UserAddress> response) {
                address.setText(response.body().getAddress());
                cityStatePinCode.setText(response.body().getCity() + ", " + response.body().getState() + ", " + response.body().getPostalCode());
            }

            @Override
            public void onFailure(Call<UserAddress> call, Throwable t) {

            }
        });
        Call<UserModel> userModelCall = apiInterface.getUserData(userId);
        userModelCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    ArrayList<UserModel> userModels = response.body().data;
                    for (int i = 0; i < userModels.size(); i++) {
                        number.setText("Phone number : "+userModels.get(i).getMobileNumber());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
        orderId = findViewById(R.id.order_id);
        String originalOrderId = "GRUPO000"+intentOrderId;
        orderId.setText("Order ID : "+originalOrderId);
    }
}