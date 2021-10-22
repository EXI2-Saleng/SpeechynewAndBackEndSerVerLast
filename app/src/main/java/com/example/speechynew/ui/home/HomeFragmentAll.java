package com.example.speechynew.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.speechynew.R;
import com.example.speechynew.Rertofit.ApiClient;
import com.example.speechynew.Rertofit.ApiInterface;
import com.example.speechynew.analysis.Translator;
import com.example.speechynew.connectDB.Continuemax;
import com.example.speechynew.connectDB.DataAnyword;
import com.example.speechynew.connectDB.DataContinuemax;
import com.example.speechynew.connectDB.DataEngword;
import com.example.speechynew.connectDB.DataRawdata;
import com.example.speechynew.connectDB.DataScheduler;
import com.example.speechynew.connectDB.DataSetting;
import com.example.speechynew.connectDB.DataTime;
import com.example.speechynew.connectDB.DataWrongword;
import com.example.speechynew.connectDB.Data_User;
import com.example.speechynew.connectDB.Engword;
import com.example.speechynew.connectDB.Rawdata;
import com.example.speechynew.connectDB.Scheduler;
import com.example.speechynew.connectDB.Setting;
import com.example.speechynew.connectDB.Timeprocess;
import com.example.speechynew.connectDB.Word;
import com.example.speechynew.connectDB.Wrongword;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.speechynew.connectDB.Continuemaxinterface.TABLE_NAME10;
import static com.example.speechynew.connectDB.Engwordinterface.TABLE_NAME2;
import static com.example.speechynew.connectDB.Timeprocessinterface.TABLE_NAME5;
import static com.example.speechynew.connectDB.Wordinterface.TABLE_NAME3;
import static com.example.speechynew.connectDB.Wrongwordinterface.TABLE_NAME11;


public class HomeFragmentAll extends Fragment {

    ApiInterface apiInterface;

    private HomeViewModel homeViewModel;

    //view
    TextView day;
    BarChart mChart;
    Button changetype;
    ImageButton graphminus;
    ImageButton graphplus;
    Button nextpage;
    TextView wait5;

    //database
    Engword eng;
    Word anothereng;
    Timeprocess time;
    Continuemax continuemax;
    Wrongword wrongword;
    Rawdata rawdata;
    Scheduler scheduler;
    Setting setting;

    //calendar
    Calendar c ;
    SimpleDateFormat df;
    String formattedDate;
    int getFormattedDay;
    int getFormattedMonth;
    int getFormattedYear;
    int getFormattednameday;

    String datename;
    String monthname;
    boolean resulttype;

    String totalwordday;
    String totaltimeday;
    int totalwordeng = 0;
    int totaltimeeng = 0;
    double wordminday;
    int continuemaxday = 0;
    String[] wordtop = new String[3];
    String[] wordtrans = new String[3];
    String showday;
    private FirebaseAuth firebaseAuth;

    int indextotalengword = 0;
    int indextotalanyword = 0;
    int indextotalall = 0;
    int indextotalmeeng =0;

    int[] arraytesteng1 = new int[24];
    int[] arraytestnone1 = new int[24];

    GoogleSignInClient mGoogleSignInClient;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        datename=" ";
        monthname=" ";

        mChart = (BarChart) root.findViewById(R.id.mp_barchart);
        day = root.findViewById(R.id.showday);
        graphminus = root.findViewById(R.id.graphminus);
        graphplus = root.findViewById(R.id.graphplus);
        changetype = root.findViewById(R.id.changetype);
        nextpage = root.findViewById(R.id.nextpage);
        wait5 = root.findViewById(R.id.wait5);

        //create object database
        eng = new Engword(root.getContext());
        anothereng = new Word(root.getContext());
        setting = new Setting(root.getContext());
        time = new Timeprocess(root.getContext());
        continuemax = new Continuemax(root.getContext());
        wrongword = new Wrongword(root.getContext());

