package com.gtech.retrofit;

import com.google.gson.JsonObject;
import com.gtech.retrofit.Response.ProductRes.ProductResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("get_all_products.php")
    Call<ProductResponse> post_getProduct(@Body JsonObject body);
/*
 @POST("get_all_products.php")
    Call<ProductResponse> post_getProduct(@Body HashMap<String, String> users);
*/

}
