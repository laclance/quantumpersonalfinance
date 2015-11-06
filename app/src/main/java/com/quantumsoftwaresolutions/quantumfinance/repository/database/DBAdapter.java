package com.quantumsoftwaresolutions.quantumfinance.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper {
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_SURFSPOT = "surfspot";

    public static final String TABLE_SURFAREA= "surfarea";
    public static final String COLUMN_NAME = "type";
    public static final String COLUMN_DESCRIPTION = "description";

    private static final String DATABASE_NAME = "quantumfinancedb";
    private static final int DATABASE_VERSION = 1; 
    
   private static final String CREATE_USER_TABLE = "create table IF NOT EXISTS "
            + TABLE_USER + " ("
            + COLUMN_ID + " integer primary key autoincrement , "
           + COLUMN_USERNAME + " text not null , "
           + COLUMN_PASSWORD + " text not null ) ; " ;

    private static final String CREATE_SURFAREA_TABLE = "create table IF NOT EXISTS "
            + TABLE_SURFAREA + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DESCRIPTION + " text);" ;

    private static final String CREATE_SURFSPOT_TABLE = "create table IF NOT EXISTS "
            + TABLE_SURFSPOT + " ("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_DESCRIPTION + " text);" ;

    public DBAdapter(Context context) {
        super (context , DATABASE_NAME , null , DATABASE_VERSION ) ;
    }

    @Override public void onCreate( SQLiteDatabase database) {
         database.execSQL( CREATE_USER_TABLE ) ;
        database.execSQL( CREATE_SURFAREA_TABLE ) ;
        database.execSQL( CREATE_SURFSPOT_TABLE ) ;

    }

    @Override
    public void onUpgrade( SQLiteDatabase db , int oldVersion , int newVersion) {
        Log.w(DBAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + " , which will destroy all old data") ;
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER) ;
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_SURFAREA) ;
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_SURFSPOT) ;
        onCreate(db) ;
    }
}
