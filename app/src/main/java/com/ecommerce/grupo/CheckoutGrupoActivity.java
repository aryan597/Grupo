package com.ecommerce.grupo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.Adapter.AddressSelectionAdapter;
import com.ecommerce.grupo.Adapter.CartAdapter;
import com.ecommerce.grupo.Adapter.OrdersAdapter;
import com.ecommerce.grupo.Model.CartItems;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.BulkPrices;
import com.ecommerce.grupo.pojo.CreateOrder;
import com.ecommerce.grupo.pojo.Data;
import com.ecommerce.grupo.pojo.Img;
import com.ecommerce.grupo.pojo.UserAddress;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutGrupoActivity extends AppCompatActivity implements PaymentResultListener {
    String userName;
    int productPrice;
    TextView totalProductPrice, gst, orderTotal, productName, noPod;
    ImageView back, productImage;
    APIInterface apiInterface;
    Button proceed;
    int sum1 = 0;
    RecyclerView ordersRecyclerView;
    ArrayList<CartItems> cartItemsArrayList = new ArrayList<>();
    LinearLayout addressLayout;
    CartAdapter cartAdapter;
    RadioGroup radioGroup;
    ArrayList<UserAddress> addressModelArrayList;
    RadioButton pod, razor;
    TextView address, location, addressId,random;
    String phoneNumberString, emailAddressString;
    int productId, id, finalRate;
    double totalOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_grupo);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        userName = "Aryan";
        productPrice = getIntent().getIntExtra("productPrice", 0);
        totalProductPrice = findViewById(R.id.subtotal_price);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        random = findViewById(R.id.textView39);
        productName = findViewById(R.id.product_name);
        productImage = findViewById(R.id.product_image);
        gst = findViewById(R.id.gst);
        address = findViewById(R.id.textView41);
        location = findViewById(R.id.cityStatePincode);
        addressId = findViewById(R.id.address_id);
        orderTotal = findViewById(R.id.order_total);
        ordersRecyclerView = findViewById(R.id.recyclerView);
        productId = getIntent().getIntExtra("productId", 0);
        SharedPreferences sp = getSharedPreferences("MyUserId", MODE_PRIVATE);
        id = sp.getInt("id", 0);
        if (productId == 0){
            Bundle args = getIntent().getBundleExtra("BUNDLE");
            cartItemsArrayList = (ArrayList<CartItems>) args.getSerializable("ARRAYLIST");
            if (cartItemsArrayList.isEmpty()){
                Toast.makeText(this, "List empty", Toast.LENGTH_SHORT).show();
            }else {
                productName.setVisibility(View.GONE);
                productImage.setVisibility(View.GONE);
                ordersRecyclerView.setVisibility(View.VISIBLE);
                ConstraintLayout constraintLayout = findViewById(R.id.parentLayout);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.textView39, ConstraintSet.TOP, R.id.recyclerView, ConstraintSet.BOTTOM, 0);
                constraintSet.applyTo(constraintLayout);
                CartAdapter ordersAdapter = new CartAdapter(cartItemsArrayList, CheckoutGrupoActivity.this, id);
                ordersRecyclerView.setAdapter(ordersAdapter);
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                ordersRecyclerView.setLayoutManager(manager);
                for (int i=0;i<cartItemsArrayList.size();i++){
                    int quant = cartItemsArrayList.get(i).getUserQuantity();
                    int mrp = cartItemsArrayList.get(i).getMaximumRetailPrice();
                    int asss=0;
                    if (quant<10){
                        int intRate = (int) mrp;
                        asss = quant*intRate;
                    }else if (quant>=10 & quant<49){
                        asss = quant*cartItemsArrayList.get(i).getBulkPrices().get_1049();
                    }else if (quant>=50 & quant<99){
                        asss = quant*cartItemsArrayList.get(i).getBulkPrices().get_5099();
                    }else if (quant>=100 & quant<200){
                        asss = quant*cartItemsArrayList.get(i).getBulkPrices().get_100200();
                    }else{
                        asss = quant*cartItemsArrayList.get(i).getBulkPrices().get_100200();
                    }
                    sum1 += asss;
                }
                totalProductPrice = findViewById(R.id.subtotal_price);
                totalProductPrice.setText("₹ " + String.valueOf(sum1));
                double gstValue1 = 0.05 * sum1;
                gst.setText("₹ " + String.valueOf(gstValue1));
                totalOrder = sum1+gstValue1;
                orderTotal.setText("₹ " + String.valueOf(sum1+gstValue1));
            }
        }
        else {
            int productPrice = getIntent().getIntExtra("productPrice", 0);
            totalProductPrice.setText("₹ " + productPrice);
            double gstValue = 0.05 * productPrice;
            gst.setText("₹ " + gstValue);
            totalOrder = gstValue + productPrice;
            orderTotal.setText("₹ " + totalOrder);
            ordersRecyclerView.setVisibility(View.GONE);
            productName.setVisibility(View.VISIBLE);
            productImage.setVisibility(View.VISIBLE);
            Call<Data> call = apiInterface.getProductDetails(productId);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(Call<Data> call, Response<Data> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getTitle().length()>65) {
                            productName.setText(response.body().getTitle().substring(0, 65) + "...");
                        }else{
                            productName.setText(response.body().getTitle());
                        }
                        Img img = response.body().getImg();
                        String imgUrl1 = img.get1();
                        Glide.with(CheckoutGrupoActivity.this).load(imgUrl1).placeholder(R.drawable.placeholder).into(productImage);
                    } else {
                        Toast.makeText(CheckoutGrupoActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    Toast.makeText(CheckoutGrupoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        Call<ArrayList<UserAddress>> call2 = apiInterface.getUserAddress(id);
        call2.enqueue(new Callback<ArrayList<UserAddress>>() {
            @Override
            public void onResponse(Call<ArrayList<UserAddress>> call, Response<ArrayList<UserAddress>> response) {
                addressModelArrayList = response.body();
                if (addressModelArrayList.isEmpty()) {
                    //create new address
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutGrupoActivity.this);
                    builder.setTitle("Oops!")
                            .setMessage("We have found that you don't have any address added with you! Please add an address in your profile section.\nPress OK to go to add address section.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(CheckoutGrupoActivity.this,ShippingAddressActivity.class));
                                    finish();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    onBackPressed();
                                }
                            })
                            .setCancelable(false)
                            .show();
                } else {
                    for (int i = 0; i < addressModelArrayList.size(); i++) {
                        if (addressModelArrayList.get(i).getAddress().equals("")) {
                            location.setText("Add your Delivery Location");
                        } else {
                            /*if (addressModelArrayList.get(i).getAddress().length()>10){
                                address.setText(addressModelArrayList.get(i).getAddress().substring(0,10));
                            }else {
                                address.setText(addressModelArrayList.get(i).getAddress());
                            }*/
                            addressId.setText(addressModelArrayList.get(i).getId());
                            address.setText(addressModelArrayList.get(i).getAddress());
                            location.setText(addressModelArrayList.get(i).getCity() + ", " + addressModelArrayList.get(i).getState() + ", " + addressModelArrayList.get(i).getPostalCode());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserAddress>> call, Throwable t) {

            }
        });
        addressLayout = findViewById(R.id.linearLayout2);
        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(CheckoutGrupoActivity.this);
                dialog.setContentView(R.layout.address_selection_layout);
                RecyclerView addressSelectedDialog = dialog.findViewById(R.id.address_select_recyclerview);
                int data = 0;
                AddressSelectionAdapter addressSelectionAdapter = new AddressSelectionAdapter(addressModelArrayList, CheckoutGrupoActivity.this, dialog, data, address, location, addressId);
                addressSelectedDialog.setAdapter(addressSelectionAdapter);
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                addressSelectedDialog.setLayoutManager(manager);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Log.d("data", String.valueOf(data));
                    }
                });
            }
        });
        noPod = findViewById(R.id.no_pod);
        proceed = findViewById(R.id.proceed);
        proceed.setVisibility(View.GONE);
        //Gst will be 5% of the product
        phoneNumberString = getIntent().getStringExtra("phoneNumber");
        emailAddressString = getIntent().getStringExtra("email");
        radioGroup = findViewById(R.id.radio_group);
        pod = findViewById(R.id.pod);
        razor = findViewById(R.id.razor);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                proceed.setVisibility(View.VISIBLE);
                if (radioGroup.getCheckedRadioButtonId() == R.id.pod) {
                    proceed.setText("Confirm Order");
                } else {
                    proceed.setText("Proceed to pay");
                }
            }
        });
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Placing order!");
        dialog.setMessage("Please wait while we are placing your order.");
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.pod) {
                    dialog.show();
                    if (totalOrder < 10000) {
                        if (productId == 0) {
                            for (int i = 0; i < cartItemsArrayList.size(); i++) {
                                CreateOrder createOrder = new CreateOrder(id, cartItemsArrayList.get(i).getId(), cartItemsArrayList.get(i).getUserQuantity(), Integer.parseInt(String.valueOf(sum1)), "POD", "SOLO", Integer.parseInt(addressId.getText().toString()));
                                Call<CreateOrder> createOrderCall = apiInterface.createOrder(createOrder);
                                createOrderCall.enqueue(new Callback<CreateOrder>() {
                                    @Override
                                    public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                                        if (response.isSuccessful()) {
                                            dialog.dismiss();
                                        } else {
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CreateOrder> call, Throwable t) {
                                        dialog.dismiss();
                                        Toast.makeText(CheckoutGrupoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            Intent intent = new Intent(CheckoutGrupoActivity.this, OrderPlacedActivity.class);
                            intent.putExtra("addressId",Integer.parseInt(addressId.getText().toString()));
                            startActivity(intent);
                        }
                        else {
                            CreateOrder createOrder = new CreateOrder(id, productId, 15, (int) totalOrder, "POD", "SOLO", Integer.parseInt(addressId.getText().toString()));
                            Call<CreateOrder> createOrderCall = apiInterface.createOrder(createOrder);
                            createOrderCall.enqueue(new Callback<CreateOrder>() {
                                @Override
                                public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                                    if (response.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(CheckoutGrupoActivity.this, "Order placed.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CheckoutGrupoActivity.this, OrderPlacedActivity.class);
                                        intent.putExtra("addressId",Integer.parseInt(addressId.getText().toString()));
                                        intent.putExtra("productId", response.body().getProductId());
                                        intent.putExtra("orderId", Integer.parseInt(response.body().getId()));
                                        startActivity(intent);
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(CheckoutGrupoActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CreateOrder> call, Throwable t) {
                                    dialog.dismiss();
                                    Toast.makeText(CheckoutGrupoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("error",t.getMessage());
                                }
                            });
                        }
                    } else {
                        dialog.dismiss();
                        noPod.setVisibility(View.VISIBLE);
                    }
                } else {
                    noPod.setVisibility(View.GONE);
                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_live_tIk6WXayZWjjsK");
                    checkout.setImage(R.drawable.grupo_logo);
                    JSONObject object = new JSONObject();
                    try {
                        object.put("name", userName);
                        object.put("description", "Pay to Grupo");
                        object.put("theme.color", "#22A2F8");
                        object.put("currency", "INR");
                        object.put("amount", totalOrder * 100);
                        object.put("prefill.contact", phoneNumberString);
                        object.put("prefill.email", emailAddressString);
                        checkout.open(CheckoutGrupoActivity.this, object);
                        dialog.dismiss();
                    } catch (JSONException e) {
                        dialog.dismiss();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Payment Done");
        dialog.setMessage("You will be redirected to app Soon.");
        dialog.setCancelable(false);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (productId == 0) {
                    for (int i = 0; i < cartItemsArrayList.size(); i++) {
                        for (int i1 = 0; i1 < addressModelArrayList.size(); i1++) {
                            CreateOrder createOrder = new CreateOrder(id, cartItemsArrayList.get(i).getId(), cartItemsArrayList.get(i).getUserQuantity(), Integer.parseInt(String.valueOf(sum1)), "POD", "SOLO", Integer.parseInt(addressModelArrayList.get(i1).getId()));
                            Call<CreateOrder> createOrderCall = apiInterface.createOrder(createOrder);
                            createOrderCall.enqueue(new Callback<CreateOrder>() {
                                @Override
                                public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                                    if (response.isSuccessful()) {
                                        dialog.dismiss();
                                        Toast.makeText(CheckoutGrupoActivity.this, "Order placed.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CheckoutGrupoActivity.this, OrderPlacedActivity.class);
                                        intent.putExtra("productId", response.body().getProductId());
                                        intent.putExtra("orderId", Integer.parseInt(response.body().getId()));
                                        startActivity(intent);
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(CheckoutGrupoActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CreateOrder> call, Throwable t) {
                                    dialog.dismiss();
                                    Toast.makeText(CheckoutGrupoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
                else {
                    for (int i = 0; i < addressModelArrayList.size(); i++) {
                        CreateOrder createOrder = new CreateOrder(id, productId, 15, (int) totalOrder, "UPI", "SOLO", Integer.parseInt(addressModelArrayList.get(i).getId()));
                        Call<CreateOrder> createOrderCall = apiInterface.createOrder(createOrder);
                        createOrderCall.enqueue(new Callback<CreateOrder>() {
                            @Override
                            public void onResponse(Call<CreateOrder> call, Response<CreateOrder> response) {
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    Toast.makeText(CheckoutGrupoActivity.this, "Order placed.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CheckoutGrupoActivity.this, OrderPlacedActivity.class);
                                    intent.putExtra("productId", response.body().getProductId());
                                    intent.putExtra("orderId", Integer.parseInt(response.body().getId()));
                                    startActivity(intent);
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(CheckoutGrupoActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CreateOrder> call, Throwable t) {
                                dialog.dismiss();
                                Toast.makeText(CheckoutGrupoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }, 3000);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("razorpayError", s);
        if (s.equals("{\"error\":{\"code\":\"BAD_REQUEST_ERROR\",\"description\":\"Payment processing cancelled by user\",\"source\":\"customer\",\"step\":\"payment_authentication\",\"reason\":\"payment_cancelled\"}}")) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}