        rawdata = new Rawdata(root.getContext());
        scheduler = new Scheduler(root.getContext());

        resulttype = false;

        c = Calendar.getInstance();
        df = new SimpleDateFormat("d-MM-yyyy");
        formattedDate = df.format(c.getTime());
        getFormattednameday = c.getTime().getDay();
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();

        wait5.setText(" Please wait 5 second");

        //call method
        nextpageonclick();
        //newviewAllday();
        //viewtotalday();
        //viewtotaltimeday();
        //wordmin();
        //continuemaxday();
        //wrongwordday();
        getdataAnyword();
        getdataContinuemax();
        getdataViewtotaltimeday();
        getdataViewWrongword();
        getdataViewtotalday();




        mChart.getDescription().setEnabled(false);
        mChart.setFitBars(true);

        getnamemonth(getFormattedMonth);
        day.setText("Today, "+getFormattedDay+" "+monthname+" "+(getFormattedYear+1900));
        showday = "Today, "+getFormattedDay+" "+monthname+" "+(getFormattedYear+1900);



        //minus day -
        graphminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                getFormattednameday = c.getTime().getDay();
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = c.getTime().getMonth();
                getFormattedYear = c.getTime().getYear();

                wait5.setText(" Please wait 5 second");

                nextpageonclick();
                //newviewAllday();
                //viewtotalday();
                //viewtotaltimeday();
                //wordmin();
                //continuemaxday();
                //wrongwordday();
                getdataAnyword();
                getdataContinuemax();
                getdataViewtotaltimeday();
                getdataViewWrongword();
                getdataViewtotalday();



                getnameday(getFormattednameday);
                getnamemonth(getFormattedMonth);

