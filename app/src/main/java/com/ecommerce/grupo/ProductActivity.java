package com.ecommerce.grupo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.Adapter.SliderAdapter;
import com.ecommerce.grupo.Adapter.SliderAdapterExample;
import com.ecommerce.grupo.Model.SliderItem;
import com.ecommerce.grupo.Model.UserModel;
import com.ecommerce.grupo.fragments.BagFragment;
import com.ecommerce.grupo.fragments.WishListFragment;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.BulkPrices;
import com.ecommerce.grupo.pojo.Data;
import com.ecommerce.grupo.pojo.Enquiry;
import com.ecommerce.grupo.pojo.Img;
import com.ecommerce.grupo.pojo.MerchantData;
import com.ecommerce.grupo.pojo.StoreUrl;
import com.ecommerce.grupo.pojo.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    ConstraintLayout merchantDetails;
    ImageView back,shareButton,lock;
    TextView title,description,bp1Tv,bp2Tv,bp3Tv,productPrice;
    TextView tv1049,tv5099,tv100200,tvQuantity,tvPrice;
    Button enquiryButton;
    EditText enquiryEdit;
    APIInterface apiInterface;
    SliderView sliderView;
    SliderAdapter adapter;
    String pri;
    String sa;
    ImageView bagImage,wishListImage;
    String imgUrl1,imgUrl2, imgUrl3;
    String userName,userPhone,merchantNam,merchantPhon,merchantAddre;
    ArrayList<UserModel> userModels;
    TextView merchantName,merchantPhone,merchantCompanyName,merchantAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        merchantDetails = findViewById(R.id.merchant_details);
        lock = findViewById(R.id.lock);
        SharedPreferences sp1 = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id1 = sp1.getInt("id",0);
        SharedPreferences sp2 = getSharedPreferences("MyUserLockStatus",MODE_PRIVATE);
        String lockStatus = sp2.getString("lockStatus","");
        if (lockStatus.equals("unlocked")){
            merchantDetails.setVisibility(View.VISIBLE);
            lock.setVisibility(View.GONE);
        }else {
            merchantDetails.setVisibility(View.GONE);
            lock.setVisibility(View.VISIBLE);
        }
        bagImage = findViewById(R.id.imageView3);
        bagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this,HomeActivity.class);
                intent.putExtra("bottom","bag");
                startActivity(intent);
            }
        });
        wishListImage = findViewById(R.id.wishlist_image);
        wishListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ProductActivity.this,HomeActivity.class);
               intent.putExtra("bottom","wishList");
               startActivity(intent);
            }
        });
        merchantName = findViewById(R.id.merchantName);
        merchantCompanyName = findViewById(R.id.merchantCompanyName);
        merchantAddress = findViewById(R.id.merchantAddress);
        merchantPhone = findViewById(R.id.merchantPhone);
        enquiryEdit = findViewById(R.id.enquiry_edit);
        enquiryButton = findViewById(R.id.enquiry);
        productPrice = findViewById(R.id.textView16);
        back = findViewById(R.id.imageView4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductActivity.super.onBackPressed();
            }
        });
        description = findViewById(R.id.description);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        int id = getIntent().getIntExtra("id",0);
        bp1Tv = findViewById(R.id.bp1);
        bp2Tv = findViewById(R.id.bp2);
        bp3Tv = findViewById(R.id.bp3);
        tv1049 =findViewById(R.id.textView17);
        tv5099 = findViewById(R.id.textView18);
        tv100200 = findViewById(R.id.textView22);
        tvQuantity = findViewById(R.id.textView14);
        tvPrice = findViewById(R.id.textView15);
        title = findViewById(R.id.textView12);
        Call<Data> call = apiInterface.getProductDetails(id);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    sa = response.body().getTitle();
                    title.setText(sa);
                    String as = response.body().getDescription();
                    description.setText(as);
                    Img img = response.body().getImg();
                    imgUrl1 = img.get1();
                    imgUrl2 = img.get2();
                    imgUrl3 = img.get3();
                    BulkPrices bulkPrices = response.body().getBulkPrices();
                    String bp1 = String.valueOf(bulkPrices.get_1049());
                    String bp2 = String.valueOf(bulkPrices.get_5099());
                    String bp3 = String.valueOf(bulkPrices.get_100200());
                    if (!bp1.equals("null")){
                        bp1Tv.setText(bp1+"₹");
                        bp2Tv.setText(bp2+"₹");
                        bp3Tv.setText(bp3+"₹");
                        float mrp = response.body().getMaximumRetailPrice();
                        enquiryEdit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }
                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if (charSequence.length()>0){
                                    productPrice.setVisibility(View.VISIBLE);
                                    int quant = Integer.parseInt(enquiryEdit.getText().toString());
                                    if (quant<10){
                                        int intRate = (int) mrp;
                                        int asss = quant*intRate;
                                        pri = String.valueOf(asss);
                                        productPrice.setText("Product Price: ₹"+pri);
                                    }else if (quant>=10 & quant<49){
                                        int asssss = quant*bulkPrices.get_1049();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Product Price: ₹"+pri);
                                    }else if (quant>=50 & quant<99){
                                        int asssss = quant*bulkPrices.get_5099();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Product Price: ₹"+pri);
                                    }else if (quant>=100 & quant<200){
                                        int asssss = quant*bulkPrices.get_100200();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Product Price: ₹"+pri);
                                    }else{
                                        int asssss = quant*bulkPrices.get_100200();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Product Price: ₹"+pri);
                                    }
                                }else if (charSequence.length()==0){
                                    productPrice.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }else {
                        float mrp = response.body().getMaximumRetailPrice();
                        tvQuantity.setText("Maximum Retail Price(MRP) : "+mrp+"₹\nNote : There is no Bulk price for this product.");
                        tvPrice.setVisibility(View.GONE);
                        tv1049.setVisibility(View.GONE);
                        tv5099.setVisibility(View.GONE);
                        tv100200.setVisibility(View.GONE);
                        bp1Tv.setVisibility(View.GONE);
                        bp2Tv.setVisibility(View.GONE);
                        bp3Tv.setVisibility(View.GONE);
                        enquiryEdit.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if (charSequence.length()>0){
                                    productPrice.setVisibility(View.VISIBLE);
                                    int quant = Integer.parseInt(enquiryEdit.getText().toString());
                                    int intRate = (int) mrp;
                                    int asss = quant*intRate;
                                    pri = String.valueOf(asss);
                                    productPrice.setText("Product Price: ₹"+pri);
                                }else if (charSequence.length()==0){
                                    productPrice.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }
                    int merchantId = response.body().getMerchantId();
                    Call<MerchantData> call1 = apiInterface.getMerchantDetails(merchantId);
                    call1.enqueue(new Callback<MerchantData>() {
                        @Override
                        public void onResponse(Call<MerchantData> call, Response<MerchantData> response) {
                            if (response.isSuccessful()){
                                String companyName = response.body().getCompanyName();
                                int userId = response.body().getUserId();
                                Call<UserModel> userModelCall = apiInterface.getUserData(userId);
                                userModelCall.enqueue(new Callback<UserModel>() {
                                    @Override
                                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                                        if (response.isSuccessful()){
                                            userModels = response.body().data;
                                            for (int i = 0; i < userModels.size(); i++) {
                                                merchantName.setText("Name : "+ userModels.get(i).getName());
                                                merchantNam = userModels.get(i).getName();
                                                merchantCompanyName.setText("Shop Name: "+companyName);
                                                merchantPhon = userModels.get(i).getMobileNumber();
                                                merchantPhone.setVisibility(View.GONE);
                                                /*merchantPhone.setText("Phone Number: "+userModels.get(i).getMobileNumber());*/
                                                merchantAddress.setText("Address : "+userModels.get(i).address1+userModels.get(i).city+userModels.get(i).getState()+" - "+userModels.get(i).getPostalCode());
                                                merchantAddre = userModels.get(i).address1+userModels.get(i).city+userModels.get(i).getState()+" - "+userModels.get(i).getPostalCode();
                                                getUserDetails(id1);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UserModel> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<MerchantData> call, Throwable t) {

                        }
                    });
                    //////////////////Adapter
                    sliderView = findViewById(R.id.imageProductSlider);
                    adapter = new SliderAdapter(ProductActivity.this);
                    try {
                        renewItems(imgUrl1,imgUrl2,imgUrl3);
                    }catch (Exception e){
                        Toast.makeText(ProductActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    sliderView.setSliderAdapter(adapter);
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
                    sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    sliderView.setIndicatorSelectedColor(Color.WHITE);
                    sliderView.setIndicatorUnselectedColor(Color.GRAY);
                    sliderView.setAutoCycle(false);

                    sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
                        @Override
                        public void onIndicatorClicked(int position) {
                            Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
                        }
                    });
                    //////////////////Adapter
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
        SharedPreferences sp = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int userId = sp.getInt("id",0);
        shareButton = findViewById(R.id.share_button);
        enquiryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enquiryEdit.getText().toString().equals("")){
                    Toast.makeText(ProductActivity.this, "Please enter a valid Quantity!", Toast.LENGTH_SHORT).show();
                    enquiryEdit.requestFocus();
                }else{
                    String quantity = enquiryEdit.getText().toString();
                    Enquiry enquiry = new Enquiry(userName,userPhone,sa,id,pri,merchantNam,merchantPhon,merchantAddre,Integer.parseInt(quantity),userId);
                    Call<Enquiry> call = apiInterface.sendEnquiry(enquiry);
                    call.enqueue(new Callback<Enquiry>() {
                        @Override
                        public void onResponse(Call<Enquiry> call, Response<Enquiry> response) {
                            if (response.isSuccessful()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                                builder.setMessage(response.body().getData())
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_baseline_auto_awesome_24)
                                        .show();
                            }else {
                                Toast.makeText(ProductActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Enquiry> call, Throwable t) {
                            Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<StoreUrl> storeUrlCall = apiInterface.getUrl();
                storeUrlCall.enqueue(new Callback<StoreUrl>() {
                    @Override
                    public void onResponse(Call<StoreUrl> call, Response<StoreUrl> response) {
                        if (response.isSuccessful()){
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            saveToSharedPreferences();
                            intent.setType("text/plain");
                            String shareBody = "titt";
                            String shareSub = sa+"\nTo view the product please download our app.\n\n" +
                                    "Grupo connects manufacturers and wholesalers. It's a platform where any individual/business can buy inventory directly from wholesalers hassle free.\n" +
                                    "\n" +
                                    "Download the app here for free \uD83D\uDE80\n\n"+response.body().getUrl();
                            /*Uri uri = Uri.parse(imgUrl1);
                            intent.putExtra(Intent.EXTRA_STREAM,uri);
                            intent.setType("image/jpeg");
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);*/
                            intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                            intent.putExtra(Intent.EXTRA_TEXT,shareSub);
                            startActivity(Intent.createChooser(intent,"Share product using"));
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreUrl> call, Throwable t) {

                    }
                });
            }
        });
    }

    private void getUserDetails(int id1) {
        Call<UserModel> userDataCall = apiInterface.getUserData(id1);
        userDataCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    userModels = response.body().data;
                    for (int i = 0; i < userModels.size(); i++) {
                        userName = userModels.get(i).getName();
                        userPhone = userModels.get(i).getMobileNumber();
                    }
                }else {
                    Toast.makeText(ProductActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void renewItems(String img1,String img2,String img3) {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 3; i++) {
            SliderItem sliderItem = new SliderItem();
            if (i == 0) {
                sliderItem.setImageUrl(String.valueOf(img1));
            } else if (i==1){
                sliderItem.setImageUrl(img2);
            }else if (i==2){
                sliderItem.setImageUrl(img3);
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }
    private void saveToSharedPreferences() {
        SharedPreferences sp = getSharedPreferences("MyUserLockStatus",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sp.edit();
        myEdit.putString("lockStatus","unlocked");
        myEdit.commit();
    }
}