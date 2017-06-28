package com.artiomlevchuk.yourbandsintown.data;

import android.provider.BaseColumns;

public class DatabaseDescription {

    public static final class Artists implements BaseColumns {
        public final static String TABLE_NAME = "Artists";

        final static String _ID = BaseColumns._ID;
        final static String COLUMN_NAME = "name";
        final static String COLUMN_URL = "url";
        final static String COLUMN_FACEBOOK_URL = "facebook_url";
        final static String COLUMN_TRACKER_COUNT = "trackers_count";
        final static String COLUMN_EVENTS_COUNT = "events_count";
        final static String COLUMN_AVATAR = "avatar";
    }

    static final class Events implements BaseColumns {
        final static String TABLE_NAME = "Events";

        final static String _ID = BaseColumns._ID;
        final static String COLUMN_ARTIST = "artist";
        final static String COLUMN_EVENT_URL = "event_url";
        final static String COLUMN_DATETIME = "datetime";
        final static String COLUMN_EVENT_NAME = "event_name";
        final static String COLUMN_LATITUDE = "latitude";
        final static String COLUMN_LONGITUDE = "longitude";
        final static String COLUMN_CITY = "city";
        final static String COLUMN_COUNTRY = "country";
        final static String COLUMN_STATUS = "status";
        final static String COLUMN_TICKETS_URL = "tickets_url";
    }

}
