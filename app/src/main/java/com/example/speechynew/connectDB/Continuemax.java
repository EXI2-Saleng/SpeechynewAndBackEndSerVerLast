package com.example.speechynew.connectDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.example.speechynew.connectDB.Continuemaxinterface.DAY;
import static com.example.speechynew.connectDB.Continuemaxinterface.DATE;
import static com.example.speechynew.connectDB.Continuemaxinterface.HOUR;
import static com.example.speechynew.connectDB.Continuemaxinterface.MAXCON;
import static com.example.speechynew.connectDB.Continuemaxinterface.MINUTE;
import static com.example.speechynew.connectDB.Continuemaxinterface.MONTH;
import static com.example.speechynew.connectDB.Continuemaxinterface.SECOND;
import static com.example.speechynew.connectDB.Continuemaxinterface.TABLE_NAME10;
import static com.example.speechynew.connectDB.Continuemaxinterface.YEAR;
import static com.example.speechynew.connectDB.Wordinterface.TABLE_NAME3;


public class Continuemax extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Continuemax.db";
    private static final int DATABASE_VERSION = 1;

    public Continuemax(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME10 + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MAXCON + " TEXT NOT NULL, " + DAY + " TEXT NOT NULL, " + DATE + " TEXT NOT NULL, " +
                MONTH + " TEXT NOT NULL, " + YEAR + " TEXT NOT NULL, " + HOUR + " TEXT NOT NULL, " + MINUTE + " TEXT NOT NULL, " + SECOND + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean updatestatus(int id,String stus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(DAY,stus);
        db.update(TABLE_NAME10,content,"_id=?",new String[]{String.valueOf(id)});
        return true;
    }

    public Cursor getAlldata(){
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor resDat = db.rawQuery("SELECT * FROM "+TABLE_NAME10,null);
        return resDat;
    }

}
