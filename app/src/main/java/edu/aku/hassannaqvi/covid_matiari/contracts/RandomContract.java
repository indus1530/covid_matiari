package edu.aku.hassannaqvi.covid_matiari.contracts;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class RandomContract {

    private static final String TAG = "Random_CONTRACT";

    public static String CONTENT_AUTHORITY = "edu.aku.hassannaqvi.covid_matiari";

    public static abstract class RandomTable implements BaseColumns {

        public static final String TABLE_NAME = "bl_random";

        public static final String _ID = "_id";
        public static final String COLUMN_DIST_ID = "dist_id";
        public static final String COLUMN_DIST_NAME = "dist_name";
        public static final String COLUMN_SUB_DIST_NAME = "sub_dist_name";
        public static final String COLUMN_HHNO = "hhno";
        public static final String COLUMN_CLUSTER = "cluster";

        public static final String SERVER_URI = "ucs.php";

        public static String PATH = "ucs";

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;
        public static Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY)
                .buildUpon().appendPath(PATH).build();

        public static String getMovieKeyFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildUriWithId(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}