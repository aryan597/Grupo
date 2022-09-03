package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.CreateUser;
import com.ecommerce.grupo.pojo.MultipleResource;
import com.ecommerce.grupo.pojo.User;
import com.ecommerce.grupo.pojo.UserOtp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    EditText ot1,ot2,ot3,ot4,ot5,ot6;
    Button continueButton;
    TextView number,resend;
    APIInterface apiInterface;
    ProgressDialog dialog;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        String phone = getIntent().getStringExtra("phone");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        resend = findViewById(R.id.textView5);
        resend.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
              resend.setVisibility(View.VISIBLE);
            }
        }, 30000);
        ProgressDialog dialog1 = new ProgressDialog(this);
        dialog1.setMessage("Sending OTP again on : "+phone);
        dialog1.setCancelable(false);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.show();
                User user = new User(phone);
                Call<User> call1 = apiInterface.sendOtp(user);
                call1.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user1 = response.body();
                        if (response.code() == 500){
                            dialog1.dismiss();
                            Toast.makeText(OtpActivity.this, "Internal Server Error! Please Try again later.", Toast.LENGTH_SHORT).show();
                        }
                        if (response.message().equals("Not Found")){
                            CreateUser createUser = new CreateUser("",
                                    phone,
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
                                        User user = new User(phone);
                                        Call<User> call2 = apiInterface.sendOtp(user);
                                        call2.enqueue(new Callback<User>() {
                                            @Override
                                            public void onResponse(Call<User> call, Response<User> response) {
                                                User user2 = response.body();
                                                String etag = response.headers().get("ETag");
                                                int id = Integer.parseInt(user2.userId);
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
                            dialog1.dismiss();
                            SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
                            SharedPreferences.Editor Editor = sp.edit();
                            Editor.putInt("id",Integer.valueOf(user1.userId));
                            Editor.commit();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });
        ot1 = findViewById(R.id.ot1);
        ot2 = findViewById(R.id.ot2);
        ot3 = findViewById(R.id.ot3);
        ot4 = findViewById(R.id.ot4);
        ot5 = findViewById(R.id.ot5);
        ot6 = findViewById(R.id.ot6);
        number = findViewById(R.id.number);
        number.setText("Please enter the one time password\nsent on : "+phone);
        ot1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 1) {
                    ot2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ot2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("count", "" + count);
                Log.e("count", "" + count);
                Log.e("count", "" + count);

                if (s.length() == 1) {
                    ot3.requestFocus();
                }else if(count==0){
                    ot1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ot3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 1) {
                    ot4.requestFocus();
                }else if(count==0){
                    ot2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ot4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 1) {
                    ot5.requestFocus();
                }else if(count==0){
                    ot3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ot5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count == 1) {
                    ot6.requestFocus();
                }else if(count==0){
                    ot4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ot6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    ot6.requestFocus();
                }else if(count==0){
                    ot5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog = new ProgressDialog(OtpActivity.this);
        dialog.setMessage("Verifying OTP...!");
        dialog.setCancelable(false);
        continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = ot1.getText().toString() + ot2.getText().toString() + ot3.getText().toString() + ot4.getText().toString() + ot5.getText().toString() + ot6.getText().toString();
                int id = getIntent().getIntExtra("id",0);
                if (code.isEmpty()){
                    Toast.makeText(OtpActivity.this, "Please Enter an OTP", Toast.LENGTH_SHORT).show();
                }else {
                    dialog.show();
                    UserOtp userOtp = new UserOtp(id, code);
                    Call<UserOtp> call1 = apiInterface.verifyOtp(userOtp);
                    call1.enqueue(new Callback<UserOtp>() {
                        @Override
                        public void onResponse(Call<UserOtp> call, Response<UserOtp> response){
                            if (response.isSuccessful()){
                                UserOtp userOtp1 = response.body();
                                saveToSharedPreferences();
                                dialog.dismiss();
                                Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                Toast.makeText(OtpActivity.this, userOtp1.data, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(OtpActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserOtp> call, Throwable t) {
                            dialog.dismiss();
                            call.cancel();
                            Toast.makeText(OtpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void saveToSharedPreferences() {
        sp = getSharedPreferences("MyUserLoggedInData",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString("loggedIn","true");
        myEdit.commit();
    }
}