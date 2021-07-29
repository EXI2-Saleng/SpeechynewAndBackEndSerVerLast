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
    @POST("datauser/Datauser.php")
    Call<UserData> DataUser(
                            @Field("email") String email,
                            @Field("name") String name,
                            @Field("devicename") String devicename);

    @GET("getdata.php/{email}")
    Call<UserData> getDataUser(@Query("email") String email);

    @GET("test.php/{email}")
    Call<UserData> updatedata(@Query("email") String email,
                               @Query("totalwordday1") String totalwordday1,
                               @Query("Day1")int Day1,
                               @Query("Month1")String Month1,
                               @Query("Year1")int Year1);



    @GET("getAnyword/getAnyword.php/{email}")
    Call<List<DataAnyword>> getDataAnyword(@Query("email") String email,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getAnyword/getAnywordMonth.php/{email}")
    Call<List<DataAnyword>> getDataAnywordMonth(@Query("email") String email,
                                           @Query("month") String month,
                                           @Query("year") String year);




    @GET("getContinuemax/getContinuemax.php/{email}")
    Call<List<DataContinuemax>> getDataContinuemax(@Query("email") String email,
                                                   @Query("date") String date,
                                                   @Query("month") String month,
                                                   @Query("year") String year);

    @GET("getContinuemax/getContinuemaxMonth.php/{email}")
    Call<List<DataContinuemax>> getDataContinuemaxMonth(@Query("email") String email,
                                                   @Query("month") String month,
                                                   @Query("year") String year);

    @GET("getEngword/getEngword.php/{email}")
    Call<List<DataEngword>> getDataEngword(@Query("email") String email,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getEngword/getEngwordMonth.php/{email}")
    Call<List<DataEngword>> getDataEngwordMonth(@Query("email") String email,
                                           @Query("month") String month,
                                           @Query("year") String year);


    @GET("getTime/getTime.php/{email}")
    Call<List<DataTime>> getDataTime(@Query("email") String email,
                                     @Query("date") String date,
                                     @Query("month") String month,
                                     @Query("year") String year);


    @GET("getTime/getTimeMonth.php/{email}")
    Call<List<DataTime>> getDataTimeMonth(@Query("email") String email,
                                     @Query("month") String month,
                                     @Query("year") String year);


    @GET("getWrongword/getWrongword.php/{email}")
    Call<List<DataWrongword>> getDataWrongword(@Query("email") String email,
                                               @Query("date") String date,
                                               @Query("month") String month,
                                               @Query("year") String year);


    @GET("getWrongword/getWrongwordMonth.php/{email}")
    Call<List<DataWrongword>> getDataWrongwordMonth(@Query("email") String email,
                                               @Query("month") String month,
                                               @Query("year") String year);


    @FormUrlEncoded
    @POST("backup/backupAnyword.php")
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
    @POST("backup/backupContinuemax.php")
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
    @POST("backup/backupEngword.php")
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
    @POST("backup/backupRawdata.php")
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
    @POST("backup/backupScheduler.php")
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
    @POST("backup/backupSetting.php")
    Call<DataSetting> DataSetting(
            @Field("email") String email,
            @Field("setting") String setting,
            @Field("nativelang") String nativelang,
            @Field("percentagenone") String percentagenone,
            @Field("chaday") String chaday);


    @FormUrlEncoded
    @POST("backup/backupTime.php")
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
    @POST("backup/backupWrongword.php")
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

