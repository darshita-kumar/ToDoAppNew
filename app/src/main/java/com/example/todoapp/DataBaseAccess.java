package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DataBaseAccess instance;

    /**
          * Private constructor to aboid object creation from outside classes.
          *
          * @param context
          */
    private DataBaseAccess(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    /**
          * Return a singleton instance of DatabaseAccess.
          *
          * @param context the Context
          * @return the instance of DabaseAccess
          */
    public static DataBaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    /**
          * Open the database connection.
          */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
          * Close the database connection.
          */
    public void close() {
        if (database != null)
        {
            this.database.close();
        }
    }

}

