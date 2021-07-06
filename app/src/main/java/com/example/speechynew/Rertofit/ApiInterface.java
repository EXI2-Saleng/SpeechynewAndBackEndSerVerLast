package com.example.speechynew.Rertofit;

import com.example.speechynew.connectDB.UserData;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("savedata.php")
    Call<UserData> DataUser(
                            @Field("email") String email,
                            @Field("totalwordday1") String totalwordday1,
                            @Field("totaltimeday1") String totaltimeday1,
                            @Field("wordminday1") Double wordminday1,
                            @Field("continuemaxday1") int continuemaxday1,
                            @Field("wordtop1") String wordtop1,
                            @Field("wordtop2") String wordtop2,
                            @Field("wordtop3") String wordtop3,
                            @Field("Day1") int Day1,
                            @Field("Month1") String Month1,
                            @Field("Year1") int Year1);

    @GET("getdata.php/{email}")
    Call<UserData> getDataUser(@Query("email") String email,
                               @Query("Day1")int Day1,
                               @Query("Month1")String Month1,
                               @Query("Year1")int Year1);




}

