package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.PatchUserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText name,mobileNumber,email,occupation;
    ImageView back;
    String number,nam,emai,gen;
    Button saveButton;
    APIInterface apiInterface;
    Spinner genderSpinner;
    ProgressDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        genderSpinner = findViewById(R.id.gender);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        back =findViewById(R.id.imageView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        number = getIntent().getStringExtra("phone");
        nam = getIntent().getStringExtra("name");
        emai = getIntent().getStringExtra("email");
        gen = getIntent().getStringExtra("gender");
        name = findViewById(R.id.address_edit);
        if (gen == "Male"){
            genderSpinner.setSelection(0);
        }else if(gen == "Female"){
            genderSpinner.setSelection(1);
        }else if (gen == "Other")
            genderSpinner.setSelection(2);
        mobileNumber = findViewById(R.id.mobile_number);
        email = findViewById(R.id.email);
        email.setText(emai);
        occupation = findViewById(R.id.occupation);
        name.setText(nam);
        String[] courses = { "Male", "Female", "Prefer Not to Say" };
        ArrayAdapter ad = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(ad);
        mobileNumber.setText(number);
        mobileNumber.setEnabled(false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating Profile");
        dialog.setCancelable(false);
        saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String hahaha = genderSpinner.getSelectedItem().toString();
                String ha = "";
                if (hahaha.equals("Male")){
                    ha = hahaha;
                }else if (hahaha.equals("Female")){
                    ha = hahaha;
                }else if (hahaha.equals("Prefer Not to Say")){
                    ha = "Other";
                }
                PatchUserData patchUserData = new PatchUserData(name.getText().toString(),email.getText().toString(),ha);
                Call<PatchUserData> call = apiInterface.patchUserData(id,patchUserData);
                call.enqueue(new Callback<PatchUserData>() {
                    @Override
                    public void onResponse(Call<PatchUserData> call, Response<PatchUserData> response) {
                        if (response.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            /*startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
                            finish();*/
                        }
                        else {
                            dialog.dismiss();
                            Toast.makeText(EditProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PatchUserData> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
                        finish();
                    }
                });
            }
        });
    }
}