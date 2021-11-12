package com.example.speechynew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.speechynew.Rertofit.ApiClient;
import com.example.speechynew.Rertofit.ApiInterface;
import com.example.speechynew.agent.Myservice;
import com.example.speechynew.connectDB.Continuemax;
import com.example.speechynew.connectDB.DataAnyword;
import com.example.speechynew.connectDB.DataAnyword2;
import com.example.speechynew.connectDB.DataContinuemax;
import com.example.speechynew.connectDB.DataContinuemax2;
import com.example.speechynew.connectDB.DataEngword;
import com.example.speechynew.connectDB.DataEngword2;
import com.example.speechynew.connectDB.DataRawdata;
import com.example.speechynew.connectDB.DataScheduler;
import com.example.speechynew.connectDB.DataScheduler2;
import com.example.speechynew.connectDB.DataTime;
import com.example.speechynew.connectDB.DataTime2;
import com.example.speechynew.connectDB.DataUsernew;
import com.example.speechynew.connectDB.DataWrongword;
import com.example.speechynew.connectDB.DataWrongword2;
import com.example.speechynew.connectDB.Engword;
import com.example.speechynew.connectDB.Rawdata;
import com.example.speechynew.connectDB.Setting;
import com.example.speechynew.connectDB.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.speechynew.connectDB.Engwordinterface.TABLE_NAME2;
import static com.example.speechynew.connectDB.Settinginterface.TABLE_NAME0;
import static com.example.speechynew.connectDB.Statusinterface.TABLE_NAME7;

