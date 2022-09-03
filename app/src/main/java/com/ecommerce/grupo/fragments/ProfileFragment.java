package com.ecommerce.grupo.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecommerce.grupo.APIInterface;
import com.ecommerce.grupo.AboutUsActivity;
import com.ecommerce.grupo.Adapter.AllProductsAdapter;
import com.ecommerce.grupo.EditProfileActivity;
import com.ecommerce.grupo.EnquiriesActivity;
import com.ecommerce.grupo.HelpActivity;
import com.ecommerce.grupo.HomeActivity;
import com.ecommerce.grupo.MainActivity;
import com.ecommerce.grupo.Model.AllProducts;
import com.ecommerce.grupo.Model.UserModel;
import com.ecommerce.grupo.OrdersActivity;
import com.ecommerce.grupo.R;
import com.ecommerce.grupo.ShippingAddressActivity;
import com.ecommerce.grupo.SplashActivity;
import com.ecommerce.grupo.SuggestionsActivity;
import com.ecommerce.grupo.TermsActivity;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.Logout;
import com.ecommerce.grupo.pojo.User;
import com.ecommerce.grupo.pojo.UserData;
import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    ConstraintLayout orders,shippingAddress,aboutUs,help,terms,suggestions,enquiries;
    Button logout;
    APIInterface apiInterface;
    String email,gender;
    SharedPreferences sp;
    TextView name,number;
    ImageView profile;
    ArrayList<UserModel> userModels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        profile = view.findViewById(R.id.imageView7);
        name = view.findViewById(R.id.textView8);
        number = view.findViewById(R.id.phoneNumber);
        SharedPreferences sp1 = getContext().getSharedPreferences("MyUserId",MODE_PRIVATE);
        int id = sp1.getInt("id",0);
        Call<UserModel> userDataCall = apiInterface.getUserData(id);
        userDataCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    userModels = response.body().data;
                    for (int i = 0; i < userModels.size(); i++) {
                        name.setText(userModels.get(i).getName());
                        number.setText(userModels.get(i).getMobileNumber());
                        email = userModels.get(i).email;
                        gender = userModels.get(i).getGender();
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("phone",number.getText().toString());
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("phone",number.getText().toString());
                intent.putExtra("email",email);
                intent.putExtra("gender",gender);
                startActivity(intent);
            }
        });
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("phone",number.getText().toString());
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Logging Out...!");
        dialog.setCancelable(false);
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure that you want to Logout?")
                        .setCancelable(true)
                        .setTitle("Logout")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.show();
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        Logout logout1 = new Logout();
                                        Call<Logout> call = apiInterface.logoutUser();
                                        call.enqueue(new Callback<Logout>() {
                                            @Override
                                            public void onResponse(Call<Logout> call, Response<Logout> response) {
                                                Toast.makeText(getContext(), "Logged out Successfully", Toast.LENGTH_SHORT).show();
                                                if (response.isSuccessful()) {
                                                    sp = getContext().getSharedPreferences("MyUserLoggedInData", MODE_PRIVATE);
                                                    SharedPreferences.Editor myEdit = sp.edit();
                                                    myEdit.putString("loggedIn", "false");
                                                    myEdit.commit();
                                                    dialogInterface.dismiss();
                                                    dialog.dismiss();
                                                    startActivity(new Intent(getContext(), MainActivity.class));
                                                    getActivity().finish();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Logout> call, Throwable t) {

                                            }
                                        });
                                    }
                                }, 2000);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        orders = view.findViewById(R.id.my_orders);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrdersActivity.class));
            }
        });
        shippingAddress = view.findViewById(R.id.shipping_address);
        shippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ShippingAddressActivity.class));
            }
        });
        aboutUs = view.findViewById(R.id.about_us);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AboutUsActivity.class);
                startActivity(intent);
            }
        });
        help = view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HelpActivity.class));
            }
        });
        terms = view.findViewById(R.id.terms);
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TermsActivity.class);
                startActivity(intent);
            }
        });
        enquiries = view.findViewById(R.id.my_enquiries);
        enquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EnquiriesActivity.class);
                startActivity(intent);
            }
        });
        suggestions = view.findViewById(R.id.suggestions);
        suggestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString = "https://docs.google.com/forms/d/e/1FAIpQLSdfpm0rJz6BI7Yv86jxX0J2ru0L6Xnf5ZfTPgr4h6WgEhIQhQ/viewform";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
                /*Intent intent = new Intent(getContext(), SuggestionsActivity.class);
                startActivity(intent);*/
            }
        });
        return view;
    }
}