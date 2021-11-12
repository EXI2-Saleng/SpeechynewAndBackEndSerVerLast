package com.example.speechynew;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.speechynew.Rertofit.ApiClient;
import com.example.speechynew.Rertofit.ApiInterface;
import com.example.speechynew.connectDB.DataScheduler;
import com.example.speechynew.connectDB.DataScheduler2;
import com.example.speechynew.connectDB.Scheduler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.speechynew.connectDB.Schedulerinterface.DATESTART;
import static com.example.speechynew.connectDB.Schedulerinterface.DAYSTART;
import static com.example.speechynew.connectDB.Schedulerinterface.MONTHSTART;
import static com.example.speechynew.connectDB.Schedulerinterface.STATUS;
import static com.example.speechynew.connectDB.Schedulerinterface.TABLE_NAME9;
import static com.example.speechynew.connectDB.Schedulerinterface.TIMESTARTHOUR;
import static com.example.speechynew.connectDB.Schedulerinterface.TIMESTARTMINUTE;
import static com.example.speechynew.connectDB.Schedulerinterface.TIMESTOPHOUR;
import static com.example.speechynew.connectDB.Schedulerinterface.TIMESTOPMINUTE;
import static com.example.speechynew.connectDB.Schedulerinterface.YEARSTART;

public class Listscheduler extends AppCompatActivity {

    Scheduler scheduler;

    ListView listview;
    ImageButton addalarm;
    ApiInterface apiInterface;

    ArrayList<String> listid; ArrayList<String> listday;
    ArrayList<String> listdate; ArrayList<String> listmonth;
    ArrayList<String> listyear; ArrayList<String> liststarthour;
    ArrayList<String> liststartminute; ArrayList<String> liststophour;
    ArrayList<String> liststopminute; ArrayList<String> liststatus;

    boolean checkagain = false;

    Calendar mcurrentTimeStart = Calendar.getInstance();
    int hourStart = mcurrentTimeStart.get(Calendar.HOUR_OF_DAY);
    int minuteStart = mcurrentTimeStart.get(Calendar.MINUTE);

    Calendar mcurrentTimeStop = Calendar.getInstance();
    int hourStop = mcurrentTimeStop.get(Calendar.HOUR_OF_DAY);
    int minuteStop = mcurrentTimeStop.get(Calendar.MINUTE);

    int starthourset = hourStart;
    int startminuteset = minuteStart;

    int stophourset = hourStop;
    int stopminuteset = minuteStop;