import com.bumptech.glide.Glide;
import com.example.speechynew.connectDB.Timeprocess;
import com.example.speechynew.connectDB.UserData;
import com.example.speechynew.connectDB.Word;
import com.example.speechynew.connectDB.Wrongword;
import com.example.speechynew.ui.ViewReportAllActivity;
import com.example.speechynew.ui.home.HomeFragment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.speechynew.databinding.ActivityMainBinding;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;
    ImageView IM;

    int totalchallenge;
    int totaleng;

    Button settime;
    Button checkactivity;
    Button addschedule;
    Button checkativityAll;
    Button BTSAVE;


    Switch switchactive;

    ImageButton editer;
    ImageView voice;

    ImageButton notichallenge;
    TextView slideonoff;

    Setting setting;
    Status statusdb;
    Engword eng;
    Word anothereng;
    Timeprocess time;
    Continuemax continuemax;
    Wrongword wrongword;
    Rawdata rawdata;

    AnimationDrawable speak;
    ApiInterface apiInterface;

    GoogleSignInClient mGoogleSignInClient;
    String message;
    String device = Build.BOOTLOADER;

    int indextotalanyword = 0;
    int indextotalmeeng =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        System.out.println("onCreateMain");



        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mGoogleSignInClient = GoogleSignIn.getClient(this,GoogleSignInOptions.DEFAULT_SIGN_IN);

        checkUser();

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            switch (view.getId()) {
                                // ...
                                case R.id.logoutBtn:
                                    signOut();
                                    cdt.cancel();
                                    break;
                                // ...
                            }

                        }
                    }
                });

            }
        });
    }

    private void checkUser() {
        //startThread();
        message="";
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        if(acct == null){
            startActivity(new Intent(this,GoogleLogin.class));
            finish();
        }else{

            String name = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String email = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


            //String devicename = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL;
            String device = Build.BOOTLOADER;

            /*
            Call<UserData> callcheckuser =apiInterface.checkusernew(personId,device);
            callcheckuser.enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    UserData checkuser =response.body();
                    if (checkuser!=null){
                        Log.d("API_DATA_DataUser","Check MS:"+checkuser.getMessages());
                        if (checkuser.getMessages().equals("NEW_User")){

                            startActivity(new Intent(MainActivity.this,PopActivity.class));
                        }
                        if (checkuser.getMessages().equals("NEW_device")){
                            //startActivity(new Intent(MainActivity.this,SettinglangActivity.class));
                            Intent intent = new Intent(MainActivity.this, SettinglangActivity.class);
                            intent.putExtra("CH",99);
                            startActivity(intent);
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {

                }
            });

             */
            DataUsernew CheckUsernew = new DataUsernew(personId,device);
            Call<DataUsernew>calldata =apiInterface.checkuser_new(CheckUsernew);
            calldata.enqueue(new Callback<DataUsernew>() {
                @Override
                public void onResponse(Call<DataUsernew> call, Response<DataUsernew> response) {
                    DataUsernew checkuser =response.body();
                    if (checkuser!=null){
                        Log.d("API_DATA_DataUser","Check User MS:"+checkuser.getUser());
                        Log.d("API_DATA_DataUser","Check Device MS:"+checkuser.getDevice());
                        if (checkuser.getUser().equals("New") && checkuser.getDevice().equals("New")){

                            startActivity(new Intent(MainActivity.this,PopActivity.class));
                        }
                        if (checkuser.getUser().equals("Active") && checkuser.getDevice().equals("New")){

                            Intent intent = new Intent(MainActivity.this, SettinglangActivity.class);
                            intent.putExtra("CH",99);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<DataUsernew> call, Throwable t) {

                }
            });



            Log.d("Show_Data_User","NAMEPHONE3: "+device);
            Log.d("Show_Data_User","IDPhone: "+ Build.ID);
            Log.d("Show_Data_User","BOOTLOADERPhone: "+ Build.BOOTLOADER);
            Log.d("Show_Data_User","email: "+email);
            Log.d("Show_Data_User","name: "+name);
            Log.d("Show_Data_User","ID: "+personId);
            Log.d("Show_Data_User","FamilyName: "+personFamilyName);
            Log.d("Show_Data_User","GivenName: "+personGivenName);
            Log.d("Show_Data_User","IdToken: "+acct.getIdToken());
            Log.d("Show_Data_User","ServerAuthCode: "+acct.getServerAuthCode());





            binding.emailTv.setText("User: "+email);
        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        checkUser();

                    }
                });
    }



    public void onResume(){
        super.onResume();
        //for check stay MainActivity
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", true).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("chaActive", true).commit();

        settime = findViewById(R.id.settime);
        checkactivity = findViewById(R.id.checkactivity);
        switchactive = findViewById(R.id.swicthactive);
        editer = findViewById(R.id.setting);
        addschedule = findViewById(R.id.addschedule);
        notichallenge = findViewById(R.id.notichallenge);
        slideonoff = findViewById(R.id.slideonoff);
        BTSAVE = findViewById(R.id.SaveBtn);





        voice = findViewById(R.id.voice);
        voice.setBackgroundResource(R.drawable.animation);
        speak = (AnimationDrawable) voice.getBackground();

        setting = new Setting(this);
        statusdb = new Status(this);
        eng = new Engword(this);

        anothereng = new Word(this);
        time = new Timeprocess(this);
        continuemax = new Continuemax(this);
        wrongword = new Wrongword(this);
        rawdata = new Rawdata(this);

        //get nativelang and challenge
        totaleng = 0;
        totalchallenge = 0;

        SQLiteDatabase db = setting.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME0, null);
        int countres = res.getCount();

        if(countres==0){
            //check for setting is null
            Activitysetlang();
            return;
        }
        while (res.moveToNext()){
            totalchallenge = res.getInt(3);
        }

        //use runnable check when open Mainactivity first
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getchallenge();

                if(totaleng >= totalchallenge && totalchallenge != 0){

                    notichallenge.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_tracktalkchallenge));

                    notichallenge.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            System.out.println("You got challenge");

                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.challengedialog);
                            dialog.setCancelable(true);

                            Button btngotchallenge = dialog.findViewById(R.id.btngotchallenge);
                            TextView showcha = dialog.findViewById(R.id.showgotchallenge);


                            btngotchallenge.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });

                            showcha.setText(totaleng+" / "+totalchallenge);

                            dialog.show();

                        }
                    });

                }

            }
        };handler.postDelayed(runnable,1000);

        //Check status
        SQLiteDatabase dbstatus = statusdb.getWritableDatabase();
        Cursor resstatus = dbstatus.rawQuery("select * from " + TABLE_NAME7, null);

        if(resstatus.getCount()==0){
            SQLiteDatabase db3 = statusdb.getWritableDatabase();
            ContentValues value3 = new ContentValues();
            value3.put("status","Inactive");
            db3.insertOrThrow(TABLE_NAME7,null,value3);

        }else{

            String sta =" ";

            while(resstatus.moveToNext()){
                sta = resstatus.getString(1);
            }

            if(sta.equals("Inactive")){
                switchactive.setChecked(false);
                speak.stop();
                voice.setBackground(Drawable.createFromPath("@drawable/voicestop"));
                slideonoff.setText("Slide to open tracking mode");

            }else if(sta.equals("Active")){
                switchactive.setChecked(true);
                voice.setBackgroundResource(R.drawable.animation);
                speak = (AnimationDrawable) voice.getBackground();
                speak.start();
                slideonoff.setText("Slide to close tracking mode");
            }

        }

        switchactive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    statusdb.update("Active");
                    editer.setEnabled(false);
                    slideonoff.setText("Slide to close tracking mode");

                    startService(new Intent(MainActivity.this, Myservice.class));
                    onWindowFocusChangedon();

                } else {
                    // The toggle is disabled
                    statusdb.update("Inactive");
                    editer.setEnabled(true);
                    slideonoff.setText("Slide to open tracking mode");

                    stopService(new Intent(MainActivity.this, Myservice.class));
                    onWindowFocusChangedoff();

                }
            }

            private void onWindowFocusChangedon() {
                voice.setBackgroundResource(R.drawable.animation);
                speak = (AnimationDrawable) voice.getBackground();
                speak.start();
            }

            private void onWindowFocusChangedoff() {
                speak.stop();
                voice.setBackground(Drawable.createFromPath("@drawable/voicestop"));
            }

        });


        editer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activitysetlang();
            }
        });

        addschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,Listscheduler.class);
                startActivity(in);
            }
        });


        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent see = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(see);
            }
        });

        checkactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityViewReport();
            }
        });



        System.out.println("onResumeMain");

        BTSAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                B_Anyword();
                B_Engword();
                B_Continuemax();
                B_Time();
                B_Wrongword();

                 */

                /*
                B_Anyword_New();
                B_Engword_New();
                B_Continuemax_New();
                B_Time_New();
                B_Wrongword_New();

                 */







              //  B_Rawdata();
                View_Test();
                getCheduler();
                //up_TEST();

                






                //getdataAnyword_New();
                /*
                getdataContinuemax_New();
                getdataViewtotaltimeday_New();
                getdataViewtotalday_New();

                 */




            }
        });



    }


    @Override
    public void onPause(){
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", false).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("chaActive", false).commit();
        System.out.println("onPauseMain");
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    public void onStop(){
        super.onStop();
        //PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", false).commit();
        System.out.println("onStopMain");
    }


    public void onRestart(){
        super.onRestart();

        System.out.println("onRestartMain");

    }

    public void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isActive", false).commit();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("chaActive", false).commit();
        System.out.println("onDestroyMain");
    }

    public void onBackPressed() {
        //super.onBackPressed();
        System.out.println("onBackPressedMain");
        moveTaskToBack(true);
    }


    void getchallenge(){

        //get engword for compare between engword and challenge
        SQLiteDatabase dbeng = eng.getWritableDatabase();
        Cursor reseng = dbeng.rawQuery("select * from "+TABLE_NAME2+" where date = "+new Date().getDate()+" and month = "+
                (new Date().getMonth()+1)+" and year = "+(new Date().getYear()+1900),null);

        int countreseng = reseng.getCount();

        if(countreseng==0){
            return;
        }

        while (reseng.moveToNext()){
            totaleng += reseng.getInt(1);
        }

        System.out.println("Cha : "+totalchallenge);
        System.out.println("Eng : "+totaleng);

    }


    //Go to setting lang page
    public void Activitysetlang(){
        Intent setlang = new Intent(MainActivity.this,SettinglangActivity.class);
        startActivity(setlang);

    }

    //Go to report page
    public void ActivityViewReport(){
        Intent viewreport = new Intent(MainActivity.this,ViewReportActivity.class);
        startActivity(viewreport);

    }

    public void B_Anyword(){
        Cursor reDef1 = anothereng.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();

        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupAnyword","No data");
        }
        else {
            while (reDef1.moveToNext()){


                Call<DataAnyword>calldataAnyword =apiInterface.DataAnywordnew(USER_ID,device,reDef1.getString(1),reDef1.getString(3),
                        reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8));

                calldataAnyword.enqueue(new Callback<DataAnyword>() {
                    @Override
                    public void onResponse(Call<DataAnyword> call, Response<DataAnyword> response) {
                        DataAnyword dataAnyword = response.body();
                        if (dataAnyword!=null){
                            Log.d("API_DATA_BackupAnyword","SaveSuccessful");
                            Log.d("API_DATA_BackupAnyword","MS:"+dataAnyword.getMessages());
                        }
                        else {

                            Log.d("API_DATA_BackupAnyword","MS:"+dataAnyword.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataAnyword> call, Throwable t) {
                        Log.d("API_DATA_BackupAnyword","Savefail T "+t);

                    }
                });
            }

        }
    }
    public void B_Continuemax(){
        Cursor reDef1 = continuemax.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();
        if (reDef1.getCount()==0){
            Log.d("API_DATA_Continuemax","No data");
        }
        else {
            while (reDef1.moveToNext()){

                Call<DataContinuemax>calldataContinuemax =apiInterface.DataContinuemaxnew(USER_ID,device,reDef1.getString(1),reDef1.getString(3),
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

    }
    public void B_Engword(){
        Cursor reDef1 = eng.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();

        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupEngword","No data");
        }
        else {
            while (reDef1.moveToNext()){
                Call<DataEngword>calldataEngword =apiInterface.DataEngwordnew(USER_ID,device,reDef1.getString(1),reDef1.getString(3),
                        reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8));

                calldataEngword.enqueue(new Callback<DataEngword>() {
                    @Override
                    public void onResponse(Call<DataEngword> call, Response<DataEngword> response) {
                        DataEngword dataEngword = response.body();
                        if (dataEngword!=null){
                            Log.d("API_DATA_BackupEngword","SaveSuccessful");
                            Log.d("API_DATA_BackupEngword","MS:"+dataEngword.getMessages());
                        }
                        else {

                            Log.d("API_DATA_BackupEngword","MS:"+dataEngword.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataEngword> call, Throwable t) {
                        Log.d("API_DATA_BackupEngword","Savefail T "+t);

                    }
                });


            }

        }
    }

    public void B_Rawdata(){

        Cursor reDef1 = rawdata.getAlldata();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();


        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupEngword","No data");
        }
        else {
            while (reDef1.moveToNext()){

                Call<DataRawdata>calldatarawdata =apiInterface.DataRawdata(USER_ID,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
                        reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8));

                calldatarawdata.enqueue(new Callback<DataRawdata>() {
                    @Override
                    public void onResponse(Call<DataRawdata> call, Response<DataRawdata> response) {
                        DataRawdata dataRawdata = response.body();
                        if (dataRawdata!=null){
                            Log.d("API_DATA_BackupRawdata","SaveSuccessful");
                            Log.d("API_DATA_BackupRawdata","MS:"+dataRawdata.getMessages());
                        }
                        else {

                            Log.d("API_DATA_BackupRawdata","MS:"+dataRawdata.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataRawdata> call, Throwable t) {
                        Log.d("API_DATA_BackupRawdata","Savefail T "+t);

                    }
                });
            }

        }
    }

    public void B_Time(){
        Cursor reDef1 = time.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();
        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupTime","No data");
        }
        else {
            while (reDef1.moveToNext()){

                Call<DataTime>calldataTime =apiInterface.DataTimenew(USER_ID,device,reDef1.getString(2),reDef1.getString(3),
                        reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8));

                calldataTime.enqueue(new Callback<DataTime>() {
                    @Override
                    public void onResponse(Call<DataTime> call, Response<DataTime> response) {
                        DataTime dataTime = response.body();
                        if (dataTime!=null){
                            Log.d("API_DATA_BackupTime","SaveSuccessful");
                            Log.d("API_DATA_BackupTime","MS:"+dataTime.getMessages());
                        }
                        else {

                            Log.d("API_DATA_BackupTime","MS:"+dataTime.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataTime> call, Throwable t) {
                        Log.d("API_DATA_BackupTime","Savefail T "+t);

                    }
                });
            }

        }
    }
    public void B_Wrongword(){
        Cursor reDef1 = wrongword.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();
        if (reDef1.getCount()==0){
            Log.d("API_DATA_Wrongword","No data");
        }
        else {
            while (reDef1.moveToNext()){

                Call<DataWrongword>calldataWrongword =apiInterface.DataWrongwordnew(USER_ID,device,reDef1.getString(1),reDef1.getString(3),
                        reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8));

                calldataWrongword.enqueue(new Callback<DataWrongword>() {
                    @Override
                    public void onResponse(Call<DataWrongword> call, Response<DataWrongword> response) {
                        DataWrongword dataWrongword = response.body();
                        if (dataWrongword!=null){
                            Log.d("API_DATA_Wrongword","SaveSuccessful");
                            Log.d("API_DATA_Wrongword","MS:"+dataWrongword.getMessages());
                        }
                        else {

                            Log.d("API_DATA_Wrongwordd","MS:"+dataWrongword.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataWrongword> call, Throwable t) {
                        Log.d("API_DATA_Wrongword","Savefail T "+t);

                    }
                });
            }

        }


    }

    public static void startThread ()
    {
        Runnable runner = new Runnable ()
        {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(900);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Log.d("TEST_AUTORUN","TEST ");

            }
        };
        new Thread (runner).start();

    }
    CountDownTimer cdt = new CountDownTimer(300000, 1000) {
        int value = 0;
        public void onTick(long millisUntilFinished) {
            // Tick
        }

        public void onFinish() {
                B_Anyword_New();
                B_Engword_New();
                B_Continuemax_New();
                B_Time_New();
                B_Wrongword_New();




            Log.d("TEST_AUTORUN","TEST VALUE :"+value);
            value++;
            cdt.start();
        }
    }.start();

    public void B_Anyword_New() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Cursor reDef1 = anothereng.getAlldata();
        String USER_ID = acct.getId();
        while (reDef1.moveToNext()) {
            if (!reDef1.getString(2).equals("99")) {
                DataAnyword2 DataAnyword2 = new DataAnyword2(USER_ID, device, reDef1.getString(1), reDef1.getString(3),
                        reDef1.getString(4), reDef1.getString(5), reDef1.getString(6), reDef1.getString(7), reDef1.getString(8));



                Call<DataAnyword2> calldata = apiInterface.DataAnywordnew(DataAnyword2);

                calldata.enqueue(new Callback<com.example.speechynew.connectDB.DataAnyword2>() {
                    @Override
                    public void onResponse(Call<com.example.speechynew.connectDB.DataAnyword2> call, Response<com.example.speechynew.connectDB.DataAnyword2> response) {
                        Log.d("TEST_POST_JSON", "JSON POST Anyword: " + response.body().getMessages());
                    }

                    @Override
                    public void onFailure(Call<com.example.speechynew.connectDB.DataAnyword2> call, Throwable t) {
                        Log.d("TEST_POST_JSON", "JSON POST USER_ID: " + "Fail");
                    }
                });
                anothereng.updatestatus(Integer.parseInt(reDef1.getString(0)),"99");
            }
        }
    }

    public void B_Engword_New() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Cursor reDef1 = eng.getAlldata();
        String USER_ID = acct.getId();
        while (reDef1.moveToNext()) {
            if (!reDef1.getString(2).equals("99")) {
                DataEngword2 DataEngword = new DataEngword2(USER_ID, device, reDef1.getString(1), reDef1.getString(3),
                        reDef1.getString(4), reDef1.getString(5), reDef1.getString(6), reDef1.getString(7), reDef1.getString(8));

                Call<DataEngword2> calldata = apiInterface.DataEngwordnew(DataEngword);

                calldata.enqueue(new Callback<DataEngword2>() {
                    @Override
                    public void onResponse(Call<DataEngword2> call, Response<DataEngword2> response) {
                        Log.d("TEST_POST_JSON", "JSON POST Engword: " + response.body().getMessages());
                    }

                    @Override
                    public void onFailure(Call<DataEngword2> call, Throwable t) {

                    }
                });
                eng.updatestatus(Integer.parseInt(reDef1.getString(0)),"99");
            }
        }
    }

    public void B_Continuemax_New() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Cursor reDef1 = continuemax.getAlldata();
        String USER_ID = acct.getId();
        while (reDef1.moveToNext()) {
            if (!reDef1.getString(2).equals("99")) {
                DataContinuemax2 DataContinuemax = new DataContinuemax2(USER_ID, device, reDef1.getString(1), reDef1.getString(3),
                        reDef1.getString(4), reDef1.getString(5), reDef1.getString(6), reDef1.getString(7), reDef1.getString(8));

                Call<DataContinuemax2> calldata = apiInterface.DataContinuemaxnew(DataContinuemax);

                calldata.enqueue(new Callback<DataContinuemax2>() {
                    @Override
                    public void onResponse(Call<DataContinuemax2> call, Response<DataContinuemax2> response) {
                        Log.d("TEST_POST_JSON", "JSON POST Continuemax: " + response.body().getMessages());
                    }

                    @Override
                    public void onFailure(Call<DataContinuemax2> call, Throwable t) {

                    }
                });
                continuemax.updatestatus(Integer.parseInt(reDef1.getString(0)),"99");
            }
        }


    }

    public void B_Time_New() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Cursor reDef1 = time.getAlldata();
        String USER_ID = acct.getId();
        while (reDef1.moveToNext()) {
            if (!reDef1.getString(1).equals("99")) {
                DataTime2 DataTime = new DataTime2(USER_ID, device, reDef1.getString(2), reDef1.getString(3),
                        reDef1.getString(4), reDef1.getString(5), reDef1.getString(6), reDef1.getString(7), reDef1.getString(8));

                Call<DataTime2> calldata = apiInterface.DataTimenew(DataTime);

                calldata.enqueue(new Callback<DataTime2>() {
                    @Override
                    public void onResponse(Call<DataTime2> call, Response<DataTime2> response) {
                        Log.d("TEST_POST_JSON", "JSON POST TIME: " + response.body().getMessages());
                    }

                    @Override
                    public void onFailure(Call<DataTime2> call, Throwable t) {

                    }
                });
                time.updatestatus(Integer.parseInt(reDef1.getString(0)),"99");
            }
        }


    }

    public void B_Wrongword_New() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Cursor reDef1 = wrongword.getAlldata();
        String USER_ID = acct.getId();
        while (reDef1.moveToNext()) {
            if (!reDef1.getString(2).equals("99")) {
                DataWrongword2 DataWrongword = new DataWrongword2(USER_ID, device, reDef1.getString(1), reDef1.getString(3),
                        reDef1.getString(4), reDef1.getString(5), reDef1.getString(6), reDef1.getString(7), reDef1.getString(8));

                Call<DataWrongword2> calldata = apiInterface.DataWrongwordnew(DataWrongword);
                calldata.enqueue(new Callback<DataWrongword2>() {
                    @Override
                    public void onResponse(Call<DataWrongword2> call, Response<DataWrongword2> response) {
                        Log.d("TEST_POST_JSON", "JSON POST Wrongword: " + response.body().getMessages());
                    }

                    @Override
                    public void onFailure(Call<DataWrongword2> call, Throwable t) {

                    }
                });

                wrongword.updatestatus(Integer.parseInt(reDef1.getString(0)),"99");
            }


        }


    }

    public void View_Test() {

        Cursor reDef1 = anothereng.getAlldata();
        Cursor reDef2 = eng.getAlldata();
        Cursor reDef3 = continuemax.getAlldata();
        Cursor reDef4 = time.getAlldata();
        Cursor reDef5 = wrongword.getAlldata();

        Log.d("VIEW_TEST", "Anyword");

        while (reDef1.moveToNext()) {
            //DataAnyword2 DataAnyword2 = new DataAnyword2(USER_ID, device, reDef1.getString(1), reDef1.getString(3),
              //      reDef1.getString(4), reDef1.getString(5), reDef1.getString(6), reDef1.getString(7), reDef1.getString(8));
            if (!reDef1.getString(2).equals("99")){
                Log.d("VIEW_TEST", "ID: " + reDef1.getString(0)+" STATUS :"+ reDef1.getString(2));

            }
            //anothereng.updatestatus(Integer.parseInt(reDef1.getString(0)),"1");

        }

        Log.d("VIEW_TEST", "Engword");
        while (reDef2.moveToNext()) {
            if (!reDef2.getString(2).equals("99")){
                Log.d("VIEW_TEST", "ID: " + reDef2.getString(0)+" STATUS :"+ reDef2.getString(2));
            }
        }
        Log.d("VIEW_TEST", "ConMaxword");
        while (reDef3.moveToNext()) {
            if (!reDef3.getString(2).equals("99")){
                Log.d("VIEW_TEST", "ID: " + reDef3.getString(0)+" STATUS :"+ reDef3.getString(2));
            }
        }
        Log.d("VIEW_TEST", "Timeword");
        while (reDef4.moveToNext()) {
            if (!reDef4.getString(1).equals("99")){
                Log.d("VIEW_TEST", "ID: " + reDef4.getString(0)+" STATUS :"+ reDef4.getString(1));
            }
        }
        Log.d("VIEW_TEST", "Wrongword");
        while (reDef5.moveToNext()) {
            if (!reDef5.getString(2).equals("99")){
                Log.d("VIEW_TEST", "ID: " + reDef5.getString(0)+" STATUS :"+ reDef5.getString(2));
            }


        }


    }
    public void up_TEST() {

        Cursor reDef1 = anothereng.getAlldata();
        Cursor reDef2 = eng.getAlldata();
        Cursor reDef3 = continuemax.getAlldata();
        Cursor reDef4 = time.getAlldata();
        Cursor reDef5 = wrongword.getAlldata();

        Log.d("VIEW_TEST", "Anyword");

        while (reDef1.moveToNext()) {
            if (reDef1.getString(3).equals("30") && reDef1.getString(4).equals("10")){
                Log.d("VIEW_TEST", "ID: " + reDef1.getString(0)+" STATUS :"+ reDef1.getString(2)
                        +"DATE: "+reDef1.getString(3)+"MONTH: "+reDef1.getString(4));
                //anothereng.updatestatus(Integer.parseInt(reDef1.getString(0)),"0");
            }
            //anothereng.updatestatus(Integer.parseInt(reDef1.getString(0)),"1");

        }

        Log.d("VIEW_TEST", "Engword");
        while (reDef2.moveToNext()) {
            if (reDef2.getString(3).equals("30") && reDef2.getString(4).equals("10")){
                Log.d("VIEW_TEST", "ID: " + reDef2.getString(0)+" STATUS :"+ reDef2.getString(2)
                +"DATE: "+reDef2.getString(3)+"MONTH: "+reDef2.getString(4));
               // eng.updatestatus(Integer.parseInt(reDef2.getString(0)),"0");

            }
        }
        Log.d("VIEW_TEST", "ConMaxword");
        while (reDef3.moveToNext()) {
            if (reDef3.getString(3).equals("30") && reDef3.getString(4).equals("10")){
                Log.d("VIEW_TEST", "ID: " + reDef3.getString(0)+" STATUS :"+ reDef3.getString(2)
                        +"DATE: "+reDef3.getString(3)+"MONTH: "+reDef3.getString(4));
                //continuemax.updatestatus(Integer.parseInt(reDef3.getString(0)),"0");

            }
        }
        Log.d("VIEW_TEST", "Timeword");
        while (reDef4.moveToNext()) {
            if (reDef4.getString(2).equals("30") && reDef4.getString(3).equals("10")){
                Log.d("VIEW_TEST", "ID: " + reDef4.getString(0)+" STATUS :"+ reDef4.getString(1)
                        +"DATE: "+reDef4.getString(2)+"MONTH: "+reDef4.getString(3));
                //time.updatestatus(Integer.parseInt(reDef4.getString(0)),"0");

            }
        }
        Log.d("VIEW_TEST", "Wrongword");
        while (reDef5.moveToNext()) {
            if (reDef5.getString(3).equals("30") && reDef5.getString(4).equals("10")){
                Log.d("VIEW_TEST", "ID: " + reDef5.getString(0)+" STATUS :"+ reDef5.getString(2)
                        +"DATE: "+reDef5.getString(3)+"MONTH: "+reDef5.getString(4));
               // wrongword.updatestatus(Integer.parseInt(reDef5.getString(0)),"0");

            }


        }


    }
    public void getCheduler(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();
        Call<List<DataScheduler>>Calldata = apiInterface.getCheduler(USER_ID);
        Calldata.enqueue(new Callback<List<DataScheduler>>() {
            @Override
            public void onResponse(Call<List<DataScheduler>> call, Response<List<DataScheduler>> response) {
                List<DataScheduler>Listdata = response.body();
                if (Listdata.size()!=0){
                    for (int i=0;i<Listdata.size();i++){
                        Log.d("TEST_GET_Cheduler", "Day : " +Listdata.get(i).getDay());
                        Log.d("TEST_GET_Cheduler", "Date: " +Listdata.get(i).getDate());
                        Log.d("TEST_GET_Cheduler", "Month : " +Listdata.get(i).getMonth());
                        Log.d("TEST_GET_Cheduler", "Year : " +Listdata.get(i).getYear());
                        Log.d("TEST_GET_Cheduler", "StartH : " +Listdata.get(i).getStarthour());
                        Log.d("TEST_GET_Cheduler", "StartM: " +Listdata.get(i).getStartminute());
                        Log.d("TEST_GET_Cheduler", "StopH: " +Listdata.get(i).getStophour());
                        Log.d("TEST_GET_Cheduler", "StipM: " +Listdata.get(i).getStopminute());
                        Log.d("TEST_GET_Cheduler", "Status: " +Listdata.get(i).getStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DataScheduler>> call, Throwable t) {

            }
        });

          /*
        SQLiteDatabase dbscheduler = scheduler.getWritableDatabase();
        ContentValues valuescheduler = new ContentValues();
        valuescheduler.put(DAYSTART,getdaycalendar);
        valuescheduler.put(DATESTART,getdatecalendar);
        valuescheduler.put(MONTHSTART,getmonthcalendar+1);
        valuescheduler.put(YEARSTART,getyearcalendar+1900);
        valuescheduler.put(TIMESTARTHOUR,starthourset);
        valuescheduler.put(TIMESTARTMINUTE,startminuteset);
        valuescheduler.put(TIMESTOPHOUR,stophourset);
        valuescheduler.put(TIMESTOPMINUTE,stopminuteset);
        valuescheduler.put(STATUS,"Active");
        dbscheduler.insertOrThrow(TABLE_NAME9, null, valuescheduler);

           */



    }
}
