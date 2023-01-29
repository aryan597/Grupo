package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.Adapter.CategoryAdapter;
import com.ecommerce.grupo.Adapter.ProductAdapter;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.Model.ProductModel;
import com.ecommerce.grupo.Model.images;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.Category;
import com.ecommerce.grupo.pojo.Product;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {
    int id,id1;
    String title;
    TextView ttittle;
    ImageView back;
    APIInterface apiInterface;
    ArrayList<ProductModel> productModels;
    ProductAdapter productAdapter;
    RecyclerView productsRecyclerView;
    ArrayList<ParentCategoryModel> parentCategoryModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        back = findViewById(R.id.imageView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        id = getIntent().getIntExtra("id",0);
        id1 = getIntent().getIntExtra("id1",0);
        productsRecyclerView = findViewById(R.id.products_recycler_view);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        ArrayList<images> imagesArrayList = new ArrayList<>();
        Call<Product> call = apiInterface.doGetProduct(id,id1);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productModels = response.body().productModels;
                if (productModels.isEmpty()){
                    Toast.makeText(ProductsActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                }else{
                    for (int i = 0; i < productModels.size(); i++) {
                        productAdapter = new ProductAdapter(productModels, ProductsActivity.this,id,id1);
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        productsRecyclerView.setLayoutManager(manager);
                        productsRecyclerView.setAdapter(productAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}