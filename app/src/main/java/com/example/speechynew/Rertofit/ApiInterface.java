package com.example.speechynew.Rertofit;

import com.example.speechynew.connectDB.DataAnyword;
import com.example.speechynew.connectDB.DataAnyword2;
import com.example.speechynew.connectDB.DataContinuemax;
import com.example.speechynew.connectDB.DataContinuemax2;
import com.example.speechynew.connectDB.DataEngword;
import com.example.speechynew.connectDB.DataEngword2;
import com.example.speechynew.connectDB.DataRawdata;
import com.example.speechynew.connectDB.DataScheduler;
import com.example.speechynew.connectDB.DataScheduler2;
import com.example.speechynew.connectDB.DataSetting;
import com.example.speechynew.connectDB.DataSettingnew;
import com.example.speechynew.connectDB.DataTime;
import com.example.speechynew.connectDB.DataTime2;
import com.example.speechynew.connectDB.DataUsernew;
import com.example.speechynew.connectDB.DataWrongword;
import com.example.speechynew.connectDB.DataWrongword2;
import com.example.speechynew.connectDB.UserData;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {



//////////////////////////////////////////////// newDB myprojectnew ///////////////////////////////

    @FormUrlEncoded
    @POST("datausernew/Datauser.php")
    Call<UserData> DataUsernew(
            @Field("USER_ID") String USER_ID,
            @Field("email") String email,
            @Field("name") String name);

    @FormUrlEncoded
    @POST("datausernew/deviceuser.php")
    Call<UserData> deviceusernew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device);

    @FormUrlEncoded
    @POST("datausernew/checkuser.php")
    Call<UserData> checkusernew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device);

/////////////////////////////////////////////////////////////////////////////////////////////////////
@POST("datausernew/checkusernew.php")
Call<DataUsernew>checkuser_new(@Body DataUsernew DataUsernew);

    @POST("datausernew/deviceusernew.php")
    Call<DataUsernew>deviceuser_new(@Body DataUsernew DataUsernew);

    @POST("datausernew/Datausernew.php")
    Call<DataUsernew>DataUser_new(@Body DataUsernew DataUsernew);



