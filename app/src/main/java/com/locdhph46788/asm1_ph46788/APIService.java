package com.locdhph46788.asm1_ph46788;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    String DOMAIN = "http://192.168.1.44:3000/";

    @GET("/api/list")
    Call<List<CarModel>> getCars();

    @POST("/api/add_car")
    Call<List<CarModel>> addCar();

    @DELETE("/api/del_car")
    Call<List<CarModel>> delCar();
}
