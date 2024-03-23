package com.locdhph46788.asm1_ph46788;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    String DOMAIN = "http://192.168.1.44:3000/";

    @GET("/api/list")
    Call<List<CarModel>> getCars();

    @POST("/api/add_car")
    Call<List<CarModel>> addCar(@Body CarModel car);

    @DELETE("/api/del_car/{id}")
    Call<List<CarModel>> delCar(@Path("id") String id);

    @PUT("/api/update_car/{id}")
    Call<List<CarModel>> updateCar(@Path("id") String id, @Body CarModel car);
}