//////////////////////////////////////////////////////////////////////////////////////////////

    @GET("getAnyword/getAnywordNew.php/{email}")
    Call<DataAnyword> getDataAnyword2(@Query("USER_ID") String USER_ID,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);



    @GET("getAnyword/getAnyword.php/{email}")
    Call<List<DataAnyword>> getDataAnyword(@Query("USER_ID") String USER_ID,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getAnyword/getAnywordMonth.php/{email}")
    Call<List<DataAnyword>> getDataAnywordMonth(@Query("USER_ID") String USER_ID,
                                           @Query("month") String month,
                                           @Query("year") String year);



    @GET("getContinuemax/getContinuemaxNew.php/{email}")
    Call<DataContinuemax> getDataContinuemax2(@Query("USER_ID") String USER_ID,
                                      @Query("date") String date,
                                      @Query("month") String month,
                                      @Query("year") String year);


    @GET("getContinuemax/getContinuemax.php/{email}")
    Call<List<DataContinuemax>> getDataContinuemax(@Query("USER_ID") String USER_ID,
                                                   @Query("date") String date,
                                                   @Query("month") String month,
                                                   @Query("year") String year);

    @GET("getContinuemax/getContinuemaxMonth.php/{email}")
    Call<List<DataContinuemax>> getDataContinuemaxMonth(@Query("USER_ID") String USER_ID,
                                                   @Query("month") String month,
                                                   @Query("year") String year);

    @GET("getEngword/getEngwordNew.php/{USER_ID}")
    Call<DataEngword> getDataEngword2(@Query("USER_ID") String USER_ID,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getEngword/getEngword.php/{email}")
    Call<List<DataEngword>> getDataEngword(@Query("USER_ID") String USER_ID,
                                           @Query("date") String date,
                                           @Query("month") String month,
                                           @Query("year") String year);

    @GET("getEngword/getEngwordMonth.php/{email}")
    Call<List<DataEngword>> getDataEngwordMonth(@Query("USER_ID") String USER_ID,
                                           @Query("month") String month,
                                           @Query("year") String year);


    @GET("getTime/getTimeNew.php/{USER_ID}")
    Call<DataTime> getDataTime2(@Query("USER_ID") String USER_ID,
                                     @Query("date") String date,
                                     @Query("month") String month,
                                     @Query("year") String year);

    @GET("getTime/getTime.php/{email}")
    Call<List<DataTime>> getDataTime(@Query("USER_ID") String USER_ID,
                                     @Query("date") String date,
                                     @Query("month") String month,
                                     @Query("year") String year);


    @GET("getTime/getTimeMonth.php/{email}")
    Call<List<DataTime>> getDataTimeMonth(@Query("USER_ID") String USER_ID,
                                     @Query("month") String month,
                                     @Query("year") String year);


    @GET("getWrongword/getWrongword.php/{email}")
    Call<List<DataWrongword>> getDataWrongword(@Query("USER_ID") String USER_ID,
                                               @Query("date") String date,
                                               @Query("month") String month,
                                               @Query("year") String year);


    @GET("getWrongword/getWrongwordMonth.php/{email}")
    Call<List<DataWrongword>> getDataWrongwordMonth(@Query("USER_ID") String USER_ID,
                                               @Query("month") String month,
                                               @Query("year") String year);

    @GET("getSetting/getsettingnew.php/{USER_ID}")
    Call<DataSetting> getSetting(@Query("USER_ID") String USER_ID);


    @FormUrlEncoded
    @POST("backup/backupAnyword.php")
    Call<DataAnyword> DataAnyword(
                    @Field("USER_ID") String USER_ID,
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
            @Field("USER_ID") String USER_ID,
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
            @Field("USER_ID") String USER_ID,
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
            @Field("USER_ID") String USER_ID,
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
            @Field("USER_ID") String USER_ID,
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
            @Field("USER_ID") String USER_ID,
            @Field("setting") String setting,
            @Field("nativelang") String nativelang,
            @Field("percentagenone") String percentagenone,
            @Field("chaday") String chaday);


    @FormUrlEncoded
    @POST("backup/backupTime.php")
    Call<DataTime> DataTime(
            @Field("USER_ID") String USER_ID,
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
            @Field("USER_ID") String USER_ID,
            @Field("wrongword") String wrongword,
            @Field("word") String word,
            @Field("day") String day,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);

/////////////////////////////////////////////////////////////////////////////////////////////
@FormUrlEncoded
@POST("backupnew/backupSetting.php")
Call<DataSetting> DataSettingnew(
        @Field("USER_ID") String USER_ID,
        @Field("nativelang") String nativelang,
        @Field("percentagenone") String percentagenone,
        @Field("chaday") String chaday);

    @FormUrlEncoded
    @POST("backupnew/backupAnyword.php")
    Call<DataAnyword> DataAnywordnew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device,
            @Field("word") String word,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);

    @FormUrlEncoded
    @POST("backupnew/backupContinuemax.php")
    Call<DataContinuemax> DataContinuemaxnew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device,
            @Field("maxcon") String maxcon,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);

    @FormUrlEncoded
    @POST("backupnew/backupEngword.php")
    Call<DataEngword> DataEngwordnew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device,
            @Field("word") String word,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);

    @FormUrlEncoded
    @POST("backupnew/backupTime.php")
    Call<DataTime> DataTimenew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second,
            @Field("totaltime") String totaltime);

    @FormUrlEncoded
    @POST("backupnew/backupWrongword.php")
    Call<DataWrongword> DataWrongwordnew(
            @Field("USER_ID") String USER_ID,
            @Field("device") String device,
            @Field("word") String word,
            @Field("date") String date,
            @Field("month") String month,
            @Field("year") String year,
            @Field("hour") String hour,
            @Field("minute") String minute,
            @Field("second") String second);


