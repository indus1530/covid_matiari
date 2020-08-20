package edu.aku.hassannaqvi.covid_matiari.contracts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class PersonalContract {

    public static String CONTENT_AUTHORITY = "edu.aku.hassannaqvi.covid_sero";

    public static abstract class PersonalTable implements BaseColumns {
        public static final String TABLE_NAME = "personals";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_A01 = "a01";
        public static final String COLUMN_A02 = "a02";
        public static final String COLUMN_A03 = "a03";
        public static final String COLUMN_HH12 = "hh12";
        public static final String COLUMN_HH13 = "hh13";
        public static final String COLUMN_CSTATUS = "cstatus";
        public static final String COLUMN_CSTATUS96x = "cstatus96x";
        public static final String COLUMN_ENDINGDATETIME = "endingdatetime";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "tagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_SA = "sA";    // personal member
        public static final String COLUMN_SB = "sB";    // personal member
        public static final String COLUMN_SC = "sC";    // personal member
        public static final String COLUMN_SI = "sI";    // personal member
        public static String PATH = "personals";
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)
                .buildUpon().appendPath(PATH).build();
        public static String SERVER_URL = "sync.php";


        public static String getMovieKeyFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
