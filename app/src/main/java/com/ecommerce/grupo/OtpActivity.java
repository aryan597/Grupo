package com.ecommerce.grupo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.CreateUser;
import com.ecommerce.grupo.pojo.User;
import com.ecommerce.grupo.pojo.UserOtp;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    EditText ot1, ot2, ot3, ot4, ot5, ot6;
    Button continueButton;
    TextView number, resend, autoReadOtp;
    ProgressBar sms;
    ImageView back;
    String autoCode = "";
    private static final int REQ_USER_CONSENT = 200;
    SmsReceiverBroad smsBroadcastReceiver;
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
        back = findViewById(R.id.imageView2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        dialog1.setMessage("Sending OTP again on : " + phone);
        dialog1.setCancelable(false);
        startSmartUserConsent();
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
                        if (response.code() == 500) {
                            dialog1.dismiss();
                            Toast.makeText(OtpActivity.this, "Internal Server Error! Please Try again later.", Toast.LENGTH_SHORT).show();
                        }
                        if (response.message().equals("Not Found")) {
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
                                    if (response.message().equals("OK")) {
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
                        } else if (response.code() == 200) {
                            dialog1.dismiss();
                            SharedPreferences sp = getSharedPreferences("MyUserId", MODE_PRIVATE);
                            SharedPreferences.Editor Editor = sp.edit();
                            Editor.putInt("id", Integer.valueOf(user1.userId));
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
       /*number = findViewById(R.id.number);
        number.setText("Please enter the one time password\nsent on : "+phone);*/
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
                } else if (count == 0) {
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
                } else if (count == 0) {
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
                } else if (count == 0) {
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
                } else if (count == 0) {
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
                } else if (count == 0) {
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
        sms = findViewById(R.id.progressBar2);
        sms.setVisibility(View.GONE);
        autoReadOtp = findViewById(R.id.auto_read_otp);
        autoReadOtp.setVisibility(View.GONE);
        continueButton = findViewById(R.id.continue_button);
        ///automatic sms receiver
        /*SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                if (!messageText.equals("")) {
                    autoCode = messageText;
                    autoReadOtp.setVisibility(View.GONE);
                    sms.setVisibility(View.GONE);
                    ot1.setText(autoCode.substring(0,1));
                    ot2.setText(autoCode.substring(1,2));
                    ot3.setText(autoCode.substring(2,3));
                    ot4.setText(autoCode.substring(3,4));
                    ot5.setText(autoCode.substring(4,5));
                    ot6.setText(autoCode.substring(5,6));
                }else{
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            sms.setVisibility(View.GONE);
                            autoReadOtp.setVisibility(View.GONE);
                            Toast.makeText(OtpActivity.this, "Cannot read OTP.", Toast.LENGTH_SHORT).show();
                        }
                    }, 8000);
                }
            }
        });*/
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCode.equals("")) {
                    String code = ot1.getText().toString() + ot2.getText().toString() + ot3.getText().toString() + ot4.getText().toString() + ot5.getText().toString() + ot6.getText().toString();
                    int id = getIntent().getIntExtra("id", 0);
                    if (code.isEmpty()) {
                        Toast.makeText(OtpActivity.this, "Please Enter an OTP", Toast.LENGTH_SHORT).show();
                    } else if (code.length() != 6) {
                        Toast.makeText(OtpActivity.this, "Please enter a valid code", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.show();
                        UserOtp userOtp = new UserOtp(id, code);
                        Call<UserOtp> call1 = apiInterface.verifyOtp(userOtp);
                        call1.enqueue(new Callback<UserOtp>() {
                            @Override
                            public void onResponse(Call<UserOtp> call, Response<UserOtp> response) {
                                if (response.code() == 404) {
                                    Toast.makeText(OtpActivity.this, "Please enter the correct OTP.", Toast.LENGTH_SHORT).show();
                                }
                                if (response.isSuccessful()) {
                                    UserOtp userOtp1 = response.body();
                                    saveToSharedPreferences();
                                    dialog.dismiss();
                                    Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(OtpActivity.this, userOtp1.data, Toast.LENGTH_SHORT).show();
                                } else {
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
                } else {
                    int id = getIntent().getIntExtra("id", 0);
                    dialog.show();
                    UserOtp userOtp = new UserOtp(id, autoCode);
                    Call<UserOtp> call1 = apiInterface.verifyOtp(userOtp);
                    call1.enqueue(new Callback<UserOtp>() {
                        @Override
                        public void onResponse(Call<UserOtp> call, Response<UserOtp> response) {
                            if (response.code() == 404) {
                                Toast.makeText(OtpActivity.this, "Please enter the correct OTP.", Toast.LENGTH_SHORT).show();
                            }
                            if (response.isSuccessful()) {
                                UserOtp userOtp1 = response.body();
                                saveToSharedPreferences();
                                dialog.dismiss();
                                Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                                Toast.makeText(OtpActivity.this, userOtp1.data, Toast.LENGTH_SHORT).show();
                            } else {
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
        sp = getSharedPreferences("MyUserLoggedInData", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString("loggedIn", "true");
        myEdit.commit();
    }

    private void startSmartUserConsent() {

        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsUserConsent(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_USER_CONSENT){

            if ((resultCode == RESULT_OK) && (data != null)){

                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                getOtpFromMessage(message);


            }


        }

    }

    private void getOtpFromMessage(String message) {

        Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
        Matcher matcher = otpPattern.matcher(message);
        if (matcher.find()){
            ot1.setText(matcher.group(0).charAt(0)+"" );
            ot2.setText(matcher.group(0).charAt(1)+"" );
            ot3.setText(matcher.group(0).charAt(2)+"" );
            ot4.setText(matcher.group(0).charAt(3)+"" );
            ot5.setText(matcher.group(0).charAt(4)+"" );
            ot6.setText(matcher.group(0).charAt(5)+"" );
        }


    }

    private void registerBroadcastReceiver(){

        smsBroadcastReceiver = new SmsReceiverBroad();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsReceiverBroad.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                startActivityForResult(intent,REQ_USER_CONSENT);
            }
            @Override
            public void onFailure() {

            }
        };
        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver,intentFilter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
    }

}