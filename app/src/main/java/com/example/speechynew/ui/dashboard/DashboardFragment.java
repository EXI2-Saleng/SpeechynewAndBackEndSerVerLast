package com.example.speechynew.ui.dashboard;

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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
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
import com.example.speechynew.connectDB.DataTime;
import com.example.speechynew.connectDB.DataWrongword;
import com.example.speechynew.connectDB.Engword;
import com.example.speechynew.connectDB.Timeprocess;
import com.example.speechynew.connectDB.Word;
import com.example.speechynew.connectDB.Wrongword;
import com.example.speechynew.ui.notifications.NotificationsFragment;
import com.example.speechynew.ui.notifications.NotificationsReport;
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

import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.speechynew.connectDB.Continuemaxinterface.TABLE_NAME10;
import static com.example.speechynew.connectDB.Engwordinterface.TABLE_NAME2;
import static com.example.speechynew.connectDB.Timeprocessinterface.TABLE_NAME5;
import static com.example.speechynew.connectDB.Wordinterface.TABLE_NAME3;
import static com.example.speechynew.connectDB.Wrongwordinterface.TABLE_NAME11;
import static java.lang.StrictMath.abs;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    BarChart mChart;
    TextView week;
    ImageButton graphminus;
    ImageButton graphplus;
    TextView wait5week;

    Button changetype;
    boolean resulttype;

    Engword eng;
    Word anothereng;
    Timeprocess time;
    Continuemax continuemax;
    Wrongword wrongword;

    Calendar c ;
    SimpleDateFormat df;
    String formattedDate;

    Calendar c2 ;
    SimpleDateFormat df2;
    String formattedDate2;

    int getFormattedDay;
    int getFormattedMonth;
    int getFormattedYear;

    Button nextpage;

    String dateweek;
    String showtotalword;
    String showtotaltime;

    int totalwordeng;
    int totaltimeeng;
    double wordminweek;

    int continuemaxweek = 0;
    String[] wordtop = new String[3];
    String[] wordtrans = new String[3];

    private FirebaseAuth firebaseAuth;
    ApiInterface apiInterface;
    int indextotalmeeng =0;
    int totaltime1 = 0;
    int indextotalengword = 0;
    int indextotalanyword = 0;
    int indextotalall = 0;
    int indexcontinuemaxweek1 =0;
    int[] arraytesteng1 = new int[24];
    int[] arraytestnone1 = new int[24];

    String indextimeingraph[] = new String[7];
    ArrayList<String> WordWrong = new ArrayList<>();
    int round =0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.viewweek);

        week = root.findViewById(R.id.showweek);
        mChart = root.findViewById(R.id.mp_barchartweek);
        graphminus = root.findViewById(R.id.graphminus);
        graphplus = root.findViewById(R.id.graphplus);
        changetype = root.findViewById(R.id.changetype);
        nextpage = root.findViewById(R.id.nextpage);
        wait5week = root.findViewById(R.id.wait5week);

        eng = new Engword(root.getContext());
        anothereng = new Word(root.getContext());
        time = new Timeprocess(root.getContext());
        continuemax = new Continuemax(root.getContext());
        wrongword = new Wrongword(root.getContext());

        resulttype = false;

        c = Calendar.getInstance();
        df = new SimpleDateFormat("d-MM-yyyy");
        formattedDate = df.format(c.getTime());

        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();

        c2 = Calendar.getInstance();
        c2.add(Calendar.DATE,-6);
        df2 = new SimpleDateFormat("d-MM-yyyy");
        formattedDate2 = df2.format(c2.getTime());

        week.setText(formattedDate2 + " - "+ formattedDate);
        dateweek = formattedDate2 + " - "+ formattedDate;

        mChart.getDescription().setEnabled(false);
        mChart.setFitBars(true);

        wait5week.setText(" Please wait 5 second");

        nextpageonclick();
        viewallweek();
        wordmin();
        getdataViewtotalAnyword();
        getdataViewtotaltimeweek();
        getdataViewcontinuemax();
        getdataViewwrongword();
        getdataViewtotalEngword();

        //minus week -
        graphminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, -7);
                formattedDate = df.format(c.getTime());
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = c.getTime().getMonth();
                getFormattedYear = c.getTime().getYear();

                c2.add(Calendar.DATE,-7);
                formattedDate2 = df2.format(c2.getTime());

                week.setText(formattedDate2 + " - "+ formattedDate);
                dateweek = formattedDate2 + " - "+ formattedDate;

                continuemaxweek = 0;

                wait5week.setText(" Please wait 5 second");

                for (int i = 0; i < 3; i++) {
                    wordtop[i] = "";
                }

                for (int i = 0; i < 3; i++) {
                    wordtrans[i] = "";
                }

                nextpageonclick();
                viewallweek();
                wordmin();
                getdataViewtotalAnyword();
                getdataViewtotaltimeweek();
                getdataViewcontinuemax();
                getdataViewwrongword();
                getdataViewtotalEngword();

            }
        });

        //plus week +
        graphplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.DATE, +7);
                formattedDate = df.format(c.getTime());
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = c.getTime().getMonth();
                getFormattedYear = c.getTime().getYear();

                c2.add(Calendar.DATE,+7);
                formattedDate2 = df2.format(c2.getTime());


                if(c.after(Calendar.getInstance())){

                    c.add(Calendar.DATE, -7);
                    formattedDate = df.format(c.getTime());
                    getFormattedDay = c.getTime().getDate();
                    getFormattedMonth = c.getTime().getMonth();
                    getFormattedYear = c.getTime().getYear();

                    c2.add(Calendar.DATE,-7);
                    formattedDate2 = df2.format(c2.getTime());

                }else{

                    week.setText(formattedDate2 + " - "+ formattedDate);
                    dateweek = formattedDate2 + " - "+ formattedDate;

                    continuemaxweek = 0;

                    for (int i = 0; i < 3; i++) {
                        wordtop[i] = "";
                    }

                    for (int i = 0; i < 3; i++) {
                        wordtrans[i] = "";
                    }

                    wait5week.setText(" Please wait 5 second");

                    nextpageonclick();
                    viewallweek();
                    wordmin();
                    getdataViewtotalAnyword();
                    getdataViewtotaltimeweek();
                    getdataViewcontinuemax();
                    getdataViewwrongword();
                    getdataViewtotalEngword();
                }

            }
        });


        changetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change from word count to Percentage
                if(resulttype==false){
                    resulttype=true;
                    getdataViewtotalAnyword();
                    viewallweek();
                    getdataViewtotalEngword();
                    changetype.setText("Word count");

                    //change from Percentage to word count
                }else if(resulttype==true){
                    resulttype=false;
                    getdataViewtotalAnyword();
                    viewallweek();
                    getdataViewtotalEngword();
                    changetype.setText("Percentage");
                }

            }
        });

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //   textView.setText(s);
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

                wait5week.setText("");

                nextpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent report = new Intent(getContext(),DashboardReport.class);
                        report.putExtra("test",dateweek);
                        report.putExtra("totalwordweek",showtotalword);
                        report.putExtra("totaltimeweek",showtotaltime);
                        report.putExtra("wordminweek",wordminweek);
                        report.putExtra("continuemaxweek",continuemaxweek);
                        report.putExtra("wordtop",wordtop);
                        report.putExtra("wordtrans",wordtrans);
                        startActivity(report);

                    }
                });
            }

        };handler.postDelayed(runnable,4000);

    }


    public void viewallweek(){

        int counteng=0;
        int countnone =0;
        int totaltime=0;
        int checkingcon = 0;
        ArrayList<String> wordfromdb;

        if(resulttype==false){

            ArrayList<BarEntry> dataVals = new ArrayList<>();
            wordfromdb = new ArrayList<>();

            String timeingraph[] = new String[7];

            int test = 7;
            int[] arraytesteng = new int[test];
            int[] arraytestnone = new int[test];


            for(int i=0;i<test;++i) {

                //for time
                SQLiteDatabase dbtime = time.getWritableDatabase();
                Cursor restime = dbtime.rawQuery("select * from " + TABLE_NAME5 +" where date = "+ getFormattedDay + " and month = " + (getFormattedMonth+1) + " and year = " + (getFormattedYear+1900),null);

                while(restime.moveToNext()){
                    totaltime += restime.getInt(8);
                }


                //for engword
                SQLiteDatabase db2 = eng.getWritableDatabase();
                Cursor res = db2.rawQuery("select * from " + TABLE_NAME2 +" where date = "+ getFormattedDay + " and month = " + (getFormattedMonth+1) + " and year = " + (getFormattedYear+1900), null);

                if (res.getCount() == 0) {
                    arraytesteng[i]=0;
                }

                while (res.moveToNext()) {
                    counteng+=res.getInt(1);
                    arraytesteng[i]+=res.getInt(1);
                }

                //for word
                SQLiteDatabase db3 = anothereng.getWritableDatabase();
                Cursor res3 = db3.rawQuery("select * from " + TABLE_NAME3 +" where date = "+ getFormattedDay + " and month = " + (getFormattedMonth+1) + " and year = " + (getFormattedYear+1900), null);

                if (res3.getCount() == 0) {
                    arraytestnone[i]=0;
                }

                while (res3.moveToNext()) {
                    countnone+=res3.getInt(1);
                    arraytestnone[i]+=res3.getInt(1);
                }

                //for continue
                SQLiteDatabase dbcon = continuemax.getWritableDatabase();
                Cursor resdbcon = dbcon.rawQuery("select * from "+TABLE_NAME10+ " where date = "+getFormattedDay+" and month = "+(getFormattedMonth+1)+
                        " and year = "+ (getFormattedYear+1900),null);

                while (resdbcon.moveToNext()){
                    checkingcon = resdbcon.getInt(1);

                    if(continuemaxweek < checkingcon){
                        continuemaxweek = checkingcon;
                    }
                }

                //for wrongword
                SQLiteDatabase dbwrong = wrongword.getWritableDatabase();
                Cursor resdbwrong = dbwrong.rawQuery("select * from "+TABLE_NAME11+" where date = "+getFormattedDay+" and month = "+(getFormattedMonth+1)+
                        " and year = "+ (getFormattedYear+1900) ,null);

                int countresdbwrong = resdbwrong.getCount();

                if(countresdbwrong==0){
                    //return;
                }

                while (resdbwrong.moveToNext()){
                    wordfromdb.add(resdbwrong.getString(1));
                }

                timeingraph[abs(i-6)] = getFormattedDay+"/"+(getFormattedMonth+1);

                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());

                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = c.getTime().getMonth();
                getFormattedYear = c.getTime().getYear();

            }

            //top3
            if(wordfromdb.size()==0){
                //nothing
            }else{
                wrongwordtop3(wordfromdb);
            }

            for(int i=0;i<test;++i){
                int valueseng = arraytesteng[abs(i-6)];
                int valuesno = arraytestnone[abs(i-6)];

                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));
            }


            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Number of English words", "Number of non-English words"});
            BarData data = new BarData(barDataSet);

            data.setValueFormatter(new DashboardFragment.MyValueFormatter());
            mChart.setData(data);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
            mChart.getLegend().setFormToTextSpace(3);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(timeingraph));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(7);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMaximum(7);
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();



            totaltimeeng = totaltime;

            int numberOfDays = totaltime / 86400;
            int numberOfHours = (totaltime % 86400) / 3600;
            int numberOfMinutes = ((totaltime % 86400) % 3600) / 60;
            int numberOfSeconds = ((totaltime % 86400) % 3600) % 60;


            if(numberOfHours<10) {
                if (numberOfMinutes < 10) {
                    if (numberOfSeconds < 10) {
                        String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : 0" + numberOfSeconds;
                        showtotaltime = text;
                    } else {
                        String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : " + numberOfSeconds;
                        showtotaltime = text;
                    }
                }else{
                    String text = numberOfDays + " Day  \n0" + numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                    showtotaltime = text;
                }
            }else{
                showtotaltime = numberOfDays+" Day  \n"+numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
            }


        }else if(resulttype==true){ //view percentage

            wordfromdb = new ArrayList<>();
            ArrayList<BarEntry> dataVals = new ArrayList<>();

            String timeingraph[] = new String[7];

            int test = 7;
            double[] arraytesteng = new double[test];
            double[] arraytestnone = new double[test];
            double[] arrayall = new double[test];


            for(int i=0;i<test;++i) {

                //for time
                SQLiteDatabase dbtime = time.getWritableDatabase();
                Cursor restime = dbtime.rawQuery("select * from " + TABLE_NAME5 +" where date = "+ getFormattedDay + " and month = " + (getFormattedMonth+1) + " and year = " + (getFormattedYear+1900),null);

                while(restime.moveToNext()){
                    totaltime += restime.getInt(8);
                }

                //for engword
                SQLiteDatabase db2 = eng.getWritableDatabase();
                Cursor res = db2.rawQuery("select * from " + TABLE_NAME2 +" where date = "+ getFormattedDay + " and month = " + (getFormattedMonth+1) + " and year = " + (getFormattedYear+1900), null);

                if (res.getCount() == 0) {
                    arraytesteng[i]=0;
                }

                while (res.moveToNext()) {
                    counteng+=res.getInt(1);
                    arraytesteng[i]+=res.getInt(1);
                }

                //for notengword
                SQLiteDatabase db3 = anothereng.getWritableDatabase();
                Cursor res3 = db3.rawQuery("select * from " + TABLE_NAME3 +" where date = "+ getFormattedDay + " and month = " + (getFormattedMonth+1) + " and year = " + (getFormattedYear+1900), null);

                if (res3.getCount() == 0) {
                    //show message
                    arraytestnone[i]=0;

                }

                while (res3.moveToNext()) {
                    countnone+=res3.getInt(1);
                    arraytestnone[i]+=res3.getInt(1);
                }


                //for continue
                SQLiteDatabase dbcon = continuemax.getWritableDatabase();
                Cursor resdbcon = dbcon.rawQuery("select * from "+TABLE_NAME10+ " where date = "+getFormattedDay+" and month = "+(getFormattedMonth+1)+
                        " and year = "+ (getFormattedYear+1900),null);

                while (resdbcon.moveToNext()){
                    checkingcon = resdbcon.getInt(1);

                    if(continuemaxweek < checkingcon){
                        continuemaxweek = checkingcon;
                    }
                }

                //for wrongword
                SQLiteDatabase dbwrong = wrongword.getWritableDatabase();
                Cursor resdbwrong = dbwrong.rawQuery("select * from "+TABLE_NAME11+" where date = "+getFormattedDay+" and month = "+(getFormattedMonth+1)+
                        " and year = "+ (getFormattedYear+1900) ,null);

                int countresdbwrong = resdbwrong.getCount();

                if(countresdbwrong==0){
                    //return;
                }

                while (resdbwrong.moveToNext()){
                    wordfromdb.add(resdbwrong.getString(1));
                }

                timeingraph[abs(i-6)] = getFormattedDay+"/"+(getFormattedMonth+1);

                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = c.getTime().getMonth();
                getFormattedYear = c.getTime().getYear();

            }

            //top3
            if(wordfromdb.size()==0){
                //nothing
            }else{
                wrongwordtop3(wordfromdb);
            }

            for(int i = 0; i < test; ++i){
                arrayall[i] = arraytesteng[i];
            }

            for(int i = 0; i < test; ++i){
                arrayall[i] +=arraytestnone[i];
            }

            for (int i = 0; i < test; ++i) {
                float valueseng;
                double testvaleng = arraytesteng[abs(i-6)] / arrayall[abs(i-6)];

                if (Double.isNaN(testvaleng)) {
                    valueseng = (float) 0;
                } else {
                    valueseng = (float) testvaleng * 100;
                }

                float valuesno;
                double testvalno = arraytestnone[abs(i-6)] / arrayall[abs(i-6)];

                if (Double.isNaN(testvalno)) {
                    valuesno = (float) 0;
                } else {
                    valuesno = (float) testvalno * 100;
                }

                dataVals.add(new BarEntry(i, new float[]{valueseng,valuesno}));

            }



            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Percentage of English words", "Percentage of non-English words"});
            BarData data = new BarData(barDataSet);

            data.setValueFormatter(new DashboardFragment.MyValueFormatter());
            mChart.setData(data);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
            mChart.getLegend().setFormToTextSpace(3);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(timeingraph));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(7);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMaximum(7);
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();



            totaltimeeng = totaltime;

            int numberOfDays = totaltime / 86400;
            int numberOfHours = (totaltime % 86400) / 3600;
            int numberOfMinutes = ((totaltime % 86400) % 3600) / 60;
            int numberOfSeconds = ((totaltime % 86400) % 3600) % 60;


            if(numberOfHours<10) {
                if (numberOfMinutes < 10) {
                    if (numberOfSeconds < 10) {
                        String text = numberOfDays + " Day \n0" + numberOfHours + " : 0" + numberOfMinutes + " : 0" + numberOfSeconds;
                        showtotaltime = text;
                    } else {
                        String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : " + numberOfSeconds;
                        showtotaltime = text;
                    }
                }else{
                    String text = numberOfDays + " Day  \n0" + numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                    showtotaltime = text;
                }
            }else{
                showtotaltime = numberOfDays+" Day  \n"+numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
            }

        }

        c.add(Calendar.DATE, +7);
        formattedDate = df.format(c.getTime());
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();

        totalwordeng = counteng;

        showtotalword = counteng+ " / "+(counteng+countnone);

    }


    void wordmin(){

        double total = (double) totalwordeng * 1.0;
        double total2 = (double) totaltimeeng/60.0;

        if(total2==0){
            total2 = 1;
        }

        wordminweek = total/total2;

    }




    public void wrongwordtop3(ArrayList<String> wordfromdb){


        for (int i = 0; i < 3; i++) {
            wordtop[i] = "";
        }

        for (int i = 0; i < 3; i++) {
            wordtrans[i] = "";
        }

        //wordcount
        int N = wordfromdb.size();
        String word[] = new String[N];
        int count[] = new int[N];

        for (int i = 0; i < word.length; i++) {
            word[i] = "";
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

    public void getdataViewtotaltimeweek(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
            totaltime1=0;

        for (int index = 0; index < 7; index++) {
            String S_day = Integer.valueOf(getFormattedDay).toString();
            String S_month = Integer.valueOf(getFormattedMonth + 1).toString();
            String S_year = Integer.valueOf(getFormattedYear + 1900).toString();


            Call<List<DataTime>> listcallgetdata = apiInterface.getDataTime(email, S_day, S_month, S_year);
            int finalIndex = index;
            listcallgetdata.enqueue(new Callback<List<DataTime>>() {
                @Override
                public void onResponse(Call<List<DataTime>> call, Response<List<DataTime>> response) {
                    if (response.isSuccessful()) {
                        List<DataTime> listdata = response.body();


                        if (listdata.size() == 0) {
                            totaltime1 += 0;
                        } else {

                            for (int i = 0; i < listdata.size(); i++) {
                                totaltime1 += Integer.parseInt(listdata.get(i).getTotaltime());

                            }


                            //Log.e("TEST_SHOW_WEEK_TIME", "totaltime1 :" + totaltime1);




                            //indextotalmeeng = totaltime1;

                        }

                    } else {
                        Log.d("TEST_GET_LISTDATA", "Fail:" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<DataTime>> call, Throwable t) {
                    Log.d("TEST_GET_LISTDATA", t + "");
                }
            });
            c.add(Calendar.DATE, -1);
            formattedDate = df.format(c.getTime());
            getFormattedDay = c.getTime().getDate();
            getFormattedMonth = c.getTime().getMonth();
            getFormattedYear = c.getTime().getYear();


        }

        c.add(Calendar.DATE, +7);
        formattedDate = df.format(c.getTime());
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Showtotaltime(totaltime1);
                indextotalmeeng = totaltime1;
            }
        };handler.postDelayed(runnable,200);



    }
    public void Showtotaltime(int TTtime){
         totaltime1=TTtime;
        int numberOfDays = totaltime1 / 86400;
        int numberOfHours = (totaltime1 % 86400) / 3600;
        int numberOfMinutes = ((totaltime1 % 86400) % 3600) / 60;
        int numberOfSeconds = ((totaltime1 % 86400) % 3600) % 60;
        if(numberOfHours<10) {
            if (numberOfMinutes < 10) {
                if (numberOfSeconds < 10) {
                    String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : 0" + numberOfSeconds;
                    //showtotaltime = text;
                    Log.d("TEST_SHOW_WEEK_TIME","Showtotaltime :"+text);
                } else {
                    String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : " + numberOfSeconds;
                    //showtotaltime = text;
                    Log.d("TEST_SHOW_WEEK_TIME","Showtotaltime :"+text);
                }
            }else{
                String text = numberOfDays + " Day  \n0" + numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                //showtotaltime = text;
                Log.d("TEST_SHOW_WEEK_TIME","Showtotaltime :"+text);
            }
        }else{
            String text = numberOfDays+" Day  \n"+numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
            //showtotaltime = text;
            Log.d("TEST_SHOW_WEEK_TIME","Showtotaltime :"+text);
        }

    }

    public void getdataViewtotalAnyword(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();

        indextotalanyword = 0 ;
        int[] totalanother = {0};
        int[] arraytestnone2 = new int[24];
        Log.d("TEST_SHOW_WEEK",dateweek);
        Log.d("TEST_SHOW_WEEK","Email :"+email);
        for (int index = 0; index < 7; index++) {
            String S_day = Integer.valueOf(getFormattedDay).toString();
            String S_month = Integer.valueOf(getFormattedMonth + 1).toString();
            String S_year = Integer.valueOf(getFormattedYear + 1900).toString();


            ArrayList<BarEntry> dataVals = new ArrayList<>();
            Call<List<DataAnyword>>listcallgetdata = apiInterface.getDataAnyword(email,S_day,S_month,S_year);
            int finalIndex = index;
            listcallgetdata.enqueue(new Callback<List<DataAnyword>>() {
                @Override
                public void onResponse(Call<List<DataAnyword>> call, Response<List<DataAnyword>> response) {
                    if (response.isSuccessful()) {
                        List<DataAnyword> listdata = response.body();
                        if (listdata.size() == 0) {
                            indextotalanyword += 0;
                            arraytestnone2[finalIndex] = 0;
                        } else {
                            for (int i = 0; i < listdata.size(); i++) {
                                indextotalanyword += Integer.parseInt(listdata.get(i).getWord());

                                arraytestnone2[finalIndex]+=Integer.parseInt(listdata.get(i).getWord());

                            }

                            }  StringBuffer buffer = new StringBuffer();
                            arraytestnone1=arraytestnone2;
                            for(int i = 0;i<7 ;i++){
                            buffer.append(":"+arraytestnone2[i]+" ");
                          //  Log.d("TEST_SHOW_WEEK_ARRAY_AW","ARRAY_ANYWORD :"+buffer.toString());

                           // Log.d("TEST_SHOW_WEEK_TTW", "indextotalanyword :" + indextotalanyword);

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
            c.add(Calendar.DATE, -1);
            formattedDate = df.format(c.getTime());
            getFormattedDay = c.getTime().getDate();
            getFormattedMonth = c.getTime().getMonth();
            getFormattedYear = c.getTime().getYear();


        }

        c.add(Calendar.DATE, +7);
        formattedDate = df.format(c.getTime());
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();



    }
    public void getdataViewtotalEngword(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();

        indextotalengword = 0;
        int[] totalall = {0};
        int[] totaleng = {0};
        int[] totalanother = {0};
        int[] arraytesteng2 = new int[24];
        double[] arrayall1 = new double[24];
        String timeingraph[] = new String[7];
        for (int index = 0; index < 7; index++) {
            String S_day = Integer.valueOf(getFormattedDay).toString();
            String S_month = Integer.valueOf(getFormattedMonth + 1).toString();
            String S_year = Integer.valueOf(getFormattedYear + 1900).toString();


            ArrayList<BarEntry> dataVals = new ArrayList<>();
            Call<List<DataEngword>> listcallgetdataEngword = apiInterface.getDataEngword(email, S_day, S_month, S_year);
            int finalIndex = index;
            listcallgetdataEngword.enqueue(new Callback<List<DataEngword>>() {
                @Override
                public void onResponse(Call<List<DataEngword>> call, Response<List<DataEngword>> response) {

                    if (response.isSuccessful()) {
                        List<DataEngword> listdata = response.body();
                        if (listdata.size() == 0) {
                            indextotalengword += 0;
                            arraytesteng2[finalIndex]=0;
                        }
                        else {

                            for (int i = 0; i < listdata.size(); i++) {
                                indextotalengword += Integer.parseInt(listdata.get(i).getWord());
                                 arraytesteng2[finalIndex]+=Integer.parseInt(listdata.get(i).getWord());
                            }

                        }

                        arraytesteng1=arraytesteng2;
                        totalall[0] = totaleng[0] + totalanother[0];
                        int TTWE = totaleng[0];
                        String TTWD = totaleng[0] + " / " + totalall[0];

                        indextotalall = indextotalanyword + indextotalengword;
                        String TTWD1 = indextotalengword + " / " + indextotalall;

                        //showtotalword =TTWD1;
                        Log.e("TEST_SHOW_WEEK_TTW", "indextotalengword :" + indextotalengword);
                        Log.e("TEST_SHOW_WEEK_TTW", "indextotaleAnyword :" + indextotalanyword);
                        Log.e("TEST_SHOW_WEEK_TTW", "showtotalwordweek :" + TTWD1);


                    } else {
                        Log.d("TEST_GET_LISTDATA", "Fail:" + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<DataEngword>> call, Throwable t) {
                    Log.d("TEST_GET_LISTDATA", t + "");
                }
            });
            indextimeingraph[abs(finalIndex-6)] = getFormattedDay+"/"+(getFormattedMonth+1);
            c.add(Calendar.DATE, -1);
            formattedDate = df.format(c.getTime());
            getFormattedDay = c.getTime().getDate();
            getFormattedMonth = c.getTime().getMonth();
            getFormattedYear = c.getTime().getYear();


        }

        c.add(Calendar.DATE, +7);
        formattedDate = df.format(c.getTime());
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getdatashowgraph(arraytesteng1,arraytestnone1);
                getdataViewwordminweek(indextotalengword);
            }
        };handler.postDelayed(runnable,300);



    }
    public void getdatashowgraph(int[] XENG,int[] XNONE ){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        arraytesteng1 = XENG;
        arraytestnone1 = XNONE;
        double[] arrayall1 = new double[7];
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        StringBuffer buffer3 = new StringBuffer();
        StringBuffer buffer4 = new StringBuffer();
        StringBuffer buffer5 = new StringBuffer();
        if(resulttype == false) {
            for (int i = 0; i < 7; ++i) {
                int valueseng = arraytesteng1[abs(i - 6)];
                int valuesno = arraytestnone1[abs(i - 6)];
                buffer1.append(":" + valueseng + " ");
                buffer2.append(":" + valuesno + " ");
                buffer5.append(":" + indextimeingraph[i] + " ");

                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));
            }
            Log.d("TEST_SHOW_WEEK_ARRAY", "ARRAY_ENG :" + buffer1.toString());
            Log.d("TEST_SHOW_WEEK_ARRAY", "ARRAY_THAI :" + buffer2.toString());
            //Log.d("TEST_SHOW_WEEK_ARRAY", "indextimeingraph :" + buffer5.toString());

        /*
        BarDataSet barDataSet = new BarDataSet(dataVals, " ");
        barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
        barDataSet.setStackLabels(new String[]{"Number of English words", "Number of non-English words"});
        BarData data = new BarData(barDataSet);

        data.setValueFormatter(new DashboardFragment.MyValueFormatter());
        mChart.setData(data);//ระยะห่างระหว่างข้อมูล
        mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
        mChart.getLegend().setFormToTextSpace(3);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(indextimeingraph));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMaximum(7);
        xAxis.setDrawGridLines(false); //เส้นตาราง

        mChart.setDragEnabled(true);
        mChart.getAxisRight().setAxisMinimum(0);
        mChart.getAxisLeft().setAxisMinimum(0);
        mChart.setVisibleXRangeMaximum(7);
        mChart.invalidate();
        mChart.animateXY(2000, 4000);
        mChart.setDoubleTapToZoomEnabled(true);
        mChart.setPinchZoom(true);
        mChart.fitScreen();

         */


        }
        else if(resulttype == true){

            for(int i = 0; i < 7; ++i){
                arrayall1[i] = arraytesteng1[i];
                arrayall1[i] +=arraytestnone1[i];
            }


            for (int i = 0; i < 7; ++i) {
                float valueseng;
                double testvaleng = arraytesteng1[abs(i-6)] / arrayall1[abs(i-6)];

                if (Double.isNaN(testvaleng)) {
                    valueseng = (float) 0;
                } else {
                    valueseng = (float) testvaleng * 100;
                }

                float valuesno;
                double testvalno = arraytestnone1[abs(i-6)] / arrayall1[abs(i-6)];

                if (Double.isNaN(testvalno)) {
                    valuesno = (float) 0;
                } else {
                    valuesno = (float) testvalno * 100;
                }
                buffer3.append(":" + valueseng + " ");
                buffer4.append(":" + valuesno + " ");
                dataVals.add(new BarEntry(i, new float[]{valueseng,valuesno}));

            }
            Log.d("TEST_SHOW_WEEK_ARRAY", "ARRAY_ENG :" + buffer3.toString());
            Log.d("TEST_SHOW_WEEK_ARRAY", "ARRAY_THAI :" + buffer4.toString());


            /*
            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Percentage of English words", "Percentage of non-English words"});
            BarData data = new BarData(barDataSet);

            data.setValueFormatter(new DashboardFragment.MyValueFormatter());
            mChart.setData(data);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
            mChart.getLegend().setFormToTextSpace(3);
            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(indextimeingraph));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);
            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(7);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMaximum(7);
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();

             */

        }

    }

    public void getdataViewcontinuemax(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();

        indexcontinuemaxweek1=0;


        for (int index = 0; index < 7; index++) {
            String S_day = Integer.valueOf(getFormattedDay).toString();
            String S_month = Integer.valueOf(getFormattedMonth + 1).toString();
            String S_year = Integer.valueOf(getFormattedYear + 1900).toString();


            Call<List<DataContinuemax>>listcallgetdata = apiInterface.getDataContinuemax(email,S_day,S_month,S_year);
            listcallgetdata.enqueue(new Callback<List<DataContinuemax>>() {
                @Override
                public void onResponse(Call<List<DataContinuemax>> call, Response<List<DataContinuemax>> response) {
                    if (response.isSuccessful()) {
                        List<DataContinuemax> listdata = response.body();
                        int checking = 0;

                        if (listdata.size() !=0){
                            for(int i= 0;i<listdata.size();i++){
                                checking=Integer.parseInt(listdata.get(i).getMaxcon());

                                if(indexcontinuemaxweek1 < checking){
                                    indexcontinuemaxweek1 = checking;

                                }

                            }
                            //continuemaxweek = indexcontinuemaxweek1;
                            Log.e("TEST_SHOW_WEEK_ConMax","continuemaxweek: "+indexcontinuemaxweek1);
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
            c.add(Calendar.DATE, -1);
            formattedDate = df.format(c.getTime());
            getFormattedDay = c.getTime().getDate();
            getFormattedMonth = c.getTime().getMonth();
            getFormattedYear = c.getTime().getYear();


        }

        c.add(Calendar.DATE, +7);
        formattedDate = df.format(c.getTime());
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();

    }

    public void getdataViewwrongword(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
        WordWrong =new ArrayList<>();



        for (int index = 0; index < 7; index++) {
            String S_day = Integer.valueOf(getFormattedDay).toString();
            String S_month = Integer.valueOf(getFormattedMonth + 1).toString();
            String S_year = Integer.valueOf(getFormattedYear + 1900).toString();


            Call<List<DataWrongword>>listcallgetdata = apiInterface.getDataWrongword(email,S_day,S_month,S_year);
            listcallgetdata.enqueue(new Callback<List<DataWrongword>>() {
                @Override
                public void onResponse(Call<List<DataWrongword>> call, Response<List<DataWrongword>> response) {
                    if (response.isSuccessful()) {
                        List<DataWrongword> listdata = response.body();
                        if (listdata.size()==0){
                            return;
                        }
                        else {
                            for (int i = 0; i < listdata.size(); i++) {
                                WordWrong.add(listdata.get(i).getWord());
                            }
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
            c.add(Calendar.DATE, -1);
            formattedDate = df.format(c.getTime());
            getFormattedDay = c.getTime().getDate();
            getFormattedMonth = c.getTime().getMonth();
            getFormattedYear = c.getTime().getYear();


        }

        c.add(Calendar.DATE, +7);
        formattedDate = df.format(c.getTime());
        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = c.getTime().getMonth();
        getFormattedYear = c.getTime().getYear();
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //top3
                if(WordWrong.size()==0){
                    //nothing
                }else{
                    getWrongWordTop3(WordWrong);
                }
            }
        };handler.postDelayed(runnable,150);

    }
    public void getWrongWordTop3(ArrayList<String> wordfromdb){
        String[] wordtop1 = new String[3];
        String[] wordtrans1 = new String[3];
        for (int i = 0; i < 3; i++) {
            //wordtop[i] = "";
            wordtop1[i] = "";
        }

        for (int i = 0; i < 3; i++) {
           // wordtrans[i] = "";
            wordtrans1[i] = "";
        }

        //wordcount
        int N = wordfromdb.size();
        String word[] = new String[N];
        int count[] = new int[N];

        for (int i = 0; i < word.length; i++) {
            word[i] = "";
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
                   // wordtop[i] = word[i];
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
        }else{
            for (int i = 0; i < 3; i++) {
                if (!word[i].equals("")) {
                   // wordtop[i] = word[i];
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
        Log.e("TEST_SHOW_WEEK_WW", "WTop1(MySQL) :" +wordtop1[0]+" WTop2 :" +wordtop1[1]+" WTop3 :" +wordtop1[2]);
        Log.d("TEST_SHOW_WEEK_WW", "WTop1(SQLite) :" +wordtop[0]+" WTop2 :" +wordtop[1]+" WTop3 :" +wordtop[2]);
    }
    public void getdataViewwordminweek(int x){
        indextotalengword = x;
        double total1= (double)  indextotalengword * 1.0;
        double total2 = (double) indextotalmeeng / 60.0;
        if (total2 == 0.0){
            total2 =1;
        }
        double wordminweek1 = total1/total2;
       // wordminweek = wordminweek1;
        Log.e("TEST_SHOW_WEEK_WMWeek","wordminweek: "+wordminweek1);


    }



}