package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.PatchAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddressActivity extends AppCompatActivity {
    String address1,city,state,postal;
    EditText address1Edit,cityEdit,stateEdit,postalEdit;
    Button save;
    APIInterface apiInterface;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp.getInt("id",0);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        address1 = getIntent().getStringExtra("address1");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        postal = getIntent().getStringExtra("postal");
        address1Edit = findViewById(R.id.address_edit);
        cityEdit = findViewById(R.id.city_edit);
        stateEdit = findViewById(R.id.state_edit);
        postalEdit = findViewById(R.id.pincode_edit);
        address1Edit.setText(address1);
        cityEdit.setText(city);
        stateEdit.setText(state);
        postalEdit.setText(postal);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating Address");
        dialog.setCancelable(false);
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                PatchAddress patchAddress = new PatchAddress(address1Edit.getText().toString(),cityEdit.getText().toString(),stateEdit.getText().toString(),postalEdit.getText().toString());
                Call<PatchAddress> call1 = apiInterface.patchAddress(id,patchAddress);
                call1.enqueue(new Callback<PatchAddress>() {
                    @Override
                    public void onResponse(Call<PatchAddress> call, Response<PatchAddress> response) {
                       if (response.isSuccessful()){
                           dialog.dismiss();
                           Toast.makeText(UpdateAddressActivity.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(UpdateAddressActivity.this,HomeActivity.class));
                           finish();
                       }
                    }

                    @Override
                    public void onFailure(Call<PatchAddress> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(UpdateAddressActivity.this, "Address Updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateAddressActivity.this,HomeActivity.class));
                        finish();
                    }
                });
            }
        });
    }
}