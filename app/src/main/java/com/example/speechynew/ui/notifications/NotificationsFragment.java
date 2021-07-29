package com.example.speechynew.ui.notifications;

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
import com.example.speechynew.ui.home.HomeFragment;
import com.example.speechynew.ui.home.HomeReport;
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

import org.w3c.dom.Text;

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

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    BarChart mChart;
    TextView month;
    ImageButton graphminus;
    ImageButton graphplus;

    Engword eng;
    Word anothereng;
    Timeprocess time;
    Continuemax continuemax;
    Wrongword wrongword;

    Button changetype;
    boolean resulttype;
    TextView wait5month;

    Calendar c;
    SimpleDateFormat df;
    String formattedDate;

    int getFormattedDay;
    int getFormattedMonth;
    int getFormattedYear;

    Button nextpage;
    String datemonth;
    String showtotalwordmonth;
    String showtotaltimemonth;

    int totalwordeng = 0;
    int totaltimeeng = 0;

    double wordminmonth;
    int continuemaxmonth = 0;
    String[] wordtop = new String[3];
    String[] wordtrans = new String[3];

    String[] timegraph;

    private FirebaseAuth firebaseAuth;
    ApiInterface apiInterface;
    int totaltime1 = 0;
    int indextotalmeeng =0;
    int indextotalengword = 0;
    int indextotalanyword = 0;
    int indextotalall = 0;
    int indexcontinuemaxmonth1 =0;

    int[] arraytesteng1 = new int[32];
    int[] arraytestnone1 = new int[32];

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        //final TextView textView = root.findViewById(R.id.viewmonth);

        mChart = root.findViewById(R.id.mp_barchart1);
        month = root.findViewById(R.id.showmonth);

        nextpage = root.findViewById(R.id.nextpage);
        graphminus = root.findViewById(R.id.graphminus);
        graphplus = root.findViewById(R.id.graphplus);
        changetype = root.findViewById(R.id.changetype);
        wait5month = root.findViewById(R.id.wait5month);

        resulttype = false;

        eng = new Engword(root.getContext());
        anothereng = new Word(root.getContext());
        time = new Timeprocess(root.getContext());
        continuemax = new Continuemax(root.getContext());
        wrongword = new Wrongword(root.getContext());

        c = Calendar.getInstance();

        df = new SimpleDateFormat("d-MM-yyyy");
        formattedDate = df.format(c.getTime());

        getFormattedDay = c.getTime().getDate();
        getFormattedMonth = (c.getTime().getMonth()) + 1;
        getFormattedYear = (c.getTime().getYear()) + 1900;

        checkmonth();

        mChart.getDescription().setEnabled(false);
        mChart.setFitBars(true);

        wait5month.setText(" Please wait 5 second");

        nextpageonclick();
        viewdatamonth();
        viewtotalmonth();
        viewtotaltimemonth();
        wordminmonth();
        continuemaxmonth();
        wrongwordtop3();

        getdataViewAnywordMonth();
        getdataViewtotaltimemonth();
        getdataViewcontinuemaxMonth();
        getdataViewWrongwordMonth();
        getdataViewtotalMonth();

        //minus month -
        graphminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.MONTH, -1);
                formattedDate = df.format(c.getTime());
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = (c.getTime().getMonth()) + 1;
                getFormattedYear = (c.getTime().getYear()) + 1900;

                wait5month.setText(" Please wait 5 second");

                nextpageonclick();
                checkmonth();
                viewdatamonth();
                viewtotalmonth();
                viewtotaltimemonth();
                wordminmonth();
                continuemaxmonth();
                wrongwordtop3();

                getdataViewAnywordMonth();
                getdataViewtotaltimemonth();
                getdataViewcontinuemaxMonth();
                getdataViewWrongwordMonth();
                getdataViewtotalMonth();

            }
        });

        //plus month +
        graphplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.add(Calendar.MONTH, 1);
                formattedDate = df.format(c.getTime());
                getFormattedDay = c.getTime().getDate();
                getFormattedMonth = (c.getTime().getMonth()) + 1;
                getFormattedYear = (c.getTime().getYear()) + 1900;

                //check > current time
                if (c.after(Calendar.getInstance())) {

                    c.add(Calendar.MONTH, -1);
                    formattedDate = df.format(c.getTime());
                    getFormattedDay = c.getTime().getDate();
                    getFormattedMonth = (c.getTime().getMonth()) + 1;
                    getFormattedYear = (c.getTime().getYear()) + 1900;

                } else {

                    wait5month.setText(" Please wait 5 second");

                    nextpageonclick();
                    checkmonth();
                    viewdatamonth();
                    viewtotalmonth();
                    viewtotaltimemonth();
                    wordminmonth();
                    continuemaxmonth();
                    wrongwordtop3();

                    getdataViewAnywordMonth();
                    getdataViewtotaltimemonth();
                    getdataViewcontinuemaxMonth();
                    getdataViewWrongwordMonth();
                    getdataViewtotalMonth();
                }
            }
        });


        changetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change from word count to Percentage
                if (resulttype == false) {
                    resulttype = true;
                    viewdatamonth();
                    getdataViewtotalMonth();
                    changetype.setText("Word count");

                    //change from Percentage to word count
                } else if (resulttype == true) {
                    resulttype = false;
                    viewdatamonth();
                    getdataViewtotalMonth();
                    changetype.setText("Percentage");
                }
            }
        });


        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // textView.setText(s);
            }
        });


        return root;
    }


    void nextpageonclick() {

        nextpage.setEnabled(false);
        nextpage.setBackgroundResource(R.drawable.buttonshape11);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                nextpage.setEnabled(true);
                nextpage.setBackgroundResource(R.drawable.buttonshape6);

                wait5month.setText("");

                nextpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent report = new Intent(getContext(), NotificationsReport.class);
                        report.putExtra("datemonth", datemonth);
                        report.putExtra("showtotalwordmonth", showtotalwordmonth);
                        report.putExtra("showtotaltimemonth", showtotaltimemonth);
                        report.putExtra("wordminmonth", wordminmonth);
                        report.putExtra("continuemaxmonth", continuemaxmonth);
                        report.putExtra("wordtopmonth", wordtop);
                        report.putExtra("wordtrans", wordtrans);
                        startActivity(report);

                    }
                });
            }

        };
        handler.postDelayed(runnable, 4000);

    }

    public void viewdatamonth() {

        if (resulttype == false) {

            ArrayList<BarEntry> dataVals = new ArrayList<>();


            int test = 32;
            int[] arraytesteng = new int[32];
            int[] arraytestnone = new int[32];

            SQLiteDatabase db2 = eng.getWritableDatabase();
            Cursor res = db2.rawQuery("select * from " + TABLE_NAME2 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

            if (res.getCount() == 0) {
                for (int i = 0; i < test; ++i) {
                    int values = 0;
                    //yValsEng.add(new BarEntry(i, values));
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (res.moveToNext()) {
                arraytesteng[res.getInt(3)] += res.getInt(1);
            }


            SQLiteDatabase db3 = anothereng.getWritableDatabase();
            Cursor resnone = db3.rawQuery("select * from " + TABLE_NAME3 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

            if (resnone.getCount() == 0) {
                for (int i = 0; i < test; ++i) {
                    int values = 0;
                    //yValsNone.add(new BarEntry(i, values));
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (resnone.moveToNext()) {
                arraytestnone[resnone.getInt(3)] += resnone.getInt(1);
            }


            for (int i = 0; i < test; ++i) {
                int valueseng = arraytesteng[i];
                int valuesno = arraytestnone[i];
                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));

            }


            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Number of English words", "Number of non-English words"});
            BarData data = new BarData(barDataSet);
            mChart.setData(data);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
            mChart.getLegend().setFormToTextSpace(3);

            data.setValueFormatter(new NotificationsFragment.MyValueFormatter());

            checktitlegraphX(c.getActualMaximum(Calendar.DATE));

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(timegraph)); //////here
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);

            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(c.getActualMaximum(Calendar.DATE) + 1);
            xAxis.setAxisMinimum(0);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMinimum(1);
            mChart.setVisibleXRangeMaximum(c.getActualMaximum(Calendar.DATE));
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();



        } else if (resulttype == true) {

            ArrayList<BarEntry> dataVals = new ArrayList<>();

            int test = 32;
            double[] arraytesteng = new double[32];
            double[] arraytestnone = new double[32];
            double[] arrayall = new double[32];

            SQLiteDatabase db2 = eng.getWritableDatabase();
            Cursor res = db2.rawQuery("select * from " + TABLE_NAME2 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

            if (res.getCount() == 0) {
                for (int i = 0; i < test; ++i) {
                    int values = 0;
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (res.moveToNext()) {
                arraytesteng[res.getInt(3)] += res.getInt(1);
            }

            SQLiteDatabase db3 = anothereng.getWritableDatabase();
            Cursor resnone = db3.rawQuery("select * from " + TABLE_NAME3 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

            if (resnone.getCount() == 0) {
                for (int i = 0; i < test; ++i) {
                    int values = 0;
                    dataVals.add(new BarEntry(i, values));
                }
            }

            while (resnone.moveToNext()) {
                arraytestnone[resnone.getInt(3)] += resnone.getInt(1);
            }

            for (int i = 0; i < 32; ++i) {
                arrayall[i] = arraytesteng[i];
            }

            for (int i = 0; i < 32; ++i) {
                arrayall[i] += arraytestnone[i];
            }

            for (int i = 0; i < test; ++i) {

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

                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));

            }


            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Percentage of English words", "Percentage of non-English words"});
            BarData data = new BarData(barDataSet);
            mChart.setData(data);
            data.setValueFormatter(new NotificationsFragment.MyValueFormatter());
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setFormToTextSpace(3);//ระยะห่างระหว่างรูปกับคำอธิบาย

            checktitlegraphX(c.getActualMaximum(Calendar.DATE));

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(timegraph));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);

            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(c.getActualMaximum(Calendar.DATE) + 1);
            xAxis.setAxisMinimum(0);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMinimum(1);
            mChart.setVisibleXRangeMaximum(c.getActualMaximum(Calendar.DATE));
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();


        }

        checkmonth();

    }

    public void viewtotalmonth() {

        int totalall = 0;
        int totaleng = 0;
        int totalanother = 0;

        SQLiteDatabase db2 = eng.getWritableDatabase();
        Cursor res2 = db2.rawQuery("select * from " + TABLE_NAME2 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

        int countlenghtres2 = res2.getCount();

        if (countlenghtres2 == 0) {
            totaleng = 0;
        }

        while (res2.moveToNext()) {
            totaleng += res2.getInt(1);
        }

        SQLiteDatabase db3 = anothereng.getWritableDatabase();
        Cursor res3 = db3.rawQuery("select * from " + TABLE_NAME3 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

        int countlenghtres3 = res3.getCount();

        if (countlenghtres3 == 0) {
            totalanother = 0;
        }

        while (res3.moveToNext()) {
            totalanother += res3.getInt(1);
        }

        totalall = totaleng + totalanother;

        totalwordeng = totaleng;

        showtotalwordmonth = totaleng + " / " + totalall;
    }


    public void viewtotaltimemonth() {

        int totaltime = 0;

        SQLiteDatabase dbtime = time.getWritableDatabase();
        Cursor restime = dbtime.rawQuery("select * from " + TABLE_NAME5 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

        int countrestime = restime.getCount();

        if (countrestime == 0) {
            totaltime = 0;
        }

        while (restime.moveToNext()) {
            totaltime += restime.getInt(8);
        }

        totaltimeeng = totaltime;

        int numberOfDays = totaltime / 86400;
        int numberOfHours = (totaltime % 86400) / 3600;
        int numberOfMinutes = ((totaltime % 86400) % 3600) / 60;
        int numberOfSeconds = ((totaltime % 86400) % 3600) % 60;


        if (numberOfHours < 10) {
            if (numberOfMinutes < 10) {
                if (numberOfSeconds < 10) {
                    String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : 0" + numberOfSeconds;
                    showtotaltimemonth = text;
                } else {
                    String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : " + numberOfSeconds;
                    showtotaltimemonth = text;
                }
            } else {
                String text = numberOfDays + " Day  \n0" + numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                showtotaltimemonth = text;
            }
        } else {
            showtotaltimemonth = numberOfDays + " Day  \n" + numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
        }

    }


    public void wordminmonth() {

        double total = (double) totalwordeng * 1.0;
        double total2 = (double) totaltimeeng / 60.0;

        if (total2 == 0.0) {
            total2 = 1.0;
        }

        wordminmonth = total / total2;

    }


    public void continuemaxmonth() {

        int checking = 0;
        continuemaxmonth = 0;
        SQLiteDatabase dbcon = continuemax.getWritableDatabase();
        Cursor resdbcon = dbcon.rawQuery("select * from " + TABLE_NAME10 + " where month = " + getFormattedMonth + " and year = " + getFormattedYear, null);

        while (resdbcon.moveToNext()) {
            checking = resdbcon.getInt(1);

            if (continuemaxmonth < checking) {
                continuemaxmonth = checking;
            }
        }

    }

    public void wrongwordtop3() {

        ArrayList<String> wordfromdb = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            wordtop[i] = "";
        }

        for (int i = 0; i < 3; i++) {
            wordtrans[i] = "";
        }

        SQLiteDatabase dbwrong = wrongword.getWritableDatabase();
        Cursor resdbwrong = dbwrong.rawQuery("select * from " + TABLE_NAME11 + " where month = " + getFormattedMonth +
                " and year = " + getFormattedYear, null);

        int countresdbwrong = resdbwrong.getCount();

        if (countresdbwrong == 0) {
            return;
        }

        while (resdbwrong.moveToNext()) {
            wordfromdb.add(resdbwrong.getString(1));
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
            for (int j = i + 1; j < N - 1; j++) {
                if (count[i] < count[j] && !word[i].equals("") && !word[j].equals("")) {
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

        if (word.length < 3) {
            for (int i = 0; i < word.length; ++i) {
                if (!word[i].equals("")) {
                    wordtop[i] = word[i];
                    System.out.println(word[i] + "   " + count[i]);

                    final Translator t = new Translator(wordtop[i], getContext());
                    t.trans();

                    Handler handler = new Handler();
                    final int finalI = i;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            wordtrans[finalI] = t.trans();

                        }
                    };
                    handler.postDelayed(runnable, 4000);
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                if (!word[i].equals("")) {
                    wordtop[i] = word[i];
                    System.out.println(word[i] + "   " + count[i]);
                    final Translator t = new Translator(wordtop[i], getContext());
                    t.trans();

                    Handler handler = new Handler();
                    final int finalI = i;
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            wordtrans[finalI] = t.trans();
                        }
                    };
                    handler.postDelayed(runnable, 4000);
                }
            }
        }
    }

    void checkmonth() {
        if (getFormattedMonth == 1) {
            month.setText("January " + getFormattedYear);
            datemonth = "January " + getFormattedYear;
        } else if (getFormattedMonth == 2) {
            month.setText("February " + getFormattedYear);
            datemonth = "February " + getFormattedYear;
        } else if (getFormattedMonth == 3) {
            month.setText("March " + getFormattedYear);
            datemonth = "March " + getFormattedYear;
        } else if (getFormattedMonth == 4) {
            month.setText("April " + getFormattedYear);
            datemonth = "April " + getFormattedYear;
        } else if (getFormattedMonth == 5) {
            month.setText("May " + getFormattedYear);
            datemonth = "May " + getFormattedYear;
        } else if (getFormattedMonth == 6) {
            month.setText("June " + getFormattedYear);
            datemonth = "June " + getFormattedYear;
        } else if (getFormattedMonth == 7) {
            month.setText("July " + getFormattedYear);
            datemonth = "July " + getFormattedYear;
        } else if (getFormattedMonth == 8) {
            month.setText("August " + getFormattedYear);
            datemonth = "August " + getFormattedYear;
        } else if (getFormattedMonth == 9) {
            month.setText("September " + getFormattedYear);
            datemonth = "September " + getFormattedYear;
        } else if (getFormattedMonth == 10) {
            month.setText("October " + getFormattedYear);
            datemonth = "October " + getFormattedYear;
        } else if (getFormattedMonth == 11) {
            month.setText("November " + getFormattedYear);
            datemonth = "November " + getFormattedYear;
        } else if (getFormattedMonth == 12) {
            month.setText("December " + getFormattedYear);
            datemonth = "December " + getFormattedYear;
        }
    }


    void checktitlegraphX(int day) {

        if (day == 31) {
            timegraph = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                    , "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        } else if (day == 30) {
            timegraph = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                    , "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
        } else if (day == 29) {
            timegraph = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                    , "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
        } else if (day == 28) {
            timegraph = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
                    , "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
        }

    }

    private class MyValueFormatter implements IValueFormatter {
        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if (value > 0) {
                return mFormat.format(value);
            } else {
                return "";
            }
        }
    }

    public void getdataViewtotaltimemonth(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_month =Integer.valueOf(getFormattedMonth).toString();
        String S_year =Integer.valueOf(getFormattedYear).toString();
        String email = firebaseUser.getEmail();
        indextotalmeeng=0;
        Call<List<DataTime>>listcallgetdata = apiInterface.getDataTimeMonth(email,S_month,S_year);
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
                        Log.d("TEST_SHOW_MONTH_TIME","(totaltime1) totaltimeeng: "+totaltime1);
                    }

                    int numberOfDays = totaltime1 / 86400;
                    int numberOfHours = (totaltime1 % 86400) / 3600;
                    int numberOfMinutes = ((totaltime1 % 86400) % 3600) / 60;
                    int numberOfSeconds = ((totaltime1 % 86400) % 3600) % 60;


                    if(numberOfHours<10) {
                        if (numberOfMinutes < 10) {
                            if (numberOfSeconds < 10) {
                                String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : 0" + numberOfSeconds;
                                //showtotaltimemonth = text;
                                Log.d("TEST_SHOW_MONTH_TIME","showtotaltimemonth: "+text);

                            } else {
                                String text = numberOfDays + " Day  \n0" + numberOfHours + " : 0" + numberOfMinutes + " : " + numberOfSeconds;
                                //showtotaltimemonth = text;
                                Log.d("TEST_SHOW_MONTH_TIME","showtotaltimemonth: "+text);
                            }
                        }else{
                            String text = numberOfDays + " Day  \n0" + numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds;
                            //showtotaltimemonth = text;
                            Log.d("TEST_SHOW_MONTH_TIME","showtotaltimemonth: "+text);
                        }
                    }else{
                        String text = numberOfDays+" Day  \n"+numberOfHours + " : " + numberOfMinutes + " : " + numberOfSeconds ;
                        //showtotaltimemonth = text;
                        Log.d("TEST_SHOW_MONTH_TIME","showtotaltimemonth: "+text);
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

    public void getdataViewAnywordMonth(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_month =Integer.valueOf(getFormattedMonth).toString();
        String S_year =Integer.valueOf(getFormattedYear).toString();
        String email = firebaseUser.getEmail();
        Log.d("TEST_SHOW_MONTH",datemonth);
        Log.d("TEST_SHOW_MONTH","Email :"+email);

        int[] totalall = {0};
        int[] totaleng = {0};
        int[] totalanother = {0};
        int[] arraytestnone2 = new int[32];
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        Call<List<DataAnyword>>listcallgetdata = apiInterface.getDataAnywordMonth(email,S_month,S_year);
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

                            arraytestnone2[Integer.parseInt(listdata.get(i).getDate())]+=Integer.parseInt(listdata.get(i).getWord());

                        }
                        StringBuffer buffer1 = new StringBuffer();
                        for(int i = 0;i<32 ;i++){
                            buffer1.append(":"+arraytestnone2[i]+" ");
                        }

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
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                indextotalanyword = totalanother[0];
                arraytestnone1=arraytestnone2;
                Log.d("TEST_SHOW_MONTH_ANYWORD", "ANYCOUNT :" + indextotalanyword);

            }
        };handler.postDelayed(runnable,100);

    }

    public void getdataViewtotalMonth() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_day = Integer.valueOf(getFormattedDay).toString();
        String S_month = Integer.valueOf(getFormattedMonth).toString();
        String S_year = Integer.valueOf(getFormattedYear).toString();
        String email = firebaseUser.getEmail();

        int[] totalall = {0};
        int[] totaleng = {0};
        int[] totalanother = {0};
        int[] arraytesteng2 = new int[32];
        double[] arrayall1 = new double[32];
        indextotalengword=0;
        for(int i = 0;i<32 ;i++){
            arraytesteng1[i]=0;
        }

        ArrayList<BarEntry> dataVals = new ArrayList<>();
        Call<List<DataEngword>> listcallgetdataEngword = apiInterface.getDataEngwordMonth(email, S_month, S_year);
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
                            arraytesteng2[Integer.parseInt(listdata.get(i).getDate())]+=Integer.parseInt(listdata.get(i).getWord());
                        }

                        arraytesteng1=arraytesteng2;
                        indextotalengword = totaleng[0];
                    }


                    indextotalall = indextotalanyword + indextotalengword;
                    String TTWD1 = indextotalengword + " / " + indextotalall;


                    //getdataViewwordminday(indextotalengword);

                } else {
                    Log.d("TEST_GET_LISTDATA", "Fail:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DataEngword>> call, Throwable t) {
                Log.d("TEST_GET_LISTDATA", t + "");
            }
        });
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                indextotalall = indextotalanyword + indextotalengword;
                String TTWD1 = indextotalengword + " / " + indextotalall;
                getdatashowgraphMonth(arraytesteng1,arraytestnone1);
                getdataViewwordminmonth(indextotalengword);

                //showtotalwordmonth =TTWD1;
                Log.d("TEST_SHOW_MONTH_ENGWORD", "indextotalengwordMonth :" + indextotalengword);
                Log.d("TEST_SHOW_MONTH_WNGWORD", "indextotalallMonth :" + TTWD1);
            }
        };handler.postDelayed(runnable,700);


    }
    public void getdatashowgraphMonth(int[] XENG,int[] XNONE ){
        ArrayList<BarEntry> dataVals = new ArrayList<>();
        double[] arrayall1 = new double[32];
        arraytesteng1 = XENG;
        arraytestnone1 = XNONE;
        StringBuffer buffer1 = new StringBuffer();
        StringBuffer buffer2 = new StringBuffer();
        StringBuffer buffer3 = new StringBuffer();
        StringBuffer buffer4 = new StringBuffer();
        if (resulttype == false){
            for (int i = 0; i < 32; ++i) {
                int valueseng = arraytesteng1[i];
                int valuesno = arraytestnone1[i];
                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));

                buffer1.append(":"+valueseng+" ");
                buffer2.append(":"+valuesno+" ");

            }
            Log.d("TEST_SHOW_MONTH_ARRAY", "ARRAY_ENG :" + buffer1.toString());
            Log.d("TEST_SHOW_MONTH_ARRAY", "ARRAY_THAI :" + buffer2.toString());

            /*
            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Number of English words", "Number of non-English words"});
            BarData data = new BarData(barDataSet);
            mChart.setData(data);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างรูปกับคำอธิบาย
            mChart.getLegend().setFormToTextSpace(3);

            data.setValueFormatter(new NotificationsFragment.MyValueFormatter());

            checktitlegraphX(c.getActualMaximum(Calendar.DATE));

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(timegraph)); //////here
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);

            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(c.getActualMaximum(Calendar.DATE) + 1);
            xAxis.setAxisMinimum(0);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMinimum(1);
            mChart.setVisibleXRangeMaximum(c.getActualMaximum(Calendar.DATE));
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();

             */


        }

        else if(resulttype ==true){
            for (int i = 0; i < 32; ++i) {
                arrayall1[i] = arraytesteng1[i];
                arrayall1[i] += arraytestnone1[i];
            }


            for (int i = 0; i < 32; ++i) {

                float valueseng;
                double testvaleng = arraytesteng1[i] / arrayall1[i];

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

                dataVals.add(new BarEntry(i, new float[]{valueseng, valuesno}));
                buffer3.append(":" + valueseng + " ");
                buffer4.append(":" + valuesno + " ");

            }
            Log.d("TEST_SHOW_MONTH_ARRAY", "ARRAY_ENG :" + buffer3.toString());
            Log.d("TEST_SHOW_MONTH_ARRAY", "ARRAY_THAI :" + buffer4.toString());

            /*
            BarDataSet barDataSet = new BarDataSet(dataVals, " ");
            barDataSet.setColors(Color.parseColor("#FF9933"), Color.parseColor("#8CB9D1"));
            barDataSet.setStackLabels(new String[]{"Percentage of English words", "Percentage of non-English words"});
            BarData data = new BarData(barDataSet);
            mChart.setData(data);
            data.setValueFormatter(new NotificationsFragment.MyValueFormatter());
            mChart.getLegend().setXEntrySpace(12);//ระยะห่างระหว่างข้อมูล
            mChart.getLegend().setFormToTextSpace(3);//ระยะห่างระหว่างรูปกับคำอธิบาย

            checktitlegraphX(c.getActualMaximum(Calendar.DATE));

            XAxis xAxis = mChart.getXAxis();
            xAxis.setValueFormatter(new IndexAxisValueFormatter(timegraph));
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1);

            xAxis.setCenterAxisLabels(false);
            xAxis.setGranularityEnabled(true);
            xAxis.setAxisMaximum(c.getActualMaximum(Calendar.DATE) + 1);
            xAxis.setAxisMinimum(0);
            xAxis.setDrawGridLines(false); //เส้นตาราง

            mChart.setDragEnabled(true);
            mChart.getAxisRight().setAxisMinimum(0);
            mChart.getAxisLeft().setAxisMinimum(0);
            mChart.setVisibleXRangeMinimum(1);
            mChart.setVisibleXRangeMaximum(c.getActualMaximum(Calendar.DATE));
            mChart.invalidate();
            mChart.animateXY(2000, 4000);
            mChart.setDoubleTapToZoomEnabled(true);
            mChart.setPinchZoom(true);
            mChart.fitScreen();

             */



        }
        checkmonth();

    }
    public void getdataViewcontinuemaxMonth(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_month =Integer.valueOf(getFormattedMonth).toString();
        String S_year =Integer.valueOf(getFormattedYear).toString();
        String email = firebaseUser.getEmail();

        Call<List<DataContinuemax>>listcallgetdata = apiInterface.getDataContinuemaxMonth(email,S_month,S_year);
        listcallgetdata.enqueue(new Callback<List<DataContinuemax>>() {
            @Override
            public void onResponse(Call<List<DataContinuemax>> call, Response<List<DataContinuemax>> response) {
                if (response.isSuccessful()) {
                    List<DataContinuemax> listdata = response.body();
                    int checking = 0;

                    if (listdata.size() !=0){
                        for(int i= 0;i<listdata.size();i++){
                            checking=Integer.parseInt(listdata.get(i).getMaxcon());

                            if(indexcontinuemaxmonth1 < checking){
                                indexcontinuemaxmonth1 = checking;

                            }

                        }
                        //continuemaxmonth = indexcontinuemaxmonth1;
                        Log.d("TEST_SHOW_MONTH_ConMax","continuemaxmonth: "+indexcontinuemaxmonth1);
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
    public void getdataViewWrongwordMonth(){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String S_month =Integer.valueOf(getFormattedMonth).toString();
        String S_year =Integer.valueOf(getFormattedYear).toString();
        String email = firebaseUser.getEmail();

        Call<List<DataWrongword>>listcallgetdata = apiInterface.getDataWrongwordMonth(email,S_month,S_year);
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
                            for (int j = i + 1; j < N - 1; j++) {
                                if (count[i] < count[j] && !word[i].equals("") && !word[j].equals("")) {
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

                        if (word.length < 3) {
                            for (int i = 0; i < word.length; ++i) {
                                if (!word[i].equals("")) {
                                    //wordtop[i] = word[i];
                                    wordtop1[i] = word[i];
                                    System.out.println(word[i] + "   " + count[i]);

                                   // final Translator t = new Translator(wordtop[i], getContext());
                                    final Translator t = new Translator(wordtop1[i], getContext());
                                    t.trans();

                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                           //wordtrans[finalI] = t.trans();
                                            wordtrans1[finalI] = t.trans();

                                        }
                                    };
                                    handler.postDelayed(runnable, 4000);
                                }
                            }
                        } else {
                            for (int i = 0; i < 3; i++) {
                                if (!word[i].equals("")) {
                                   // wordtop[i] = word[i];
                                    wordtop1[i] = word[i];
                                    System.out.println(word[i] + "   " + count[i]);
                                   // final Translator t = new Translator(wordtop[i], getContext());
                                    final Translator t = new Translator(wordtop1[i], getContext());
                                    t.trans();

                                    Handler handler = new Handler();
                                    final int finalI = i;
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                          //  wordtrans[finalI] = t.trans();
                                            wordtrans1[finalI] = t.trans();
                                        }
                                    };
                                    handler.postDelayed(runnable, 4000);
                                }
                            }
                        }
                        ///wordtop[0]="TEST1";wordtop[1]="TEST2";wordtop[2]="TEST3";
                        Log.e("TEST_SHOW_MONTH_WW", "WTop1 MySQL:" +wordtop1[0]+" WTop2 :" +wordtop1[1]+" WTop3 :" +wordtop1[2]);
                        Log.d("TEST_SHOW_MONTH_WW", "WTop1 SQLite:" +wordtop[0]+" WTop2 :" +wordtop[1]+" WTop3 :" +wordtop[2]);

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

    public void getdataViewwordminmonth(int x){
        indextotalengword = x;
        double total1= (double)  indextotalengword * 1.0;
        double total2 = (double) indextotalmeeng / 60.0;
        if (total2 == 0.0){
            total2 =1;
        }
        double wordminmonth1 = total1/total2;
        //wordminmonth = wordminmonth1;
        Log.d("TEST_SHOW_MONTH_WORDMIN","wordminmonth: "+wordminmonth1);


    }




}