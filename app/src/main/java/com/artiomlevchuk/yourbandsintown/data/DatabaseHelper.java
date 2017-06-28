package com.artiomlevchuk.yourbandsintown.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.artiomlevchuk.yourbandsintown.data.DatabaseDescription.*;

class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BITManagerDatabase";
    private static final int DATABASE_VERSION = 1;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String ARTISTS_TABLE_SQL = "CREATE TABLE " + Artists.TABLE_NAME + "(" +
                Artists._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Artists.COLUMN_NAME + " TEXT NOT NULL, " +
                Artists.COLUMN_URL + " TEXT NOT NULL, " +
                Artists.COLUMN_AVATAR + " BLOB, " +
                Artists.COLUMN_FACEBOOK_URL + " TEXT NOT NULL, " +
                Artists.COLUMN_TRACKER_COUNT + " INTEGER NOT NULL, " +
                Artists.COLUMN_EVENTS_COUNT + " INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(ARTISTS_TABLE_SQL);

        final String EVENTS_TABLE_SQL = "CREATE TABLE " + Events.TABLE_NAME + "(" +
                Events._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Events.COLUMN_ARTIST + " TEXT NOT NULL, " +
                Events.COLUMN_EVENT_URL + " TEXT NOT NULL, " +
                Events.COLUMN_DATETIME + " TEXT NOT NULL, " +
                Events.COLUMN_EVENT_NAME + " TEXT NOT NULL, " +
                Events.COLUMN_LATITUDE + " REAL NOT NULL, " +
                Events.COLUMN_LONGITUDE + " REAL NOT NULL, " +
                Events.COLUMN_CITY + " TEXT NOT NULL, " +
                Events.COLUMN_COUNTRY + " TEXT NOT NULL, " +
                Events.COLUMN_STATUS + " TEXT, " +
                Events.COLUMN_TICKETS_URL + " TEXT);";
        sqLiteDatabase.execSQL(EVENTS_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { }

}