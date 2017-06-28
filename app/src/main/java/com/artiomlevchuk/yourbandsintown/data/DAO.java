package com.artiomlevchuk.yourbandsintown.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.artiomlevchuk.yourbandsintown.data.DatabaseDescription.*;
import com.artiomlevchuk.yourbandsintown.entities.Artist;
import com.artiomlevchuk.yourbandsintown.entities.Event;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDbHelper;

    public DAO(Context context) {
        mDbHelper = new DatabaseHelper(context);
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public int getSize(String tableName) {
        Cursor cursor = mDatabase.query(tableName, new String[] {"*"}, null, null, null, null, null);
        int size = cursor.getCount();
        cursor.close();
        return size;
    }

    public String getArtistNameById(String id) {
        Cursor cursor = mDatabase.query(Artists.TABLE_NAME,
                new String[] {Artists.COLUMN_NAME}, Artists._ID + " = ? ",
                new String[] {id}, null, null, null);
        cursor.moveToNext();
        String name = cursor.getString(cursor.getColumnIndex(Artists.COLUMN_NAME));
        cursor.close();
        return name;
    }

    public void putArtist(Artist artist) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseDescription.Artists.COLUMN_NAME,             artist.getName());
        cv.put(DatabaseDescription.Artists.COLUMN_URL,              artist.getUrl());
        cv.put(DatabaseDescription.Artists.COLUMN_FACEBOOK_URL,     artist.getFacebookPageUrl());
        cv.put(DatabaseDescription.Artists.COLUMN_EVENTS_COUNT,     artist.getUpcomingEventCount());
        cv.put(DatabaseDescription.Artists.COLUMN_TRACKER_COUNT,    artist.getTrackerCount());

        if (isContains(artist.getName())) {
            mDatabase.update(Artists.TABLE_NAME, cv,
                    Artists.COLUMN_NAME + " = ? ", new String[] {artist.getName()});
        } else {
            mDatabase.insert(Artists.TABLE_NAME, null, cv);
        }
    }

    private boolean isContains(String name) {
        Cursor cursor = mDatabase.query(Artists.TABLE_NAME, new String[] {Artists.COLUMN_NAME},
                Artists.COLUMN_NAME + " = ? ", new String[] {name}, null, null, null);
        int size = cursor.getCount();
        cursor.close();
        return size != 0;
    }

    public Artist getArtist(String name) {
        Cursor cursor = mDatabase.query(Artists.TABLE_NAME, new String[] {" * "},
                Artists.COLUMN_NAME + " = ? ", new String[] {name}, null, null, null);

        cursor.moveToNext();
        Artist artist = new Artist(
                cursor.getString(cursor.getColumnIndex(Artists.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Artists.COLUMN_URL)),
                cursor.getString(cursor.getColumnIndex(Artists.COLUMN_FACEBOOK_URL)),
                cursor.getInt(cursor.getColumnIndex(Artists.COLUMN_TRACKER_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Artists.COLUMN_EVENTS_COUNT))
        );
        cursor.close();
        return artist;
    }

    public void putEvents(String name, List<Event> events) {
        mDatabase.delete(Events.TABLE_NAME, Events.COLUMN_ARTIST + " = ? ", new String[] {name});

        for (Event event : events) {
            ContentValues cv = new ContentValues();
            cv.put(Events.COLUMN_ARTIST, name);
            cv.put(Events.COLUMN_EVENT_URL, event.getUrl());
            cv.put(Events.COLUMN_DATETIME, event.getDatetime());
            cv.put(Events.COLUMN_EVENT_NAME, event.getVenue().getName());
            cv.put(Events.COLUMN_LATITUDE, event.getVenue().getLatitude());
            cv.put(Events.COLUMN_LONGITUDE, event.getVenue().getLongitude());
            cv.put(Events.COLUMN_CITY, event.getVenue().getCity());
            cv.put(Events.COLUMN_COUNTRY, event.getVenue().getCountry());
            if (!event.getOffers().isEmpty()) {
                cv.put(Events.COLUMN_STATUS, event.getOffers().get(0).getStatus());
                cv.put(Events.COLUMN_TICKETS_URL, event.getOffers().get(0).getUrl());
            }
            mDatabase.insert(Events.TABLE_NAME, null, cv);
        }
    }

    public ArrayList<Event> getEvents(String name) {
        ArrayList<Event> events = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                Events.TABLE_NAME, new String[] {" * "},
                Events.COLUMN_ARTIST + " = ? ",
                new String[] {name}, null, null, null);
        while (cursor.moveToNext()) {
            events.add(new Event(
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_EVENT_URL)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_DATETIME)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_EVENT_NAME)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_LONGITUDE)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_CITY)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_COUNTRY)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_TICKETS_URL)),
                    cursor.getString(cursor.getColumnIndex(Events.COLUMN_STATUS))
            ));
        }
        cursor.close();
        return events;
    }


    public void putAvatar(String name, Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] avatar = stream.toByteArray();

        ContentValues cv = new ContentValues();
        cv.put(Artists.COLUMN_AVATAR, avatar);

        mDatabase.update(Artists.TABLE_NAME, cv, Artists.COLUMN_NAME + " = ? ", new String[] {name});
    }

    public byte[] getAvatar(String name) {
        Cursor cursor = mDatabase.query(Artists.TABLE_NAME,
                new String[] {Artists.COLUMN_AVATAR}, Artists.COLUMN_NAME + " = ? ",
                new String[] {name}, null, null, null);
        cursor.moveToNext();
        byte[] image = cursor.getBlob(cursor.getColumnIndex(Artists.COLUMN_AVATAR));
        cursor.close();
        return image;
    }

}