    Calendar calendar;
    int getdaycalendar;
    int getdatecalendar;
    int getmonthcalendar;
    int getyearcalendar;
    int CH;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_listscheduler);

        listview = findViewById(R.id.listviewscheduler);
        addalarm = findViewById(R.id.addalarm);

        listid = new ArrayList<>(); listday = new ArrayList<>();
        listdate = new ArrayList<>(); listmonth = new ArrayList<>();
        listyear = new ArrayList<>(); liststarthour = new ArrayList<>();
        liststartminute = new ArrayList<>(); liststophour = new ArrayList<>();
        liststopminute = new ArrayList<>(); liststatus = new ArrayList<>();

        scheduler = new Scheduler(this);

        calendar = Calendar.getInstance();
        getdaycalendar = calendar.getTime().getDay();
        getdatecalendar = calendar.getTime().getDate();
        getmonthcalendar = calendar.getTime().getMonth();
        getyearcalendar = calendar.getTime().getYear();

        Intent i = getIntent();
        CH = i.getIntExtra("CH",1);
        if (CH==99){
            getCheduler();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    saveCheduler();
                }
            };handler.postDelayed(runnable,100);

            startActivity(new Intent(Listscheduler.this,MainActivity.class));
        }

        showlist();

        SchedulerAdapter schedulerAdapter = new SchedulerAdapter(Listscheduler.this,listid,listday,listdate,
                                                                  listmonth,listyear,liststarthour,liststartminute,liststophour,
                                                                  liststopminute,liststatus);

        listview.setAdapter(schedulerAdapter);

        addalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Listscheduler.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.addschedulerdialog);
                dialog.setCancelable(true);

                String starttext = "";
                String stoptext = "";

                Button btnscheduler = dialog.findViewById(R.id.btnaddscheduler);
                final Button starttime = dialog.findViewById(R.id.starttime);
                final Button stoptime = dialog.findViewById(R.id.stoptime);
                final TextView again = dialog.findViewById(R.id.setagian);


                //check addstart<10
                if(hourStart<10){
                    if(minuteStart<10){
                        starttext = "0"+hourStart+":0"+minuteStart;
                    }else{
                        starttext = "0"+hourStart+":"+minuteStart;
                    }
                }else{
                    if(minuteStart<10){
                        starttext = hourStart+":0"+minuteStart;
                    }else{
                        starttext = hourStart+":"+minuteStart;
                    }
                }
                starttime.setText(starttext);

                //check addstop<10
                if(hourStop<10){
                    if(minuteStop<10) {
                        stoptext = "0" + hourStop + ":0" + minuteStop;
                    }else{
                        stoptext = "0" + hourStop + ":" + minuteStop;
                    }
                }else{
                    if(minuteStop<10) {
                        stoptext = hourStop + ":0" + minuteStop;
                    }else{
                        stoptext = hourStop + ":" + minuteStop;
                    }
                }
                stoptime.setText(stoptext);


                starttime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog mTimePicker;

                        mTimePicker = new TimePickerDialog(Listscheduler.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                                String starttext = "";

                                starthourset = selectedHour;
                                startminuteset = selectedMinute;

                                if(selectedHour<10){
                                    if(selectedMinute<10){
                                        starttext = "0"+selectedHour+":0"+selectedMinute;
                                    }else{
                                        starttext = "0"+selectedHour+":"+selectedMinute;
                                    }
                                }else{
                                    if(selectedMinute<10){
                                        starttext = selectedHour+":0"+selectedMinute;
                                    }else{
                                        starttext = selectedHour+":"+selectedMinute;
                                    }
                                }

                                starttime.setText(starttext);
                            }
                        }, hourStart, minuteStart, true);//Yes 24 hour time

                        mTimePicker.show();
                    }
                });


                stoptime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(Listscheduler.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                                String stoptext = "";

                                stophourset = selectedHour;
                                stopminuteset = selectedMinute;

                                if(selectedHour<10){
                                    if(selectedMinute<10){
                                        stoptext = "0"+selectedHour+":0"+selectedMinute;
                                    }else{
                                        stoptext = "0"+selectedHour+":"+selectedMinute;
                                    }
                                }else{
                                    if(selectedMinute<10){
                                        stoptext = selectedHour+":0"+selectedMinute;
                                    }else{
                                        stoptext = selectedHour+":"+selectedMinute;
                                    }
                                }

                                stoptime.setText(stoptext);
                            }
                        }, hourStop, minuteStop, true);//Yes 24 hour time

                        mTimePicker.show();
                    }
                });


                btnscheduler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //check something
                        checkline(starthourset,startminuteset,stophourset,stopminuteset);


                        if(checkagain == true){
                            again.setText("Please set time again..");
                            checkagain = false;
                        }else{

                            int start = (starthourset*3600)+(startminuteset*60);
                            int stop  = (stophourset*3600)+(stopminuteset*60);

                            if(stop <= start){
                                again.setText("Please set time again..");

                            }else{

                                addDB();
                                Handler handler = new Handler();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        saveCheduler();
                                    }
                                };handler.postDelayed(runnable,100);

                                Intent in = new Intent(dialog.getContext(),Listscheduler.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(in);
                                dialog.cancel();

                            }
                        }
                    }
                });
                dialog.show();


            }
        });



        System.out.println("oncreate");
    }


    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("ListActive", true).commit();
        System.out.println("onResumeList");

    }


    public void onPause(){
        super.onPause();
    }

    public void onStop(){
        super.onStop();
        System.out.println("onStopList");
    }

    public void onDestroy(){
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("ListActive", false).commit();
        System.out.println("onDestroyList");
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(in);
        System.out.println("onBackPressedList");
       // moveTaskToBack(true);

    }


    public void showlist(){

        SQLiteDatabase dbscheduler = scheduler.getWritableDatabase();
        Cursor resscheduler = dbscheduler.rawQuery("select * from "+TABLE_NAME9,null);

        int countresscheduler = resscheduler.getCount();

        if(countresscheduler==0){
            return;
        }else{

            while(resscheduler.moveToNext()){
                listid.add(resscheduler.getString(0));
                listday.add(resscheduler.getString(1));
                listdate.add(resscheduler.getString(2));
                listmonth.add(resscheduler.getString(3));
                listyear.add(resscheduler.getString(4));
                liststarthour.add(resscheduler.getString(5));
                liststartminute.add(resscheduler.getString(6));
                liststophour.add(resscheduler.getString(7));
                liststopminute.add(resscheduler.getString(8));
                liststatus.add(resscheduler.getString(9));
            }

        }
    }



    //for check not between set before
    void checkline(int starthourset,int startminuteset,int stophourset,int stopminuteset){

        int count=0;
        ArrayList<String> checkstarthour = new ArrayList<>();
        ArrayList<String> checkstartminute = new ArrayList<>();
        ArrayList<String> checkstophour = new ArrayList<>();
        ArrayList<String> checkstopminute = new ArrayList<>();

        SQLiteDatabase dbscheduler = scheduler.getWritableDatabase();
        Cursor resscheduler = dbscheduler.rawQuery("select * from "+TABLE_NAME9,null);

        if(resscheduler.getCount()==0){
            return;
        }else{

            while (resscheduler.moveToNext()){
                checkstarthour.add(count,resscheduler.getString(5));
                checkstartminute.add(count,resscheduler.getString(6));
                checkstophour.add(count,resscheduler.getString(7));
                checkstopminute.add(count,resscheduler.getString(8));
                count++;
            }
        }

        int millistartset = (starthourset*3600)+(startminuteset*60);
        int millistopset = (stophourset*3600)+(stopminuteset*60);

        for(int i=0;i<checkstarthour.size();++i){

            if( millistartset >= ((Integer.parseInt(checkstarthour.get(i))*3600)+(Integer.parseInt(checkstartminute.get(i))*60)) &&
                    millistartset <= ((Integer.parseInt(checkstophour.get(i))*3600)+(Integer.parseInt(checkstopminute.get(i))*60))){
                //don't add to db
                checkagain = true;
                break;
            }

            if(millistopset <= (((Integer.parseInt(checkstophour.get(i))*3600))+((Integer.parseInt(checkstopminute.get(i))*60))) &&
                    millistopset >= ((Integer.parseInt(checkstarthour.get(i))*3600)+(Integer.parseInt(checkstartminute.get(i))*60))){
                checkagain = true;
                break;
            }

        }

    }



    void addDB(){

        int time = ((starthourset*3600)+(startminuteset*60))-((new Date().getHours()*3600)+(new Date().getMinutes()*60));

        if(time<=0){

            calendar.add(Calendar.DATE,+1);
            getdaycalendar = calendar.getTime().getDay();
            getdatecalendar = calendar.getTime().getDate();
            getmonthcalendar = calendar.getTime().getMonth();
            getyearcalendar = calendar.getTime().getYear();

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

        }else{

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

        }

    }
    public void saveCheduler(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        Cursor reDef1 = scheduler.getAlldata();
        String USER_ID = acct.getId();
        while(reDef1.moveToNext()){
            DataScheduler2 DataScheduler = new DataScheduler2(USER_ID,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),
                    reDef1.getString(3),reDef1.getString(4),reDef1.getString(5),
                    reDef1.getString(6),reDef1.getString(7),reDef1.getString(8),reDef1.getString(9));
            Call<DataScheduler2>callscheduler2 =apiInterface.DataChedulernew(DataScheduler);
            callscheduler2.enqueue(new Callback<DataScheduler2>() {
                @Override
                public void onResponse(Call<DataScheduler2> call, Response<DataScheduler2> response) {
                    Log.d("TEST_POST_JSON", "JSON POST Cheduler: " + response.body().getMessages());
                }

                @Override
                public void onFailure(Call<DataScheduler2> call, Throwable t) {

                }
            });

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
                        Log.d("TEST_GET_Cheduler", "StipM: " +Listdata.get(i).getStophour());
                        Log.d("TEST_GET_Cheduler", "Status: " +Listdata.get(i).getStatus());
                        SQLiteDatabase dbscheduler = scheduler.getWritableDatabase();
                        ContentValues valuescheduler = new ContentValues();
                        valuescheduler.put(DAYSTART,Listdata.get(i).getDay());
                        valuescheduler.put(DATESTART,Listdata.get(i).getDate());
                        valuescheduler.put(MONTHSTART,Listdata.get(i).getMonth());
                        valuescheduler.put(YEARSTART,Listdata.get(i).getYear());
                        valuescheduler.put(TIMESTARTHOUR,Listdata.get(i).getStarthour());
                        valuescheduler.put(TIMESTARTMINUTE,Listdata.get(i).getStartminute());
                        valuescheduler.put(TIMESTOPHOUR,Listdata.get(i).getStophour());
                        valuescheduler.put(TIMESTOPMINUTE,Listdata.get(i).getStophour());
                        valuescheduler.put(STATUS,Listdata.get(i).getStatus());
                        dbscheduler.insertOrThrow(TABLE_NAME9, null, valuescheduler);
                        DeleteScheduler(Listdata.get(i).getID());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DataScheduler>> call, Throwable t) {

            }
        });

    }

    public void DeleteScheduler(String ID){
        String ID1=ID;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String USER_ID = acct.getId();
        //Log.d("TEST_DELETE_scheduler", "USER_ID:" +USER_ID);
        //Log.d("TEST_DELETE_scheduler", "GET ID:" + ID1);
        DataScheduler2 deletedata = new DataScheduler2(USER_ID,ID1);
        Call<DataScheduler2>Calldeletedata =apiInterface.DeleteCheduler(deletedata);
        Calldeletedata.enqueue(new Callback<DataScheduler2>() {
            @Override
            public void onResponse(Call<DataScheduler2> call, Response<DataScheduler2> response) {
                Log.e("TEST_DELETE_scheduler", "Messages:" + response.body().getMessages());
            }

            @Override
            public void onFailure(Call<DataScheduler2> call, Throwable t) {

            }
        });
    }

}
