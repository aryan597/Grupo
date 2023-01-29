package com.ecommerce.grupo;



import com.ecommerce.grupo.Model.EnquiryModel;
import com.ecommerce.grupo.Model.UserModel;
import com.ecommerce.grupo.pojo.AddtoBag;
import com.ecommerce.grupo.pojo.AllProductsPojo;
import com.ecommerce.grupo.pojo.Category;
import com.ecommerce.grupo.pojo.CreateOrder;
import com.ecommerce.grupo.pojo.CreateUser;
import com.ecommerce.grupo.pojo.Data;
import com.ecommerce.grupo.pojo.Enquiry;
import com.ecommerce.grupo.pojo.GetCartItems;
import com.ecommerce.grupo.pojo.GetCategory;
import com.ecommerce.grupo.pojo.GetOrder;
import com.ecommerce.grupo.pojo.ImgGet;
import com.ecommerce.grupo.pojo.Logout;
import com.ecommerce.grupo.pojo.MerchantData;
import com.ecommerce.grupo.pojo.MultipleResource;
import com.ecommerce.grupo.pojo.ParentCategory;
import com.ecommerce.grupo.pojo.PatchAddress;
import com.ecommerce.grupo.pojo.PatchUserData;
import com.ecommerce.grupo.pojo.PostAddress;
import com.ecommerce.grupo.pojo.PostRequirements;
import com.ecommerce.grupo.pojo.Product;
import com.ecommerce.grupo.pojo.RemoveCartItems;
import com.ecommerce.grupo.pojo.StoreUrl;
import com.ecommerce.grupo.pojo.User;
import com.ecommerce.grupo.pojo.UserAddress;
import com.ecommerce.grupo.pojo.UserOtp;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIInterface {

    /*@GET("/api/user")
    Call<MultipleResource> doGetListResources();*/

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/user/{id}")
    Call<UserModel> getUserData(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/user/{id}/orders")
    Call<GetOrder> getOrdersById(@Path("id") int id);

    @GET("/api/parentCategory")
    Call<ParentCategory> getParentCategory();

    @GET("/api/parentCategory/{id}/category")
    Call<GetCategory> getCategory(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/address/user/{id}")
    Call<ArrayList<UserAddress>> getUserAddress(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/address/{id}")
    Call<UserAddress> getParticularAddress(@Path("id") int id);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/product?take=200&skip=0")
    Call<AllProductsPojo> getAllProducts();

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/product?take=5&skip=50")
    Call<AllProductsPojo> getAllProductsTodaysChoice();

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @POST("/api/order")
    Call<CreateOrder> createOrder(@Body CreateOrder createOrder);

    @Multipart
    @POST("/api/upload")
    Call<ImgGet> uploadFile(@Part MultipartBody.Part photo);


    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/product?take=5&skip=30")
    Call<AllProductsPojo> getAllProductsTrending();

    @POST("/api/address")
    Call<PostAddress> postAddress(@Body PostAddress postAddress);

    @POST("/api/requirement")
    Call<PostRequirements> postRequirements(@Body PostRequirements postRequirements);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @GET("/api/user/{id}/cart")
    Call<GetCartItems> getCartItems(@Path("id")int id);

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
    @PATCH("/api/address/{id}")
    Call<PatchAddress> patchAddress(@Path("id") int id,@Body PatchAddress patchAddress);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @DELETE("/api/address/{id}")
    Call<UserAddress> deleteAddress(@Path("id") int id);

    @GET("/api/parentcategory?take=100&skip=0")
    Call<MultipleResource> doGetListResources();

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @POST("/api/cart/remove")
    Call<RemoveCartItems> deleteCart(@Body RemoveCartItems removeCartItems);

    @Headers({"Cookie"+":"+"session=eyJwYXNzcG9ydCI6eyJ1c2VyIjoiKzkxOTM5ODQ1NzE0MyJ9fQ==; session.sig=fuCLxuyYNKJCVdiNu-iALgMjYU4"})
    @POST("/api/cart/add")
    Call<AddtoBag> addToBag(@Body AddtoBag addtoBag);

    @DELETE("/api/enquiry/{id}")
    Call<Enquiry> deleteEnquiry(@Path("id") int id);

    @GET("/api/store")
    Call<StoreUrl> getUrl();

    @GET("/api/parentcategory/{id}/category?take=100&skip=0")
    Call<Category> doGetCategory(@Path("id") int id);

    @GET("/api/category")
    Call<Category> getCategory();

    @GET("/api/category")
    Call<MultipleResource> getCategory1();

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
