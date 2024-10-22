package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.CreateUser;
import com.ecommerce.grupo.pojo.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText phone;
    APIInterface apiInterface;
    ProgressDialog progressDialog;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending OTP(One Time Password)!");
        progressDialog.setCancelable(false);
        phone = findViewById(R.id.editText);
        phone.setBackground(null);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(v->{
            progressDialog.show();
            ValidatePhone();
        });
    }
    private void showBottomSheetDialog(String phone,int id) {
        progressDialog.dismiss();
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.terms_conditions);
        CheckBox checkBox1,checkBox2;
        checkBox1 = bottomSheetDialog.findViewById(R.id.checkBox);
        checkBox2 = bottomSheetDialog.findViewById(R.id.checkBox2);
        Button continueButton = bottomSheetDialog.findViewById(R.id.continue_button);
        continueButton.setEnabled(false);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    continueButton.setBackgroundColor(getResources().getColor(R.color.button));
                    continueButton.setEnabled(true);
                }
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OtpActivity.class);
                intent.putExtra("id",id);
                SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
                SharedPreferences.Editor Editor = sp.edit();
                Editor.putInt("id",id);
                Editor.commit();
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.show();
    }
    private void ValidatePhone(){
        if (phone.getText().toString().equals("")){
            progressDialog.dismiss();
            phone.setError("Please Enter Phone Number");
            phone.requestFocus();
        }else if (phone.getText().toString().length()!=10){
            Toast.makeText(this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
        }
        else{
            String phoneNumber = "+91"+phone.getText().toString().trim();
            User user = new User(phoneNumber);
            Call<User> call1 = apiInterface.sendOtp(user);
            call1.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user1 = response.body();
                    if (response.code() == 500){
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Internal Server Error! Please Try again later.", Toast.LENGTH_SHORT).show();
                    }
                    if (response.message().equals("Not Found")){
                        CreateUser createUser = new CreateUser("",
                                phoneNumber,
                                "",
                                "",
                                false,
                                false,
                                "CUSTOMER");
                        Call<CreateUser> createUserCall = apiInterface.createUser(createUser);
                        createUserCall.enqueue(new Callback<CreateUser>() {
                            @Override
                            public void onResponse(Call<CreateUser> call, Response<CreateUser> response) {
                                CreateUser createUser1 = response.body();
                                if(response.message().equals("OK")){
                                    User user = new User(phoneNumber);
                                    Call<User> call2 = apiInterface.sendOtp(user);
                                    call2.enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            User user2 = response.body();
                                            String etag = response.headers().get("ETag");
                                            int id = Integer.parseInt(user2.userId);
                                            showBottomSheetDialog(phoneNumber,id);
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                            @Override
                            public void onFailure(Call<CreateUser> call, Throwable t) {

                            }
                        });
                    }
                    else if (response.code() == 200){
                        progressDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this,OtpActivity.class);
                        intent.putExtra("phone",phoneNumber);
                        intent.putExtra("id",Integer.valueOf(user1.userId));
                        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
                        SharedPreferences.Editor Editor = sp.edit();
                        Editor.putInt("id",Integer.valueOf(user1.userId));
                        Editor.commit();
                        intent.putExtra("type","old");
                        startActivity(intent);
                    }else if (response.code() == 429){
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sorry our messaging servers are down. Please Try again in a moment!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    call.cancel();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}