                day.setText(datename+", "+getFormattedDay+" "+monthname+" "+(getFormattedYear+1900));
                showday = datename+", "+getFormattedDay+" "+monthname+" "+(getFormattedYear+1900);


            }
        });

        //plus day +
        graphplus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, +1);
                formattedDate = df.format(c.getTime());
                getFormattednameday = c.getTime().getDay();
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = c.getTime().getMonth();
                getFormattedYear = c.getTime().getYear();


                if(c.after(Calendar.getInstance())){ // > current time
                    c.add(Calendar.DATE, -1);
                    formattedDate = df.format(c.getTime());
                    getFormattednameday = c.getTime().getDay();
                    getFormattedDay = c.getTime().getDate();
                    getFormattedMonth = c.getTime().getMonth();
                    getFormattedYear = c.getTime().getYear();

                }else{
                    wait5.setText(" Please wait 5 second");

                    nextpageonclick();
                    //newviewAllday();
                    //viewtotaltimeday();
                    //viewtotalday();
                    //wordmin();
                    //continuemaxday();
                   // wrongwordday();
                    getdataAnyword();
                    getdataContinuemax();
                    getdataViewtotaltimeday();
                    getdataViewWrongword();
                    getdataViewtotalday();


                    getnameday(getFormattednameday);
                    getnamemonth(getFormattedMonth);

                    day.setText(datename+", "+getFormattedDay+" "+monthname+" "+(getFormattedYear+1900));
                    showday = datename+", "+getFormattedDay+" "+monthname+" "+(getFormattedYear+1900);
                }

            }
        });


        changetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change from word count to Percentage
                if(resulttype==false){
                    resulttype=true;
                   // newviewAllday();
                    getdataViewtotalday();

                    changetype.setText("Word count");

                //change from Percentage to word count
                }else if(resulttype==true){
                    resulttype=false;
                    //newviewAllday();
                    getdataViewtotalday();
                    changetype.setText("Percentage");
                }

            }
        });


        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);

            }
        });

        return root;

    }


    void nextpageonclick(){

        nextpage.setEnabled(false);
        nextpage.setBackgroundResource(R.drawable.buttonshape11);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                nextpage.setEnabled(true);
                nextpage.setBackgroundResource(R.drawable.buttonshape6);
                wait5.setText("");

                nextpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent in = new Intent(getContext(),HomeReportAll.class);
                        in.putExtra("test",showday);
                        in.putExtra("totalwordday",totalwordday);
                        in.putExtra("totaltimeday",totaltimeday);
                        in.putExtra("wordminday",wordminday);
                        in.putExtra("continuemaxday",continuemaxday);
                        in.putExtra("wordtop",wordtop);
                        in.putExtra("wordtrans",wordtrans);
                        startActivity(in);

                    }
                });
            }

        };handler.postDelayed(runnable,4000);
    }




    public void getnamemonth(int month){
        if((month+1)==1){
            monthname="January";
        }else if((month+1)==2){
            monthname="February";
        }else if((month+1)==3){
            monthname="March";
        }else if((month+1)==4){
            monthname="April";
        }else if((month+1)==5){
            monthname="May";
        }else if((month+1)==6){
            monthname="June";
        }else if((month+1)==7){
            monthname="July";
        }else if((month+1)==8){
            monthname="August";
        }else if((month+1)==9){
            monthname="September";
        }else if((month+1)==10){
            monthname="October";
        }else if((month+1)==11){
            monthname="November";
        }else if((month+1)==12){
            monthname="December";
        }
    }

    public void getnameday(int day){
        if(day==0){
            datename="Sunday";
        }else if(day==1){
            datename="Monday";
        }else if(day==2){
            datename="Tuesday";
        }else if(day==3){
            datename="Wednesday";
        }else if(day==4){
            datename="Thursday";
        }else if(day==5){
            datename="Friday";
        }else if(day==6){
            datename="Saturday";
        }


    }



    private class MyValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;
        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");
        }
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if(value > 0) {
                return mFormat.format(value);
            } else {
                return "";
            }
        }

    }





    public void getdataAnyword(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String USER_ID = acct.getId();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();


        String showday1 = S_day+" : "+S_month+" : "+S_year;
        Log.d("TEST_SHOW_DAY",showday1);
        Log.d("TEST_SHOW_DAY","Email :"+email);
        int[] totalall = {0};
        int[] totaleng = {0};
        int[] totalanother = {0};
        int[] arraytestnone2 = new int[24];
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        Call<List<DataAnyword>>listcallgetdata = apiInterface.getDataAnyword(USER_ID,S_day,S_month,S_year);
            listcallgetdata.enqueue(new Callback<List<DataAnyword>>() {
                @Override
                public void onResponse(Call<List<DataAnyword>> call, Response<List<DataAnyword>> response) {
                    if (response.isSuccessful()) {
                        List<DataAnyword> listdata = response.body();
                        if (listdata.size() == 0) {
                            totalanother[0] = 0;
                        } else {
                            for (int i = 0; i < listdata.size(); i++) {
                                totalanother[0] += Integer.parseInt(listdata.get(i).getWord());
                                indextotalanyword = totalanother[0];
                                arraytestnone2[Integer.parseInt(listdata.get(i).getHour())]+=Integer.parseInt(listdata.get(i).getWord());

                            }
                            StringBuffer buffer = new StringBuffer();
                            arraytestnone1=arraytestnone2;
                            for(int i = 0;i<24 ;i++){
                                buffer.append(":"+arraytestnone2[i]+" ");
                            }
                            //Log.d("GET_View_G","ARRAY_ANYWORD :"+buffer.toString());

                            Log.e("TEST_SHOW_DAY", "indextotalanyword :" + indextotalanyword);

                        }


                    }
                    else{
                        Log.d("TEST_GET_LISTDATA","Fail:"+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<DataAnyword>> call, Throwable t) {
                    Log.d("TEST_GET_LISTDATA",t+"");
                }
            });



    }

    public void getdataContinuemax(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String USER_ID = acct.getId();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();



        Call<List<DataContinuemax>>listcallgetdata = apiInterface.getDataContinuemax(USER_ID,S_day,S_month,S_year);
        listcallgetdata.enqueue(new Callback<List<DataContinuemax>>() {
            @Override
            public void onResponse(Call<List<DataContinuemax>> call, Response<List<DataContinuemax>> response) {
                if (response.isSuccessful()) {
                    List<DataContinuemax> listdata = response.body();
                    int checking = 0;
                    int continuemaxday1 =0;
                    if (listdata.size() !=0){
                        for(int i= 0;i<listdata.size();i++){
                            checking=Integer.parseInt(listdata.get(i).getMaxcon());

                            if(continuemaxday1 < checking){
                                continuemaxday1 = checking;

                            }


                        }
                        continuemaxday =continuemaxday1;
                        Log.e("TEST_SHOW_DAY","continuemaxday: "+continuemaxday1);
                    }


                }
                else{
                    Log.d("TEST_GET_LISTDATA","Fail:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DataContinuemax>> call, Throwable t) {
                Log.d("TEST_GET_LISTDATA",t+"");
            }
        });

    }

    public void getdataEngword(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();



        Call<List<DataEngword>>listcallgetdata = apiInterface.getDataEngword(email,S_day,S_month,S_year);
        listcallgetdata.enqueue(new Callback<List<DataEngword>>() {
            @Override
            public void onResponse(Call<List<DataEngword>> call, Response<List<DataEngword>> response) {
                int totalall = 0;
                int totaleng = 0;
                int totalanother = 0;
                if (response.isSuccessful()) {
                    List<DataEngword> listdata = response.body();
                    if (listdata.size()==0){
                        totaleng=0;
                    }
                    else {

                        for(int i= 0;i<listdata.size();i++){
                            totaleng+=Integer.parseInt(listdata.get(i).getWord());
                        }
                    }



                    Log.e("TEST_GET_LISTDATA","================= getdataEngword =================");
                    Log.e("TEST_GET_LISTDATA","Successful");
                    for(int i= 0;i<listdata.size();i++){
                        Log.e("TEST_GET_LISTDATA","Engword: "+listdata.get(i).getEngword());
                        Log.e("TEST_GET_LISTDATA","DATE: "+listdata.get(i).getDate());

                    }

                }
                else{
                    Log.d("TEST_GET_LISTDATA","Fail:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DataEngword>> call, Throwable t) {
                Log.d("TEST_GET_LISTDATA",t+"");
            }
        });

    }

    public void getdataViewtotaltimeday(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String USER_ID = acct.getId();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();



        Call<List<DataTime>>listcallgetdata = apiInterface.getDataTime(USER_ID,S_day,S_month,S_year);
        listcallgetdata.enqueue(new Callback<List<DataTime>>() {
            @Override
            public void onResponse(Call<List<DataTime>> call, Response<List<DataTime>> response) {
                if (response.isSuccessful()) {
                    List<DataTime> listdata = response.body();
                    int totaltime1 = 0;

                    if (listdata.size()==0){
                        totaltime1 = 0;
                    }
                    else {
                        for(int i= 0;i<listdata.size();i++){
                            totaltime1+=Integer.parseInt(listdata.get(i).getTotaltime());
                        }
                        indextotalmeeng =totaltime1;
                        Log.e("TEST_SHOW_DAY","(totaltime1) totaltimeeng: "+totaltime1);
                    }

                    int numberOfHours = (totaltime1 % 86400) / 3600;
                    int numberOfMinutes = ((totaltime1 % 86400) % 3600) / 60;
                    int numberOfSeconds = ((totaltime1 % 86400) % 3600) % 60;

                    if(numberOfMinutes<10){
                        if(numberOfSeconds<10){
                            String text = numberOfHours+" : 0"+numberOfMinutes+" : 0"+numberOfSeconds;
                            totaltimeday =text;
                            Log.e("TEST_SHOW_DAY","totaltimeday: "+text);
                        }else{
                            String text = numberOfHours+" : 0"+numberOfMinutes+" : "+numberOfSeconds;
                            totaltimeday =text;
                            Log.e("TEST_SHOW_DAY","totaltimeday: "+text);
                        }
                    }else{
                        String text = numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                        totaltimeday =text;
                        Log.e("TEST_SHOW_DAY","totaltimeday: "+text);
                    }

                }
                else{
                    Log.d("TEST_GET_LISTDATA","Fail:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DataTime>> call, Throwable t) {
                Log.d("TEST_GET_LISTDATA",t+"");
            }
        });

    }

    public void getdataViewWrongword(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String USER_ID = acct.getId();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();

        Call<List<DataWrongword>>listcallgetdata = apiInterface.getDataWrongword(USER_ID,S_day,S_month,S_year);
        listcallgetdata.enqueue(new Callback<List<DataWrongword>>() {
            @Override
            public void onResponse(Call<List<DataWrongword>> call, Response<List<DataWrongword>> response) {
                if (response.isSuccessful()) {
                    List<DataWrongword> listdata = response.body();

                    ArrayList<String> wordfromdb = new ArrayList<>();
                    String[] wordtop1 = new String[3];
                    String[] wordtrans1 = new String[3];
                    if (listdata.size()==0){
                        return;
                    }
                    else {
                        for(int i= 0;i<listdata.size();i++){
                            wordfromdb.add(listdata.get(i).getWord());
                        }

                        //wordcount
                        int N = wordfromdb.size();
                        String word[] = new String[N];
                        int count[] = new int[N];

                        for (int i = 0; i < word.length; i++) {
                            word[i] = ""; //set default
                        }

                        for (int i = 0; i < N; i++) {
                            String text = wordfromdb.get(i);
                            for (int j = 0; j < word.length; j++) {
                                if (word[j].equals("")) {
                                    word[j] = text;
                                    count[j] = 1;
                                    break;
                                } else if (word[j].equals(text)) {
                                    count[j]++;
                                    break;
                                }
                            }
                        }

                        for (int i = 0; i < N; i++) {
                            for (int j = i+1; j < N-1; j++) {
                                if(count[i] < count[j] && !word[i].equals("") && !word[j].equals("")){
                                    int temp = count[i];
                                    count[i] = count[j];
                                    count[j] = temp;

                                    String tempText = word[i];
                                    word[i] = word[j];
                                    word[j] = tempText;
                                }
                            }
                        }


                        for (int i = 0; i < word.length; i++) {
                            if (!word[i].equals("")) {
                                //System.out.println(word[i] + " " + count[i]);
                            }
                        }



                        if(word.length<3){
                            for(int i=0;i<word.length;++i){
                                if (!word[i].equals("")) {
                                    wordtop[i] = word[i];
                                   // wordtop1[i] = word[i];

                                    final Translator t = new Translator(wordtop[i],getContext());
                                    //final Translator t = new Translator(wordtop1[i],getContext());
                                    t.trans();

                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            wordtrans[finalI] = t.trans();
                                           // wordtrans1[finalI] = t.trans();

                                        }
                                    };handler.postDelayed(runnable,4000);

                                }
                            }

                        }else{
                            for (int i = 0; i < 3; i++) {
                                if (!word[i].equals("")) {
                                    wordtop[i] = word[i];
                                    //wordtop1[i] = word[i];

                                    final Translator t = new Translator(wordtop[i],getContext());
                                   // final Translator t = new Translator(wordtop1[i],getContext());
                                    t.trans();

                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            wordtrans[finalI] = t.trans();
                                           // wordtrans1[finalI] = t.trans();
                                        }
                                    };handler.postDelayed(runnable,4000);
                                }
                            }
                        }
                        ///wordtop[0]="TEST1";wordtop[1]="TEST2";wordtop[2]="TEST3";
                        Log.e("TEST_SHOW_DAY", "WTop1 MySQL:" +wordtop1[0]+" WTop2 :" +wordtop1[1]+" WTop3 :" +wordtop1[2]);
                        Log.d("TEST_SHOW_DAY", "WTop1 SQLite:" +wordtop[0]+" WTop2 :" +wordtop[1]+" WTop3 :" +wordtop[2]);
                       // Log.e("Data_View", "WTrans1 :" +wordtrans1[0]+" WTrans2 :" +wordtrans1[1]+" WTrans3 :" +wordtrans1[2]);


                    }



                }
                else{
                    Log.d("TEST_GET_LISTDATA","Fail:"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DataWrongword>> call, Throwable t) {
                Log.d("TEST_GET_LISTDATA",t+"");
            }
        });

    }

    public void getdataViewtotalday() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        String USER_ID = acct.getId();
        String S_day = Integer.valueOf(getFormattedDay).toString();
        String S_month = Integer.valueOf(getFormattedMonth + 1).toString();
        String S_year = Integer.valueOf(getFormattedYear + 1900).toString();
        String email = firebaseUser.getEmail();

        int[] totalall = {0};
        int[] totaleng = {0};
        int[] totalanother = {0};
        int[] arraytesteng2 = new int[24];
        double[] arrayall1 = new double[24];

        ArrayList<BarEntry> dataVals = new ArrayList<>();
            Call<List<DataEngword>> listcallgetdataEngword = apiInterface.getDataEngword(USER_ID, S_day, S_month, S_year);
            listcallgetdataEngword.enqueue(new Callback<List<DataEngword>>() {
                @Override
                public void onResponse(Call<List<DataEngword>> call, Response<List<DataEngword>> response) {

                    if (response.isSuccessful()) {
                        List<DataEngword> listdata = response.body();
                        if (listdata.size() == 0) {
                            totaleng[0] = 0;
                        } else {

                            for (int i = 0; i < listdata.size(); i++) {
                                totaleng[0] += Integer.parseInt(listdata.get(i).getWord());
                                arraytesteng2[Integer.parseInt(listdata.get(i).getHour())]+=Integer.parseInt(listdata.get(i).getWord());
                            }

                            arraytesteng1=arraytesteng2;

                            indextotalengword = totaleng[0];
                        }


                        totalall[0] = totaleng[0] + totalanother[0];
                        int TTWE = totaleng[0];
                        String TTWD = totaleng[0] + " / " + totalall[0];

                        indextotalall = indextotalanyword + indextotalengword;
                        String TTWD1 = indextotalengword + " / " + indextotalall;

                        if(resulttype == false) {
                            StringBuffer buffer1 = new StringBuffer();
                            StringBuffer buffer2 = new StringBuffer();
                            for (int i = 0; i < 24; ++i) {
                                int valueseng = arraytesteng2[i];
                                int valuesno = arraytestnone1[i];
                                buffer1.append(":" + arraytestnone1[i] + " ");
                                buffer2.append(":" + arraytesteng2[i] + " ");
                                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));
                            }
                            Log.d("GET_View_G", "ARRAYENG_THAI :" + buffer1.toString());
                            Log.d("GET_View_G", "ARRAYENG_WORD :" + buffer2.toString());

                            //setting bar chart

                        BarDataSet barDataSet = new BarDataSet(dataVals, " ");
                        barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
                        barDataSet.setStackLabels(new String[]{"Number of English words", "Number of non-English words"});

                        BarData data = new BarData(barDataSet);
                        mChart.setData(data);

                        data.setValueFormatter(new MyValueFormatter());

                        mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างข้อมูล
                        mChart.getLegend().setFormToTextSpace(3);//ระยะห่างระหว่างรูปกับคำอธิบาย

                        String[] time = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11",
                                "12","13","14","15","16","17","18","19","20","21","22","23"};

                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setGranularity(1);
                        xAxis.setCenterAxisLabels(false);
                        xAxis.setGranularityEnabled(true);
                        xAxis.setAxisMaximum(24);
                        xAxis.setDrawGridLines(false); //เส้นตาราง

                        mChart.setDragEnabled(true);
                        mChart.getAxisRight().setAxisMinimum(0);
                        mChart.getAxisLeft().setAxisMinimum(0);
                        mChart.setVisibleXRangeMaximum(24);
                        mChart.invalidate();
                        mChart.animateXY(2000, 4000);
                        mChart.setDoubleTapToZoomEnabled(true);
                        mChart.setPinchZoom(true);
                        mChart.fitScreen();




                        }
                        else if(resulttype == true){
                            //get all word
                            for(int i = 0; i < 24; ++i){
                                arrayall1[i] = arraytesteng2[i];
                                arrayall1[i] += arraytestnone1[i];
                            }
                            StringBuffer buffer1 = new StringBuffer();
                            StringBuffer buffer2 = new StringBuffer();
                            //set data in bar chart
                            for (int i = 0; i < 24; ++i) {
                                float valueseng;
                                double testvaleng = arraytesteng2[i] / arrayall1[i];

                                if (Double.isNaN(testvaleng)) {
                                    valueseng = (float) 0;
                                } else {
                                    valueseng = (float) testvaleng * 100;
                                }

                                float valuesno;
                                double testvalno = arraytestnone1[i] / arrayall1[i];

                                if (Double.isNaN(testvalno)) {
                                    valuesno = (float) 0;
                                } else {
                                    valuesno = (float) testvalno * 100;
                                }
                                buffer1.append(":" + valuesno + " ");
                                buffer2.append(":" + valueseng + " ");



                                dataVals.add(new BarEntry(i, new float[]{valueseng,valuesno}));
                            }
                            Log.d("GET_View_G", "ARRAYENG_THAI :" + buffer1);
                            Log.d("GET_View_G", "ARRAYENG_WORD :" + buffer2);

                            //setting bar chart
                            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
                            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
                            barDataSet.setStackLabels(new String[]{"Percentage of English words", "Percentage of non-English words"});
                            BarData data = new BarData(barDataSet);
                            data.setValueFormatter(new MyValueFormatter());

                            mChart.setData(data);//ระยะห่างระหว่างข้อมูล
                            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
                            mChart.getLegend().setFormToTextSpace(3);

                            String[] time = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"
                                    , "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

                            XAxis xAxis = mChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(time));
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setGranularity(1);
                            xAxis.setCenterAxisLabels(false);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setAxisMaximum(24);
                            xAxis.setDrawGridLines(false); //เส้นตาราง

                            mChart.setDragEnabled(true);
                            mChart.getAxisRight().setAxisMinimum(0);
                            mChart.getAxisLeft().setAxisMinimum(0);
                            mChart.setVisibleXRangeMaximum(24);
                            mChart.invalidate();
                            mChart.animateXY(2000, 4000);
                            mChart.setDoubleTapToZoomEnabled(true);
                            mChart.setPinchZoom(true);
                            mChart.fitScreen();



                        }

                        //totalwordday =TTWD1;
                        totalwordday ="TEST";
                        Log.e("TEST_SHOW_DAY", "indextotalengword :" + indextotalengword);
                        Log.e("TEST_SHOW_DAY", "indextotalall :" + TTWD1);

                        getdataViewwordminday(indextotalengword);

                    } else {
                        Log.d("TEST_GET_LISTDATA", "Fail:" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<DataEngword>> call, Throwable t) {
                    Log.d("TEST_GET_LISTDATA", t + "");
                }
            });

    }
    public void getdataViewwordminday(int x){
        indextotalengword = x;
        double total1= (double)  indextotalengword * 1.0;
        double total2 = (double) indextotalmeeng / 60.0;
        if (total2 == 0.0){
            total2 =1.0;
        }
        double wordminday1 = total1/total2;
        wordminday =wordminday1;
        Log.e("TEST_SHOW_DAY","wordminday: "+wordminday1);
        Log.d("TEST_SHOW_DAY","wordminday: "+"TESTTTTTT");



    }




}