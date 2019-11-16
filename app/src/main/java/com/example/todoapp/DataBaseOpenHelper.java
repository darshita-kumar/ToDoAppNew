package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Events_Datsbase.db";
    private static  final String TABLE_NAME = "CHECKLIST";
    private static  final String Col0 = "SRNO";
    private static  final String Col1 = "TITLE";
    private static  final String Col2 = "DESCRIPTION";
    private static  final String Col3= "DATE";
    private static final String Col4 = "TIME";
    private static final String Col5 = "NOTIFY";
    private static final int DATABASE_VERSION = 1;
    //SQLiteDatabase db = this.getWritableDatabase();

    public DataBaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(SRNO INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT, DESCRIPTION TEXT, DATE TEXT, TIME TEXT, NOTIFY TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insert(Event ev)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Col1, ev.title);
        values.put(Col2, ev.description);
        values.put(Col3, ev.date);
        values.put(Col4, ev.time);
        values.put(Col5, ev.notification);
        long l = db.insert(TABLE_NAME,null,values);
        if(l == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return res;
    }

}
