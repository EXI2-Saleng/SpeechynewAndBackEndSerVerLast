package com.example.speechynew.connectDB;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.example.speechynew.R;
import com.example.speechynew.connectDB.Engword;
import com.example.speechynew.connectDB.Setting;
import com.example.speechynew.connectDB.Timeprocess;
import com.example.speechynew.connectDB.Continuemax;
import static com.example.speechynew.connectDB.Continuemaxinterface.TABLE_NAME10;
import static com.example.speechynew.connectDB.Engwordinterface.TABLE_NAME2;
import static com.example.speechynew.connectDB.Timeprocessinterface.TABLE_NAME5;
import static com.example.speechynew.connectDB.Wordinterface.TABLE_NAME3;
import static com.example.speechynew.connectDB.Wrongwordinterface.TABLE_NAME11;
import  com.example.speechynew.Rertofit.ApiClient;
import  com.example.speechynew.Rertofit.ApiInterface;
import  com.example.speechynew.connectDB.UserData;
import  com.example.speechynew.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.annotations.JsonAdapter;


import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class backup {
    ApiInterface apiInterface;
    Continuemax continuemax;
    Context mContext;
    private FirebaseAuth firebaseAuth;

    public void getcontinuemax() {
           continuemax = new Continuemax(mContext);
           Cursor reDef1 = continuemax.getAlldata();
           apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
           firebaseAuth = FirebaseAuth.getInstance();
           FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

           StringBuffer buffer = new StringBuffer();
           String email = firebaseUser.getEmail();
           if (reDef1.getCount()==0){
               Log.d("API_DATA_Continuemax","No data");
           }
           else {
               while (reDef1.moveToNext()){

                   buffer.append("Continuemax: "+reDef1.getString(0)+"\n");
                   buffer.append("MAXCON: " + reDef1.getString(1) + "\n");
                   buffer.append("DAY: " + reDef1.getString(2) + "\n");
                   buffer.append("DATE: " + reDef1.getString(3) + "\n");
                   buffer.append("MONTH: " + reDef1.getString(4) + "\n");
                   buffer.append("YEAR: " + reDef1.getString(5) + "\n");
                   buffer.append("HOUR: " + reDef1.getString(6) + "\n");
                   buffer.append("MINUTE: " + reDef1.getString(7) + "\n");
                   buffer.append("SECOND: " + reDef1.getString(8) + "\n");
                   buffer.append("===============================================\n");
                   Call<DataContinuemax>calldataContinuemax =apiInterface.DataContinuemax(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
                           reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8));

                   calldataContinuemax.enqueue(new Callback<DataContinuemax>() {
                       @Override
                       public void onResponse(Call<DataContinuemax> call, Response<DataContinuemax> response) {
                           DataContinuemax dataContinuemax = response.body();
                           if (dataContinuemax!=null){
                               Log.d("API_DATA_Continuemax","SaveSuccessful");
                               Log.d("API_DATA_Continuemax","MS:"+dataContinuemax.getMessages());
                           }
                           else {

                               Log.d("API_DATA_Continuemax","MS:"+dataContinuemax.getMessages());
                           }
                       }

                       @Override
                       public void onFailure(Call<DataContinuemax> call, Throwable t) {
                           Log.d("API_DATA_Continuemax","Savefail T "+t);

                       }
                   });
               }

           }
            Log.d("API_DATA_BackupCM",buffer.toString());

        }




}
