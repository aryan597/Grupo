package com.ecommerce.grupo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ecommerce.grupo.fragments.BagFragment;
import com.ecommerce.grupo.fragments.CategoriesFragment;
import com.ecommerce.grupo.fragments.HomeFragment;
import com.ecommerce.grupo.fragments.ProfileFragment;
import com.ecommerce.grupo.fragments.WishListFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private static final int RC_APP_UPDATE = 100;
    BottomNavigationView bottomNavigationView;
    String bottom;
    FrameLayout container;
    ConstraintLayout noInternet;
    SwipeRefreshLayout refreshInternet;
    AppUpdateManager mAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLoginData();
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        mAppUpdateManager = AppUpdateManagerFactory.create(this);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<AppUpdateInfo>()
        {
            @Override
            public void onSuccess(AppUpdateInfo result)
            {
                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                {
                    try
                    {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, HomeActivity.this
                                ,RC_APP_UPDATE);

                    } catch (IntentSender.SendIntentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        //mAppUpdateManager.registerListener(installStateUpdatedListener);
        container = findViewById(R.id.container);
        noInternet  = findViewById(R.id.no_internet);
        if (checkConnection(HomeActivity.this)){
            container.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.GONE);
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottom = getIntent().getStringExtra("bottom");
            try {
                if (bottom.equals("wishList")){
                    bottomNavigationView.setSelectedItemId(R.id.saved);
                    openFragment(new WishListFragment());
                }else if (bottom.equals("bag")){
                    bottomNavigationView.setSelectedItemId(R.id.bag);
                    openFragment(new BagFragment());
                } else {
                    bottomNavigationView.setSelectedItemId(R.id.home);
                    openFragment(new HomeFragment());
                }
            }catch (Exception e){
                bottomNavigationView.setSelectedItemId(R.id.home);
                openFragment(new HomeFragment());
            }
        }else{
            container.setVisibility(View.GONE);
            noInternet.setVisibility(View.VISIBLE);
        }
        refreshInternet = findViewById(R.id.refresh_internet);
        refreshInternet.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkConnection(HomeActivity.this)){
                    refreshInternet.setRefreshing(false);
                    container.setVisibility(View.VISIBLE);
                    noInternet.setVisibility(View.GONE);
                }else{
                    refreshInternet.setRefreshing(false);
                    container.setVisibility(View.GONE);
                    noInternet.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (mBottomNavigationView.getSelectedItemId() == R.id.home)
        {
            super.onBackPressed();
            finish();
        }
        else
        {
            mBottomNavigationView.setSelectedItemId(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                openFragment(new HomeFragment());
                return true;
            case R.id.categories:
                openFragment(new CategoriesFragment());
                return true;
            case R.id.bag:
                openFragment(new BagFragment());
                return true;
            case R.id.saved:
                openFragment(new WishListFragment());
                return true;
            case R.id.profile:
                openFragment(new ProfileFragment());
                return true;
            default:
                break;
        }
        return false;
    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_left_to_right, 0,
                R.anim.enter_left_to_right, 0);
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
    private void loadLoginData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserLoggedInData", MODE_PRIVATE);
        if (!sharedPreferences.equals(null)){
            String loggedIn = sharedPreferences.getString("loggedIn","");
            if (loggedIn.equals("true")){

            }else if (loggedIn.equals("false")){
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();
            }else {
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
                finish();
            }
        }
    }
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }
    @Override
    protected void onStop()
    {
        //if(mAppUpdateManager!=null) mAppUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<AppUpdateInfo>()
        {
            @Override
            public void onSuccess(AppUpdateInfo result)
            {
                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
                {
                    try
                    {
                        mAppUpdateManager.startUpdateFlowForResult(result,AppUpdateType.IMMEDIATE, HomeActivity.this
                                ,RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private InstallStateUpdatedListener installStateUpdatedListener =new InstallStateUpdatedListener()
    {
        @Override
        public void onStateUpdate(InstallState state)
        {
            if(state.installStatus() == InstallStatus.DOWNLOADED)
            {
                showCompletedUpdate();
            }
        }
    };
    private void showCompletedUpdate()
    {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"New app is ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAppUpdateManager.completeUpdate();
            }
        });
        snackbar.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
    /* we can check without requestCode == RC_APP_UPDATE because
    we known exactly there is only requestCode from  startUpdateFlowForResult() */
        if(requestCode == RC_APP_UPDATE && resultCode != RESULT_OK)
        {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}