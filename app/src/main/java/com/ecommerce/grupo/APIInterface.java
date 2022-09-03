package com.ecommerce.grupo;



import com.ecommerce.grupo.Model.EnquiryModel;
import com.ecommerce.grupo.Model.UserModel;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.Category;
import com.ecommerce.grupo.pojo.CreateUser;
import com.ecommerce.grupo.pojo.Data;
import com.ecommerce.grupo.pojo.Enquiry;
import com.ecommerce.grupo.pojo.Logout;
import com.ecommerce.grupo.pojo.MerchantData;
import com.ecommerce.grupo.pojo.MultipleResource;
import com.ecommerce.grupo.pojo.PatchAddress;
import com.ecommerce.grupo.pojo.PatchUserData;
import com.ecommerce.grupo.pojo.Product;
import com.ecommerce.grupo.pojo.StoreUrl;
import com.ecommerce.grupo.pojo.User;
import com.ecommerce.grupo.pojo.UserAddress;
import com.ecommerce.grupo.pojo.UserOtp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {

    /*@GET("/api/user")
    Call<MultipleResource> doGetListResources();*/

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/user/{id}")
    Call<UserModel> getUserData(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/user/{id}")
    Call<UserAddress> getUserAddress(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/product")
    Call<AllProductsPojo> getAllProducts();

    @POST("/api/enquiry")
    Call<Enquiry> sendEnquiry(@Body Enquiry enquiry);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/merchantdetails/{id}")
    Call<MerchantData> getMerchantDetails(@Path("id") int id);

    @POST("/auth/verify-mobile")
    Call<User> sendOtp(@Body User user);

    @POST("api/user")
    Call<CreateUser> createUser(@Body CreateUser createUser);

    @POST("/auth/verify-mobile-otp")
    Call<UserOtp> verifyOtp(@Body UserOtp userOtp);

    @GET("/auth/logout")
    Call<Logout> logoutUser();

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @PATCH("/api/user/{id}")
    Call<PatchAddress> patchAddress(@Path("id") int id,@Body PatchAddress patchAddress);

    @GET("/api/parentcategory?take=100&skip=0")
    Call<MultipleResource> doGetListResources();

    @DELETE("/api/enquiry/{id}")
    Call<Enquiry> deleteEnquiry(@Path("id") int id);

    @GET("/api/store")
    Call<StoreUrl> getUrl();

    @GET("/api/parentcategory/{id}/category?take=100&skip=0")
    Call<Category> doGetCategory(@Path("id") int id);

    @GET("/api/parentcategory/{id}/category/{id1}/products?take=100&skip=0")
    Call<Product> doGetProduct(@Path("id") int id,@Path("id1") int id1);

    @GET("/api/product/{id}")
    Call<Data> getProductDetails(@Path("id") int id );

    @GET("/api/enquiry/{id}")
    Call<ArrayList<EnquiryModel>> getEnquiries(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @PATCH("/api/user/{id}")
    Call<PatchUserData> patchUserData(@Path("id") int id, @Body PatchUserData patchUserData);
}
