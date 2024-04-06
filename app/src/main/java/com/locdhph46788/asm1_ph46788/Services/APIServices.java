package com.locdhph46788.asm1_ph46788.Services;

import com.locdhph46788.asm1_ph46788.Model.CarModel;
import com.locdhph46788.asm1_ph46788.Model.Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServices {
    public static String DOMAIN = "http://192.168.1.44:3000/api/";

    @GET("list")
    Call<Response<List<CarModel>>> getCars();

    @POST("add_car")
    Call<List<CarModel>> addCar(@Body CarModel car);

    @DELETE("del_car/{id}")
    Call<List<CarModel>> delCar(@Path("id") String id);

    @PUT("update_car/{id}")
    Call<List<CarModel>> updateCar(@Path("id") String id, @Body CarModel car);

    @GET("search_car")
    Call<Response<List<CarModel>>> searchCar(@Query("key") String key);

    @GET("sort_by_price")
    Call<Response<List<CarModel>>> getCarsSort(@Query("order") String order);
}
