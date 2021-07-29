package com.example.speechynew.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.speechynew.MainActivity;
import com.example.speechynew.R;
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
import com.example.speechynew.connectDB.User;
import com.example.speechynew.connectDB.Word;
import com.example.speechynew.connectDB.Wrongword;
import com.example.speechynew.connectDB.backup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.speechynew.connectDB.Continuemaxinterface.TABLE_NAME10;
import static com.example.speechynew.connectDB.Engwordinterface.TABLE_NAME2;
import static com.example.speechynew.connectDB.Timeprocessinterface.TABLE_NAME5;
import static com.example.speechynew.connectDB.Wordinterface.TABLE_NAME3;
import static com.example.speechynew.connectDB.Wrongwordinterface.TABLE_NAME11;


import  com.example.speechynew.Rertofit.ApiClient;
import  com.example.speechynew.Rertofit.ApiInterface;
import  com.example.speechynew.connectDB.UserData;
import  com.example.speechynew.MainActivity;




import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class HomeFragment extends Fragment {

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
        newviewAllday();
        viewtotalday();
        viewtotaltimeday();
        wordmin();
        continuemaxday();
        wrongwordday();



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
                newviewAllday();
                viewtotalday();
                viewtotaltimeday();
                wordmin();
                continuemaxday();
                wrongwordday();

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
                    newviewAllday();
                    viewtotaltimeday();
                    viewtotalday();
                    wordmin();
                    continuemaxday();
                    wrongwordday();

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
                    newviewAllday();
                    getdataViewtotalday();
                    changetype.setText("Word count");

                //change from Percentage to word count
                }else if(resulttype==true){
                    resulttype=false;
                    newviewAllday();
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

                        Intent in = new Intent(getContext(),HomeReport.class);
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



    public void newviewAllday(){


        if(resulttype==false) { //view word count

            ArrayList<BarEntry> dataVals = new ArrayList<>();

            int manyhour = 24;
            int[] arraytesteng = new int[manyhour];
            int[] arraytestnone = new int[manyhour];

            //eng word
            SQLiteDatabase dbeng = eng.getWritableDatabase();
            Cursor reseng = dbeng.rawQuery("select * from " + TABLE_NAME2 + " where date = " + getFormattedDay + " and month = " +
                                                (getFormattedMonth + 1) + " and year = " + (getFormattedYear + 1900), null);

            if (reseng.getCount() == 0) { //set 0
                for (int i = 0; i < manyhour; ++i) {
                    int values = 0;
                    //yValsEng.add(new BarEntry(i, values));
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (reseng.moveToNext()) {
                arraytesteng[reseng.getInt(6)]+=reseng.getInt(1);
            }

            //not eng word
            SQLiteDatabase dbnone = anothereng.getWritableDatabase();
            Cursor resnone = dbnone.rawQuery("select * from " + TABLE_NAME3 + " where date = " + getFormattedDay + " and month = " +
                                                  (getFormattedMonth + 1) + " and year = " + (getFormattedYear + 1900), null);

            if (resnone.getCount() == 0) {
                for (int i = 0; i < manyhour; ++i) {
                    int values = 0;
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (resnone.moveToNext()) {
                arraytestnone[resnone.getInt(6)]+=resnone.getInt(1);
            }


            for(int i=0;i<manyhour;++i){
                int valueseng = arraytesteng[i];
                int valuesno = arraytestnone[i];
                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));
            }

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





        }else if(resulttype==true){ //view percentage

            ArrayList<BarEntry> dataVals = new ArrayList<>();

            double[] arraytesteng = new double[24];
            double[] arraytestnone = new double[24];
            double[] arrayall = new double[24];
            int manyhour = 24;

            //eng word
            SQLiteDatabase dbeng = eng.getWritableDatabase();
            Cursor reseng = dbeng.rawQuery("select * from " + TABLE_NAME2 + " where date = " + getFormattedDay + " and month = " +
                                                (getFormattedMonth + 1) + " and year = " + (getFormattedYear + 1900), null);

            if (reseng.getCount() == 0) {
                for (int i = 0; i < manyhour; ++i) {
                    int values = 0;
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (reseng.moveToNext()) {
                arraytesteng[reseng.getInt(6)]+=reseng.getInt(1);
            }


            //not eng word
            SQLiteDatabase dbnone = anothereng.getWritableDatabase();
            Cursor resnone = dbnone.rawQuery("select * from " + TABLE_NAME3 + " where date = " + getFormattedDay + " and month = " +
                                                  (getFormattedMonth + 1) + " and year = " + (getFormattedYear + 1900), null);

            if (resnone.getCount() == 0) {
                for (int i = 0; i < manyhour; ++i) {
                    int values = 0;
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (resnone.moveToNext()) {
                arraytestnone[resnone.getInt(6)]+=resnone.getInt(1);
            }

            //get all word
            for(int i = 0; i < manyhour; ++i){
                arrayall[i] = arraytesteng[i];
            }
            for(int i = 0; i < manyhour; ++i){
                arrayall[i] +=arraytestnone[i];
            }


            //set data in bar chart
            for (int i = 0; i < manyhour; ++i) {
                float valueseng;
                double testvaleng = arraytesteng[i] / arrayall[i];

                if (Double.isNaN(testvaleng)) {
                    valueseng = (float) 0;
                } else {
                    valueseng = (float) testvaleng * 100;
                }

                float valuesno;
                double testvalno = arraytestnone[i] / arrayall[i];

                if (Double.isNaN(testvalno)) {
                    valuesno = (float) 0;
                } else {
                    valuesno = (float) testvalno * 100;
                }
                dataVals.add(new BarEntry(i, new float[]{valueseng,valuesno}));
            }


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

    //total word
    public void viewtotalday(){

        int totalall = 0;
        int totaleng = 0;
        int totalanother = 0;

        //eng word
        SQLiteDatabase db2 = eng.getWritableDatabase();
        Cursor res2 = db2.rawQuery("select * from " + TABLE_NAME2 + " where date = " + getFormattedDay +" and month = " +
                                        (getFormattedMonth+1) +" and year = "+(getFormattedYear+1900), null);

        int countlenghtres2 = res2.getCount();
        if(countlenghtres2==0){
            totaleng=0;
        }

        while(res2.moveToNext()){
            totaleng+=res2.getInt(1);

        }

        //not eng word
        SQLiteDatabase db3 = anothereng.getWritableDatabase();
        Cursor res3 = db3.rawQuery("select * from " + TABLE_NAME3 + " where date = " + getFormattedDay +" and month = " +
                                        (getFormattedMonth+1) +" and year = "+(getFormattedYear+1900), null);

        int countlenghtres3 = res3.getCount();
        if(countlenghtres3==0){
            totalanother=0;
        }

        while(res3.moveToNext()){
            totalanother+=res3.getInt(1);

        }

        totalall = totaleng+totalanother;
        totalwordeng = totaleng;
        totalwordday = totaleng+ " / "+totalall;


    }

    //total time
    public void viewtotaltimeday(){

        int totaltime = 0;

        SQLiteDatabase dbtime = time.getWritableDatabase();
        Cursor restime = dbtime.rawQuery("select * from "+ TABLE_NAME5 + " where date = "+getFormattedDay+" and month = "+
                                              (getFormattedMonth+1)+ " and year = "+ (getFormattedYear+1900),null);

        int countrestime = restime.getCount();
        if(countrestime==0){
            totaltime=0;
        }

        while(restime.moveToNext()){
            totaltime += restime.getInt(8);
        }

        totaltimeeng = totaltime;

        int numberOfHours = (totaltime % 86400) / 3600;
        int numberOfMinutes = ((totaltime % 86400) % 3600) / 60;
        int numberOfSeconds = ((totaltime % 86400) % 3600) % 60;

        if(numberOfMinutes<10){
            if(numberOfSeconds<10){
                String text = numberOfHours+" : 0"+numberOfMinutes+" : 0"+numberOfSeconds;
                totaltimeday = text;
            }else{
                String text = numberOfHours+" : 0"+numberOfMinutes+" : "+numberOfSeconds;
                totaltimeday = text;
            }
        }else{
            totaltimeday = numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
        }
    }


    public void wordmin(){

        double total = (double) totalwordeng * 1.0;
        double total2 = (double) totaltimeeng / 60.0;

        if(total2 == 0.0){
            total2 = 1.0;
        }

        wordminday = total/total2;

        System.out.println("wmddd "+wordminday);

    }

    public void continuemaxday(){

        int checking = 0;
        continuemaxday = 0;
        SQLiteDatabase dbcon = continuemax.getWritableDatabase();
        Cursor resdbcon = dbcon.rawQuery("select * from "+TABLE_NAME10+ " where date = "+getFormattedDay+" and month = "+(getFormattedMonth+1)+
                                              " and year = "+ (getFormattedYear+1900),null);

        while (resdbcon.moveToNext()){
            checking = resdbcon.getInt(1);

            if(continuemaxday < checking){
                continuemaxday = checking;

            }
        }
    }


    public void wrongwordday(){

        ArrayList<String> wordfromdb = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
                wordtop[i] = "";
        }

        for (int i = 0; i < 3; i++) {
            wordtrans[i] = "";
        }

        SQLiteDatabase dbwrong = wrongword.getWritableDatabase();
        Cursor resdbwrong = dbwrong.rawQuery("select * from "+TABLE_NAME11+" where date = "+getFormattedDay+" and month = "+(getFormattedMonth+1)+
                                                  " and year = "+ (getFormattedYear+1900) ,null);

        int countresdbwrong = resdbwrong.getCount();

        if(countresdbwrong==0){
            return;
        }

        while (resdbwrong.moveToNext()){
            wordfromdb.add(resdbwrong.getString(1));

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

        System.out.println("Top3");

        if(word.length<3){
            for(int i=0;i<word.length;++i){
                if (!word[i].equals("")) {
                    wordtop[i] = word[i];

                    final Translator t = new Translator(wordtop[i],getContext());
                    t.trans();

                    Handler handler = new Handler();
                    final int finalI = i;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            wordtrans[finalI] = t.trans();

                        }
                    };handler.postDelayed(runnable,4000);

                }
            }

        }else{
            for (int i = 0; i < 3; i++) {
                if (!word[i].equals("")) {
                    wordtop[i] = word[i];

                    final Translator t = new Translator(wordtop[i],getContext());
                    t.trans();

                    Handler handler = new Handler();
                    final int finalI = i;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            wordtrans[finalI] = t.trans();

                        }
                    };handler.postDelayed(runnable,4000);
                }
            }
        }

        ///wordtop[0]="TEST1";wordtop[1]="TEST2";wordtop[2]="TEST3";




        BackupAnyword();
        BackupContinuemax();
        BackupEngword();
        BackupRawdata();
        BackupScheduler();
        BackupSetting();
        BackupTime();
        BackupWrongword();

        getdataAnyword();
        //getdataAnyword();
        getdataContinuemax();
        //getdataEngword();
        getdataViewtotaltimeday();
        getdataViewWrongword();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getdataViewtotalday();
            }
        };handler.postDelayed(runnable,100);





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
    FirebaseDatabase database;
    DatabaseReference ref;
    Data_User data_user;
    int maxid=0;


    public void BackupAnyword(){
        Cursor reDef1 = anothereng.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        String email = firebaseUser.getEmail();
        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupAnyword","No data");
        }
        else {
            while (reDef1.moveToNext()){

                buffer.append("anyword: "+reDef1.getString(0)+"\n");
                buffer.append("word: " + reDef1.getString(1) + "\n");
                buffer.append("DAY: " + reDef1.getString(2) + "\n");
                buffer.append("DATE: " + reDef1.getString(3) + "\n");
                buffer.append("MONTH: " + reDef1.getString(4) + "\n");
                buffer.append("YEAR: " + reDef1.getString(5) + "\n");
                buffer.append("HOUR: " + reDef1.getString(6) + "\n");
                buffer.append("MINUTE: " + reDef1.getString(7) + "\n");
                buffer.append("SECOND: " + reDef1.getString(8) + "\n");
                buffer.append("===============================================\n");

                Call<DataAnyword>calldataAnyword =apiInterface.DataAnyword(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
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
       // Log.d("API_DATA_BackupAnyword",buffer.toString());



    }

    public void BackupContinuemax(){
        Cursor reDef1 = continuemax.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
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
       // Log.d("API_DATA_Continuemax",buffer.toString());

    }

    public void BackupEngword(){
        Cursor reDef1 = eng.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        String email = firebaseUser.getEmail();
    int i=1;
        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupEngword","No data");
        }
        else {
            while (reDef1.moveToNext()){

                    if (i%2==0){

                        buffer1.append("Continuemax: " + reDef1.getString(0) + "\n");
                        buffer1.append("MAXCON: " + reDef1.getString(1) + "\n");
                        buffer1.append("DAY: " + reDef1.getString(2) + "\n");
                        buffer1.append("DATE: " + reDef1.getString(3) + "\n");
                        buffer1.append("MONTH: " + reDef1.getString(4) + "\n");
                        buffer1.append("YEAR: " + reDef1.getString(5) + "\n");
                        buffer1.append("HOUR: " + reDef1.getString(6) + "\n");
                        buffer1.append("MINUTE: " + reDef1.getString(7) + "\n");
                        buffer1.append("SECOND: " + reDef1.getString(8) + "\n");
                        buffer1.append("===============================================\n");
                    }
                    else {
                        buffer.append("Continuemax: " + reDef1.getString(0) + "\n");
                        buffer.append("MAXCON: " + reDef1.getString(1) + "\n");
                        buffer.append("DAY: " + reDef1.getString(2) + "\n");
                        buffer.append("DATE: " + reDef1.getString(3) + "\n");
                        buffer.append("MONTH: " + reDef1.getString(4) + "\n");
                        buffer.append("YEAR: " + reDef1.getString(5) + "\n");
                        buffer.append("HOUR: " + reDef1.getString(6) + "\n");
                        buffer.append("MINUTE: " + reDef1.getString(7) + "\n");
                        buffer.append("SECOND: " + reDef1.getString(8) + "\n");
                        buffer.append("===============================================\n");
                    }
                    i++;


                Call<DataEngword>calldataEngword =apiInterface.DataEngword(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
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
       // Log.d("API_DATA_BackupEngword",buffer.toString());

       // Log.d("API_DATA_BackupEngword",buffer1.toString());

    }

    public void BackupRawdata(){

        Cursor reDef1 = rawdata.getAlldata();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        String email = firebaseUser.getEmail();
        int i=1;
        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupEngword","No data");
        }
        else {
            while (reDef1.moveToNext()){
                if (i<25) {
                    buffer.append("rawdata: " + reDef1.getString(0) + "\n");
                    buffer.append("totalword: " + reDef1.getString(1) + "\n");
                    buffer.append("DAY: " + reDef1.getString(2) + "\n");
                    buffer.append("DATE: " + reDef1.getString(3) + "\n");
                    buffer.append("MONTH: " + reDef1.getString(4) + "\n");
                    buffer.append("YEAR: " + reDef1.getString(5) + "\n");
                    buffer.append("HOUR: " + reDef1.getString(6) + "\n");
                    buffer.append("MINUTE: " + reDef1.getString(7) + "\n");
                    buffer.append("SECOND: " + reDef1.getString(8) + "\n");
                    buffer.append("===============================================\n");
                }
                else {
                    buffer1.append("rawdata: " + reDef1.getString(0) + "\n");
                    buffer1.append("totalword: " + reDef1.getString(1) + "\n");
                    buffer1.append("DAY: " + reDef1.getString(2) + "\n");
                    buffer1.append("DATE: " + reDef1.getString(3) + "\n");
                    buffer1.append("MONTH: " + reDef1.getString(4) + "\n");
                    buffer1.append("YEAR: " + reDef1.getString(5) + "\n");
                    buffer1.append("HOUR: " + reDef1.getString(6) + "\n");
                    buffer1.append("MINUTE: " + reDef1.getString(7) + "\n");
                    buffer1.append("SECOND: " + reDef1.getString(8) + "\n");
                    buffer1.append("===============================================\n");
                }
                i++;
                Log.d("API_DATA_BackupRawdata","rawdata: " + reDef1.getString(0) + "\n"+
                        "totalword: " + reDef1.getString(1) + "\n"+
                        "DAY: " + reDef1.getString(2) + "\n"+
                        "MONTH: " + reDef1.getString(4) + "\n"+
                        "YEAR: " + reDef1.getString(5) + "\n"+
                        "HOUR: " + reDef1.getString(6) + "\n"+
                        "MINUTE: " + reDef1.getString(7) + "\n"+
                        "SECOND: " + reDef1.getString(8) + "\n"+
                        "===============================================\n");

                Call<DataRawdata>calldatarawdata =apiInterface.DataRawdata(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
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
        //Log.d("API_DATA_BackupRawdata",buffer.toString());
        //Log.d("API_DATA_BackupRawdata",buffer1.toString());

    }

    public void BackupScheduler(){

        Cursor reDef1 = scheduler.getAlldata();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        String email = firebaseUser.getEmail();

        if (reDef1.getCount()==0){
            Log.d("API_DATA_Scheduler","No data");
        }
        else {
            while (reDef1.moveToNext()){

                    buffer.append("scheduler: " + reDef1.getString(0) + "\n");
                    buffer.append("DAY: " + reDef1.getString(1) + "\n");
                    buffer.append("DATE: " + reDef1.getString(2) + "\n");
                    buffer.append("MONTH: " + reDef1.getString(3) + "\n");
                    buffer.append("YEAR: " + reDef1.getString(4) + "\n");
                    buffer.append("starthour: " + reDef1.getString(5) + "\n");
                    buffer.append("startminute: " + reDef1.getString(6) + "\n");
                    buffer.append("stophour: " + reDef1.getString(7) + "\n");
                    buffer.append("stopminute: " + reDef1.getString(8) + "\n");
                    buffer.append("status: " + reDef1.getString(9) + "\n");
                    buffer.append("===============================================\n");




                Call<DataScheduler>calldataScheduler =apiInterface.DataScheduler(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
                        reDef1.getString(4),reDef1.getString(5),reDef1.getString(6),reDef1.getString(7),reDef1.getString(8),reDef1.getString(9));

                calldataScheduler.enqueue(new Callback<DataScheduler>() {
                    @Override
                    public void onResponse(Call<DataScheduler> call, Response<DataScheduler> response) {
                        DataScheduler dataScheduler = response.body();
                        if (dataScheduler!=null){
                            Log.d("API_DATA_Scheduler","SaveSuccessful");
                            Log.d("API_DATA_Scheduler","MS:"+dataScheduler.getMessages());
                        }
                        else {

                            Log.d("API_DATA_Scheduler","MS:"+dataScheduler.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataScheduler> call, Throwable t) {
                        Log.d("API_DATA_Scheduler","Savefail T "+t);

                    }
                });
            }

        }
     //   Log.d("API_DATA_Scheduler",buffer.toString());


    }

    public void BackupSetting(){

        Cursor reDef1 = setting.getAlldata();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        StringBuffer buffer1 = new StringBuffer();
        String email = firebaseUser.getEmail();

        if (reDef1.getCount()==0){
            Log.d("API_DATA_Scheduler","No data");
        }
        else {
            while (reDef1.moveToNext()){

                buffer.append("setting: " + reDef1.getString(0) + "\n");
                buffer.append("nativelang: " + reDef1.getString(1) + "\n");
                buffer.append("percentagenone: " + reDef1.getString(2) + "\n");
                buffer.append("chaday: " + reDef1.getString(3) + "\n");
                buffer.append("===============================================\n");




                Call<DataSetting>calldataSetting =apiInterface.DataSetting(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3));

                calldataSetting.enqueue(new Callback<DataSetting>() {
                    @Override
                    public void onResponse(Call<DataSetting> call, Response<DataSetting> response) {
                        DataSetting dataSetting = response.body();
                        if (dataSetting!=null){
                            Log.d("API_DATA_BackupSetting","SaveSuccessful");
                            Log.d("API_DATA_BackupSetting","MS:"+dataSetting.getMessages());
                        }
                        else {

                            Log.d("API_DATA_BackupSetting","MS:"+dataSetting.getMessages());
                        }
                    }

                    @Override
                    public void onFailure(Call<DataSetting> call, Throwable t) {
                        Log.d("API_DATA_BackupSetting","Savefail T "+t);

                    }
                });
            }

        }
      //  Log.d("API_DATA_BackupSetting",buffer.toString());


    }

    public void BackupTime(){
        Cursor reDef1 = time.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        String email = firebaseUser.getEmail();
        if (reDef1.getCount()==0){
            Log.d("API_DATA_BackupTime","No data");
        }
        else {
            while (reDef1.moveToNext()){

                buffer.append("time: "+reDef1.getString(0)+"\n");
                buffer.append("DAY: " + reDef1.getString(1) + "\n");
                buffer.append("DATE: " + reDef1.getString(2) + "\n");
                buffer.append("MONTH: " + reDef1.getString(3) + "\n");
                buffer.append("YEAR: " + reDef1.getString(4) + "\n");
                buffer.append("HOUR: " + reDef1.getString(5) + "\n");
                buffer.append("MINUTE: " + reDef1.getString(6) + "\n");
                buffer.append("SECOND: " + reDef1.getString(7) + "\n");
                buffer.append("totaltime: " + reDef1.getString(8) + "\n");
                buffer.append("===============================================\n");

                Call<DataTime>calldataTime =apiInterface.DataTime(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
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
       // Log.d("API_DATA_BackupTime",buffer.toString());

    }

    public void BackupWrongword(){
        Cursor reDef1 = wrongword.getAlldata();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        getnamemonth(getFormattedMonth);
        StringBuffer buffer = new StringBuffer();
        String email = firebaseUser.getEmail();
        if (reDef1.getCount()==0){
            Log.d("API_DATA_Wrongword","No data");
        }
        else {
            while (reDef1.moveToNext()){

                buffer.append("Wrongword: "+reDef1.getString(0)+"\n");
                buffer.append("word: " + reDef1.getString(1) + "\n");
                buffer.append("DAY: " + reDef1.getString(2) + "\n");
                buffer.append("DATE: " + reDef1.getString(3) + "\n");
                buffer.append("MONTH: " + reDef1.getString(4) + "\n");
                buffer.append("YEAR: " + reDef1.getString(5) + "\n");
                buffer.append("HOUR: " + reDef1.getString(6) + "\n");
                buffer.append("MINUTE: " + reDef1.getString(7) + "\n");
                buffer.append("SECOND: " + reDef1.getString(8) + "\n");
                buffer.append("===============================================\n");

                Call<DataWrongword>calldataWrongword =apiInterface.DataWrongword(email,reDef1.getString(0),reDef1.getString(1),reDef1.getString(2),reDef1.getString(3),
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
        // Log.d("API_DATA_Wrongword",buffer.toString());

    }

    public void getdataAnyword(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
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
        Call<List<DataAnyword>>listcallgetdata = apiInterface.getDataAnyword(email,S_day,S_month,S_year);
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
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();



        Call<List<DataContinuemax>>listcallgetdata = apiInterface.getDataContinuemax(email,S_day,S_month,S_year);
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
                        //continuemaxday =continuemaxday1;
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
                    Log.e("TEST_GET_LISTDATA",listdata.get(1).getEmail());
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
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();



        Call<List<DataTime>>listcallgetdata = apiInterface.getDataTime(email,S_day,S_month,S_year);
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
                            //totaltimeday =text;
                            Log.e("TEST_SHOW_DAY","totaltimeday: "+text);
                        }else{
                            String text = numberOfHours+" : 0"+numberOfMinutes+" : "+numberOfSeconds;
                            //totaltimeday =text;
                            Log.e("TEST_SHOW_DAY","totaltimeday: "+text);
                        }
                    }else{
                        String text = numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                        //totaltimeday =text;
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
        String S_day= Integer.valueOf(getFormattedDay).toString();
        String S_month =Integer.valueOf(getFormattedMonth+1).toString();
        String S_year =Integer.valueOf(getFormattedYear+1900).toString();
        String email = firebaseUser.getEmail();

        Call<List<DataWrongword>>listcallgetdata = apiInterface.getDataWrongword(email,S_day,S_month,S_year);
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
                                    //wordtop[i] = word[i];
                                    wordtop1[i] = word[i];

                                    //final Translator t = new Translator(wordtop[i],getContext());
                                    final Translator t = new Translator(wordtop1[i],getContext());
                                    t.trans();

                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            //wordtrans[finalI] = t.trans();
                                            wordtrans1[finalI] = t.trans();

                                        }
                                    };handler.postDelayed(runnable,4000);

                                }
                            }

                        }else{
                            for (int i = 0; i < 3; i++) {
                                if (!word[i].equals("")) {
                                    //wordtop[i] = word[i];
                                    wordtop1[i] = word[i];

                                   // final Translator t = new Translator(wordtop[i],getContext());
                                    final Translator t = new Translator(wordtop1[i],getContext());
                                    t.trans();

                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                           // wordtrans[finalI] = t.trans();
                                            wordtrans1[finalI] = t.trans();
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
            Call<List<DataEngword>> listcallgetdataEngword = apiInterface.getDataEngword(email, S_day, S_month, S_year);
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
                        /*
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

                         */


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
                            /*
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

                             */

                        }

                        //totalwordday =TTWD1;
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
        //wordminday =wordminday1;
        Log.e("TEST_SHOW_DAY","wordminday: "+wordminday1);

    }




}