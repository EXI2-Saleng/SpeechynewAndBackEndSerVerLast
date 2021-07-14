package com.example.speechynew.Rertofit;

import com.example.speechynew.connectDB.DataAnyword;
import com.example.speechynew.connectDB.DataContinuemax;
import com.example.speechynew.connectDB.DataEngword;
import com.example.speechynew.connectDB.DataRawdata;
import com.example.speechynew.connectDB.DataScheduler;
import com.example.speechynew.connectDB.DataSetting;
import com.example.speechynew.connectDB.DataTime;
import com.example.speechynew.connectDB.DataWrongword;
import com.example.speechynew.connectDB.UserData;


import java.util.List;

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

    @GET("test.php/{email}")
    Call<UserData> updatedata(@Query("email") String email,
                               @Query("totalwordday1") String totalwordday1,
                               @Query("Day1")int Day1,
                               @Query("Month1")String Month1,
                               @Query("Year1")int Year1);


    @GET("getAnyword.php/{email}")
    Call<List<DataAnyword>> getDataAnyword(@Query("email") String email,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getContinuemax.php/{email}")
    Call<List<DataContinuemax>> getDataContinuemax(@Query("email") String email);

    @GET("getEngword.php/{email}")
    Call<List<DataEngword>> getDataEngword(@Query("email") String email,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getTime.php/{email}")
    Call<List<DataTime>> getDataTime(@Query("email") String email,
                                     @Query("date") String date,
                                     @Query("month") String month,
                                     @Query("year") String year);

    @GET("getWrongword.php/{email}")
    Call<List<DataWrongword>> getDataWrongword(@Query("email") String email);


    @FormUrlEncoded
    @POST("backupAnyword.php")
    Call<DataAnyword> DataAnyword(
                    @Field("email") String email,
                    @Field("anyword") String anyword,
                    @Field("word") String word,
                    @Field("day") String day,
                    @Field("date") String date,
                    @Field("month") String month,
                    @Field("year") String year,
                    @Field("hour") String hour,
                    @Field("minute") String minute,
                    @Field("second") String second);

    @FormUrlEncoded
    @POST("backupContinuemax.php")
    Call<DataContinuemax> DataContinuemax(
            @Field("email") String email,
            @Field("continuemax") String continuemax,
            @Field("maxcon") String maxcon,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);


    @FormUrlEncoded
    @POST("backupEngword.php")
    Call<DataEngword> DataEngword(
            @Field("email") String email,
            @Field("engword") String engword,
            @Field("word") String word,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);

    @FormUrlEncoded
    @POST("backupRawdata.php")
    Call<DataRawdata> DataRawdata(
            @Field("email") String email,
            @Field("rawdata") String rawdata,
            @Field("totalword") String totalword,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);


    @FormUrlEncoded
    @POST("backupScheduler.php")
    Call<DataScheduler> DataScheduler(
            @Field("email") String email,
            @Field("scheduler") String scheduler,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("starthour") String starthour,
            @Field("startminute") String startminute,
            @Field("stophour") String stophour,
            @Field("stopminute") String stopminute,
            @Field("status") String status);


    @FormUrlEncoded
    @POST("backupSetting.php")
    Call<DataSetting> DataSetting(
            @Field("email") String email,
            @Field("setting") String setting,
            @Field("nativelang") String nativelang,
            @Field("percentagenone") String percentagenone,
            @Field("chaday") String chaday);


    @FormUrlEncoded
    @POST("backupTime.php")
    Call<DataTime> DataTime(
            @Field("email") String email,
            @Field("time") String time,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second,
            @Field("totaltime") String totaltime);


    @FormUrlEncoded
    @POST("backupWrongword.php")
    Call<DataWrongword> DataWrongword(
            @Field("email") String email,
            @Field("wrongword") String wrongword,
            @Field("word") String word,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);






}

