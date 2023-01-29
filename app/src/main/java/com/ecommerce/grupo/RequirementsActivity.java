package com.ecommerce.grupo;

import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ParentCategoryModel;
import com.ecommerce.grupo.pojo.APIClient;
import com.ecommerce.grupo.pojo.GetCategory;
import com.ecommerce.grupo.pojo.Img;
import com.ecommerce.grupo.pojo.ImgGet;
import com.ecommerce.grupo.pojo.ParentCategory;
import com.ecommerce.grupo.pojo.PostRequirements;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequirementsActivity extends AppCompatActivity {
    APIInterface apiInterface;
    Spinner category,subcategory;
    Button submit;
    ImageView img1,img2,img3,back,orders;
    EditText productName,productDescription,quantity;
    ArrayList<ParentCategoryModel> parentCategoryModelArrayList;
    ArrayList<CategoryModel> categoryModelArrayList;
    int subCategoryId,parentCategoryId;
    String url1="",url2="",url3="";
    String url;
    Uri results;
    String pathToFile;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirements);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.button));
        apiInterface = APIClient.getClient().create(APIInterface.class);
        category = findViewById(R.id.spinner_category);
        subcategory = findViewById(R.id.spinner_sub_category);
        back = findViewById(R.id.imageView9);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        orders = findViewById(R.id.imageView3);
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RequirementsActivity.this,HomeActivity.class);
                intent.putExtra("bottom","wishList");
                startActivity(intent);
            }
        });
        ArrayList<String> arrayList = new ArrayList<>();
        SharedPreferences sp1 = getSharedPreferences("MyUserId",MODE_PRIVATE);
        int userId = sp1.getInt("id",0);
        Call<ParentCategory> parentCategoryCall = apiInterface.getParentCategory();
        parentCategoryCall.enqueue(new Callback<ParentCategory>() {
            @Override
            public void onResponse(Call<ParentCategory> call, Response<ParentCategory> response) {
                if (response.isSuccessful()){
                    parentCategoryModelArrayList = response.body().data;
                    for (int i=0;i<parentCategoryModelArrayList.size();i++){
                        arrayList.add(parentCategoryModelArrayList.get(i).getTitle());
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, arrayList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        category.setAdapter(arrayAdapter);
                    }
                }
            }
            @Override
            public void onFailure(Call<ParentCategory> call, Throwable t) {
                Toast.makeText(RequirementsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<String> subcategoryArrayList = new ArrayList<>();
                parentCategoryId = parentCategoryModelArrayList.get(i).getId();
                Call<GetCategory> getCategoryCall = apiInterface.getCategory(parentCategoryModelArrayList.get(i).getId());
                getCategoryCall.enqueue(new Callback<GetCategory>() {
                    @Override
                    public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                        categoryModelArrayList = response.body().data;
                        for (int i=0;i<categoryModelArrayList.size();i++){
                            subcategoryArrayList.add(categoryModelArrayList.get(i).getTitle());
                            ArrayAdapter<String> subcategoryAdapter = new ArrayAdapter<>(RequirementsActivity.this,android.R.layout.simple_spinner_item, subcategoryArrayList);
                            subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            subcategory.setAdapter(subcategoryAdapter);
                            subcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    subCategoryId = categoryModelArrayList.get(i).getId();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCategory> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        productName = findViewById(R.id.edit_business_name);
        productDescription = findViewById(R.id.edit_description);
        quantity = findViewById(R.id.editText4);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage1();
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage2();
            }
        });
        submit = findViewById(R.id.button2);
        try {
            String productNameString, img1S,img2S,img3S;
            productNameString = getIntent().getStringExtra("productName");
            img1S = getIntent().getStringExtra("img1");
            img2S = getIntent().getStringExtra("img2");
            img3S = getIntent().getStringExtra("img3");
            url1 = img1S;
            url2 = img2S;
            url3 = img3S;
            productName.setText(productNameString);
            Glide.with(RequirementsActivity.this).load(img1S).into(img1);
            Glide.with(RequirementsActivity.this).load(img2S).into(img2);
            Glide.with(RequirementsActivity.this).load(img3S).into(img3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productName.getText().toString().equals("")||productDescription.getText().toString().equals("")||quantity.getText().toString().equals("")){
                    Toast.makeText(RequirementsActivity.this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                }else {
                    if (url1.equals("")||url2.equals("")||url3.equals("")){
                        Toast.makeText(RequirementsActivity.this, "Please upload all 3 images", Toast.LENGTH_SHORT).show();
                    }else {
                        Img img = new Img();
                        img.set1(url1);
                        img.set2(url2);
                        img.set3(url3);
                        PostRequirements postRequirements = new PostRequirements(parentCategoryId, subCategoryId, productName.getText().toString(), productDescription.getText().toString(), Integer.parseInt(quantity.getText().toString()), img, userId);
                        Call<PostRequirements> postRequirementsCall = apiInterface.postRequirements(postRequirements);
                        postRequirementsCall.enqueue(new Callback<PostRequirements>() {
                            @Override
                            public void onResponse(Call<PostRequirements> call, Response<PostRequirements> response) {
                                if (response.isSuccessful()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RequirementsActivity.this);
                                    builder.setTitle("Thank you")
                                            .setMessage("Thank you for your requirement we will respond back to you shortly!")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                    startActivity(new Intent(RequirementsActivity.this, HomeActivity.class));
                                                    finish();
                                                }
                                            })
                                            .setCancelable(false)
                                            .show();
                                } else {
                                    Toast.makeText(RequirementsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PostRequirements> call, Throwable t) {
                                Toast.makeText(RequirementsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            results = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), results);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img1.setImageBitmap(bitmap);
            uploadFile(results, 1);
        }else if (requestCode == 50 && resultCode == RESULT_OK){
            results = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), results);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img2.setImageBitmap(bitmap);
            uploadFile(results, 2);
        }else if (requestCode == 20 && resultCode == RESULT_OK){
            results = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), results);
            } catch (IOException e) {
                e.printStackTrace();
            }
            img3.setImageBitmap(bitmap);
            uploadFile(results, 3);
        }
    }
    private void selectImage() {
        verifyStoragePermissions(RequirementsActivity.this);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_PICK);
        if (contentSelectionIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile!=null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(RequirementsActivity.this,"com.ecommerce.grupomerchant.fileprovider",photoFile);
                contentSelectionIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                contentSelectionIntent.setType("image/*");
                contentSelectionIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(contentSelectionIntent, 100);
            }
        }
    }
    private void selectImage1() {
        verifyStoragePermissions(RequirementsActivity.this);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_PICK);
        if (contentSelectionIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile!=null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(RequirementsActivity.this,"com.ecommerce.grupo.fileprovider",photoFile);
                contentSelectionIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                contentSelectionIntent.setType("image/*");
                contentSelectionIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(contentSelectionIntent, 50);
            }
        }
    }
    private void selectImage2() {
        verifyStoragePermissions(RequirementsActivity.this);
        Intent contentSelectionIntent = new Intent(Intent.ACTION_PICK);
        if (contentSelectionIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile!=null){
                pathToFile = photoFile.getAbsolutePath();
                Uri photoUri = FileProvider.getUriForFile(RequirementsActivity.this,"com.ecommerce.grupomerchant.fileprovider",photoFile);
                contentSelectionIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                contentSelectionIntent.setType("image/*");
                contentSelectionIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(contentSelectionIntent, 20);
            }
        }
    }
    private void uploadFile(Uri part_image,int im) {
        // Map is used to multipart the file using okhttp3.RequestBody
        /*try {
            InputStream inputStream = getContentResolver().openInputStream(url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File file = new File(url.getPath());
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse(""), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        Call call = apiInterface.uploadFile(fileToUpload);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddImageActivity.this, "Image Uploaded...!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AddImageActivity.this, "Image not Uploaded...!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(AddImageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error:",t.getMessage());
            }
        });*/
        String path = RealPathUtil.getRealPath(RequirementsActivity.this,part_image);
        File imageFile = new File(path);
        RequestBody reqBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData("file", imageFile.getName(), reqBody);
        Call<ImgGet> upload = apiInterface.uploadFile(partImage);
        upload.enqueue(new Callback<ImgGet>() {
            @Override
            public void onResponse(Call<ImgGet> call, Response<ImgGet> response) {
                if (response.isSuccessful()){
                    url = response.body().data;
                    if (im==1){
                        url1 = url;
                    }else if (im==2){
                        url2 = url;
                    }else if (im==3){
                        url3 = url;
                        submit.setVisibility(View.VISIBLE);
                    }
                }
                else if (response.code() == 413){
                    Toast.makeText(RequirementsActivity.this, "File too large!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ImgGet> call, Throwable t) {
                Toast.makeText(RequirementsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error",t.getMessage());
            }
        });
    }
    private File createImageFile(){
        File imageFile=null;
        String stamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File dir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imageFileName="JPEG_"+stamp+"_";
        try {
            imageFile=File.createTempFile(imageFileName,".jpg",dir);
        } catch (IOException e) {
            Log.d("YJW",e.getMessage());
        }
        return  imageFile;
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}