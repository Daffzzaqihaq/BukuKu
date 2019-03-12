package com.daffzzaqihaq.crudbuku.data.remote;

import com.daffzzaqihaq.crudbuku.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    // Membuat endpoint login
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    // Membuat endpointnya registerrr
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> registerUser(@Field("username")String username,
                                     @Field("password")String password,
                                     @Field("namauser")String namauser,
                                     @Field("alamat")String alamat,
                                     @Field("jenkel")String jenkel,
                                     @Field("notelp")String notelp

    );

    // Membuat endpoint untuk update
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("iduser")int iduser,
            @Field("namauser")String namauser,
            @Field("alamat")String alamat,
            @Field("jenkel")String jenkel,
            @Field("notelp")String notelp
    );

}
