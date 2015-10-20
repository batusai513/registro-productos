package com.example.richard.registrodeproductos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Richard on 10/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MYBDName.sqlite";
    public static final int DB_SCHEMA_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBManager.DROP_TABLE);
    }
}