/////////////////////////////////////////////////////////////////////////
@GET("getAnywordnew/getAnywordNew.php/{USER_ID}")
Call<DataAnyword> getDataAnywordnew(@Query("USER_ID") String USER_ID,
                                  @Query("date") String date,
                                  @Query("month") String month,
                                  @Query("year") String year);

    @GET("getAnywordnew/getAnywordMonthNew.php/{USER_ID}")
    Call<DataAnyword> getDataAnywordMonthnew(@Query("USER_ID") String USER_ID,
                                                @Query("month") String month,
                                                @Query("year") String year);

    @GET("getContinuemaxnew/getContinuemaxNew.php/{USER_ID}")
    Call<DataContinuemax> getDataContinuemaxnew(@Query("USER_ID") String USER_ID,
                                              @Query("date") String date,
                                              @Query("month") String month,
                                              @Query("year") String year);

    @GET("getContinuemaxnew/getContinuemaxMonthNew.php/{USER_ID}")
    Call<DataContinuemax> getDataContinuemaxMonthnew(@Query("USER_ID") String USER_ID,
                                                        @Query("month") String month,
                                                        @Query("year") String year);

    @GET("getTimenew/getTimeNew.php/{USER_ID}")
    Call<DataTime> getDataTimenew(@Query("USER_ID") String USER_ID,
                                @Query("date") String date,
                                @Query("month") String month,
                                @Query("year") String year);



    @GET("getTimenew/getTimeMonthNew.php/{USER_ID}")
    Call<DataTime> getDataTimeMonthnew(@Query("USER_ID") String USER_ID,
                                          @Query("month") String month,
                                          @Query("year") String year);

    @GET("getEngwordnew/getEngwordNew.php/{USER_ID}")
    Call<DataEngword> getDataEngwordnew(@Query("USER_ID") String USER_ID,
                                      @Query("date") String date,
                                      @Query("month") String month,
                                      @Query("year") String year);



    @GET("getEngwordnew/getEngwordMonthNew.php/{USER_ID}")
    Call<DataEngword> getDataEngwordMonthnew(@Query("USER_ID") String USER_ID,
                                                @Query("month") String month,
                                                @Query("year") String year);

    @GET("getWrongwordnew/getWrongwordNew.php/{USER_ID}")
    Call<DataWrongword> getDataWrongwordnew(@Query("USER_ID") String USER_ID,
                                               @Query("date") String date,
                                               @Query("month") String month,
                                               @Query("year") String year);


    @GET("getWrongwordnew/getWrongwordMonthNew.php/{USER_ID}")
    Call<DataWrongword> getDataWrongwordMonthnew(@Query("USER_ID") String USER_ID,
                                                    @Query("month") String month,
                                                    @Query("year") String year);

    @GET("datausernew/getdevice.php/{USER_ID}")
    Call<DataUsernew> getDataDevice(@Query("USER_ID") String USER_ID,
                                    @Query("device") String device);

    ////////////////////////POST_RAW///////////////////////////////////////////
    @POST("backupnew/backupSettingnew.php")
    Call<DataSettingnew>DataSettingnew(@Body DataSettingnew DataSettingnew);

    @POST("backupnew/backupAnyword.php")
    Call<DataAnyword2>DataAnywordnew(@Body DataAnyword2 DataAnyword2);

    @POST("backupnew/backupWrongword.php")
    Call<DataWrongword2>DataWrongwordnew(@Body DataWrongword2 DataWrongword2);

    @POST("backupnew/backupEngword.php")
    Call<DataEngword2>DataEngwordnew(@Body DataEngword2 DataEngword2);

    @POST("backupnew/backupContinuemax.php")
    Call<DataContinuemax2>DataContinuemaxnew(@Body DataContinuemax2 DataContinuemax2);

    @POST("backupnew/backupTime.php")
    Call<DataTime2>DataTimenew(@Body DataTime2 DataTime2);

    @POST("datausernew/updatedevice.php")
    Call<DataUsernew>updateDevicename(@Body DataUsernew DataUsernew);

    @POST("backupnew/backupScheduler.php")
    Call<DataScheduler2>DataChedulernew(@Body DataScheduler2 DataScheduler2);




}

