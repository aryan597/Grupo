package com.ecommerce.grupo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.ecommerce.grupo.Adapter.ColorAdapter;
import com.ecommerce.grupo.Adapter.ProductAdapter;
import com.ecommerce.grupo.Adapter.SliderAdapter;
import com.ecommerce.grupo.Model.ProductModel;
import com.ecommerce.grupo.Model.SliderItem;
import com.ecommerce.grupo.Model.UserModel;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AddtoBag;
import com.ecommerce.grupo.pojo.BulkPrices;
import com.ecommerce.grupo.pojo.Data;
import com.ecommerce.grupo.pojo.Img;
import com.ecommerce.grupo.pojo.MerchantData;
import com.ecommerce.grupo.pojo.Product;
import com.ecommerce.grupo.pojo.StoreUrl;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    ConstraintLayout merchantDetails;
    ImageView back,shareButton;
    TextView title,description,bp1Tv,bp2Tv,bp3Tv,productPrice;
    TextView colorTv,gsmTv,wspTv,skuTv,packTv;
    RecyclerView sizesRecyclerView;
    TextView tv1049,tv5099,tv100200,tvQuantity,tvPrice,priceTv;
    Button addToBag,buyNow;
    EditText enquiryEdit;
    APIInterface apiInterface;
    SliderView sliderView;
    SliderAdapter adapter;
    ProductAdapter productAdapter;
    RecyclerView moreItemsRecyclerView;
    int id;
    ArrayList<String> sizes;
    ImageSlider imageSlider;
    ImageView priceInfo;
    String pri;
    ArrayList<ProductModel> productModels = new ArrayList<>();
    String sa,userNameString,phoneNumberString,emailAddressString;
    ImageView bagImage;
    String imgUrl1,imgUrl2, imgUrl3;
    String pcId,cId;
    String userName,userPhone,merchantNam,merchantPhon,merchantAddre;
    ArrayList<UserModel> userModels;
    int productId;
    TextView businessName,merchantName,merchantCompanyName,merchantGst,maxRetailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        SharedPreferences sp1 = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id1 = sp1.getInt("id",0);
        imageSlider = findViewById(R.id.image_slider);
        pcId = getIntent().getStringExtra("pcId");
        cId = getIntent().getStringExtra("cId");
        //////lock status
        /*SharedPreferences sp2 = getSharedPreferences("MyUserLockStatus",MODE_PRIVATE);
        String lockStatus = sp2.getString("lockStatus","");
        if (lockStatus.equals("unlocked")){
            merchantDetails.setVisibility(View.VISIBLE);
            lock.setVisibility(View.GONE);
        }else {
            merchantDetails.setVisibility(View.GONE);
            lock.setVisibility(View.VISIBLE);
        }*/
        //////lock status
        addToBag = findViewById(R.id.add_to_cart);
        addToBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AddtoBag addtoBag = new AddtoBag(id1, productId);
                    Call<AddtoBag> addtoBagCall = apiInterface.addToBag(addtoBag);
                    addtoBagCall.enqueue(new Callback<AddtoBag>() {
                        @Override
                        public void onResponse(Call<AddtoBag> call, Response<AddtoBag> response) {
                            if (response.isSuccessful()) {
                                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                vibe.vibrate(200);
                                Toast.makeText(ProductActivity.this, "Added to Bag successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProductActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ProductActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddtoBag> call, Throwable t) {
                            Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                ////custom alert dialog
                /*AlertDialog.Builder builder = new AlertDialog.Builder(ProductActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.customview, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                dialogView.findViewById(R.id.save_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });*/
            }
        });
       /* wishListImage = findViewById(R.id.wishlist_image);
        wishListImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ProductActivity.this,HomeActivity.class);
               intent.putExtra("bottom","wishList");
               startActivity(intent);
            }
        });*/
        back = findViewById(R.id.imageView4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductActivity.super.onBackPressed();
            }
        });
        description = findViewById(R.id.textView26);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        id = getIntent().getIntExtra("id",0);
        title = findViewById(R.id.textView12);
        /*Call<Product> productCall = apiInterface.doGetProduct(Integer.parseInt(pcId), Integer.parseInt(cId));
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                productModels = response.body().productModels;
                if (productModels.isEmpty()) {
                    moreItemsRecyclerView.setVisibility(View.GONE);
                } else {
                    moreItemsRecyclerView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < productModels.size(); i++) {
                        productAdapter = new ProductAdapter(productModels, getApplicationContext(),Integer.parseInt(pcId), Integer.parseInt(cId));
                        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                        moreItemsRecyclerView.setLayoutManager(manager);
                        moreItemsRecyclerView.setAdapter(productAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });*/
        Call<Data> call = apiInterface.getProductDetails(id);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    productId = (int) response.body().getId();
                    sa = response.body().getTitle();
                    title.setText(sa);
                    sizes = new ArrayList<>();
                    sizes = response.body().getSizes();
                    sizesRecyclerView = findViewById(R.id.recyclerView2);
                    ColorAdapter colorAdapter = new ColorAdapter(sizes, getApplicationContext());
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
                    sizesRecyclerView.setLayoutManager(manager);
                    sizesRecyclerView.setAdapter(colorAdapter);
                    colorTv = findViewById(R.id.color);
                    gsmTv = findViewById(R.id.gsm);
                    wspTv = findViewById(R.id.wsp);
                    skuTv = findViewById(R.id.sku);
                    packTv = findViewById(R.id.pack);
                    colorTv.setText("Color : "+response.body().getColor());
                    gsmTv.setText("GSM : "+response.body().getGsm());
                    wspTv.setText("WSP : "+response.body().getWsp());
                    skuTv.setText("SKU : "+response.body().getSku());
                    packTv.setText("Pack : "+sizes.size());
                    priceTv = findViewById(R.id.price);
                    double totalPrice = response.body().getPrice()*sizes.size()+0.07*response.body().getPrice();
                    priceTv.setText("Rs. "+totalPrice);
                    String as = response.body().getDescription();
                    description.setText(as);
                    Img img = response.body().getImg();
                    imgUrl1 = img.get1();
                    imgUrl2 = img.get2();
                    imgUrl3 = img.get3();
                    ArrayList<SlideModel> slideModels = new ArrayList<>();
                    slideModels.add(new SlideModel(imgUrl1, ScaleTypes.CENTER_INSIDE));
                    slideModels.add(new SlideModel(imgUrl2, ScaleTypes.CENTER_INSIDE));
                    slideModels.add(new SlideModel(imgUrl3, ScaleTypes.CENTER_INSIDE));
                    imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);
                    imageSlider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int i) {
                            Intent imageIntent = new Intent(ProductActivity.this, OpenImageActivity.class);
                            imageIntent.putExtra("imageUrl", slideModels.get(i).getImageUrl());
                            startActivity(imageIntent);
                        }
                    });
                    /*BulkPrices bulkPrices = response.body().getBulkPrices();
                    String bp1 = String.valueOf(bulkPrices.get_1049());
                    String bp2 = String.valueOf(bulkPrices.get_5099());
                    String bp3 = String.valueOf(bulkPrices.get_100200());
                    if (!bp1.equals("null")){
                        maxRetailTv.setText("Retail price : ₹"+(int) response.body().getMaximumRetailPrice());
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
                                        productPrice.setText("Estimated Price: ₹"+pri);
                                    }else if (quant>=10 & quant<=49){
                                        int asssss = quant*bulkPrices.get_1049();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Estimated Price: ₹"+pri);
                                    }else if (quant>=50 & quant<=99){
                                        int asssss = quant*bulkPrices.get_5099();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Estimated Price: ₹"+pri);
                                    }else if (quant>=100 & quant<=200){
                                        int asssss = quant*bulkPrices.get_100200();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Estimated Price: ₹"+pri);
                                    }else{
                                        int asssss = quant*bulkPrices.get_100200();
                                        pri = String.valueOf(asssss);
                                        productPrice.setText("Estimated Price: ₹"+pri);
                                    }
                                }else if (charSequence.length()==0){
                                    productPrice.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                    }
                    else {
                        float mrp = response.body().getMaximumRetailPrice();
                        maxRetailTv.setText("Retail price : ₹"+(int) response.body().getMaximumRetailPrice());
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
                    }*/
                    /*int merchantId = response.body().getMerchantId();
                    Call<MerchantData> call1 = apiInterface.getMerchantDetails(merchantId);
                    call1.enqueue(new Callback<MerchantData>() {
                        @Override
                        public void onResponse(Call<MerchantData> call, Response<MerchantData> response) {
                            if (response.isSuccessful()){
                                String companyName = response.body().getCompanyName();
                                businessName = findViewById(R.id.business_name);
                                businessName.setText(companyName);
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
                    });*/
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
        Call<UserModel> userDataCall = apiInterface.getUserData(userId);
        userDataCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    userModels = response.body().data;
                    for (int i = 0; i < userModels.size(); i++) {
                      userNameString = userModels.get(i).getName();
                      phoneNumberString = userModels.get(i).getMobileNumber();
                      emailAddressString = userModels.get(i).getEmail();
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
            }
        });
        shareButton = findViewById(R.id.share_button);
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
                           /* Uri uri = Uri.parse(imgUrl1);
                            intent.putExtra(Intent.EXTRA_STREAM,uri);*/
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                            intent.putExtra(Intent.EXTRA_TEXT,shareSub);
                            startActivity(Intent.createChooser(intent,"Share product using"));
                        }else {
                            Toast.makeText(ProductActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreUrl> call, Throwable t) {
                        Toast.makeText(ProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        /*buyNow =findViewById(R.id.buy_now);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (enquiryEdit.getText().toString().equals("")){
                   enquiryEdit.setError("Please enter the quantity you want.");
                   enquiryEdit.requestFocus();
               }else if (Integer.parseInt(enquiryEdit.getText().toString())<10){
                   Toast.makeText(ProductActivity.this, "You have to order more than (or) equal to 10 products.", Toast.LENGTH_SHORT).show();
               }else{
                   if (Integer.parseInt(productPrice.getText().toString().replace("Estimated Price: ₹",""))<10000){
                       //cod available
                       Intent intent = new Intent(ProductActivity.this,CheckoutGrupoActivity.class);
                       intent.putExtra("isCod",true);
                       intent.putExtra("name",userNameString);
                       intent.putExtra("quantity",enquiryEdit.getText().toString());
                       intent.putExtra("phoneNumber",phoneNumberString);
                       intent.putExtra("email",emailAddressString);
                       intent.putExtra("productId",id);
                       intent.putExtra("productPrice",Integer.parseInt(productPrice.getText().toString().replace("Estimated Price: ₹","")));
                       startActivity(intent);
                   }else{
                       //cod not available
                       Intent intent = new Intent(ProductActivity.this,CheckoutGrupoActivity.class);
                       intent.putExtra("isCod",false);
                       intent.putExtra("name",userNameString);
                       intent.putExtra("quantity",enquiryEdit.getText().toString());
                       intent.putExtra("productId",id);
                       intent.putExtra("productPrice",Integer.parseInt(productPrice.getText().toString().replace("Estimated Price: ₹","")));
                       startActivity(intent);
                   }
               }
            }
        });*/
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