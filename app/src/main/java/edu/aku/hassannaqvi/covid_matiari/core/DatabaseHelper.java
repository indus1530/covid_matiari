package edu.aku.hassannaqvi.covid_matiari.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract.PersonalTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.RandomContract.RandomTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.UsersContract.UsersTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.VersionAppContract;
import edu.aku.hassannaqvi.covid_matiari.contracts.VersionAppContract.VersionAppTable;
import edu.aku.hassannaqvi.covid_matiari.models.Form;
import edu.aku.hassannaqvi.covid_matiari.models.Personal;
import edu.aku.hassannaqvi.covid_matiari.models.Random;
import edu.aku.hassannaqvi.covid_matiari.models.Users;
import edu.aku.hassannaqvi.covid_matiari.models.VersionApp;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.DATABASE_VERSION;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.SQL_CREATE_FORMS;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.SQL_CREATE_PERSONALS;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.SQL_CREATE_RANDOM;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.SQL_CREATE_VERSIONAPP;


/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_FORMS);
        db.execSQL(SQL_CREATE_PERSONALS);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_RANDOM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Integer syncVersionApp(JSONObject VersionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionAppContract.VersionAppTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionApp Vc = new VersionApp();
            Vc.Sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                Users user = new Users();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
                values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
                values.put(UsersTable.DIST_ID, user.getDistId());
                values.put(UsersTable.FULL_NAME, user.getFullname());
                long rowID = db.insert(UsersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncRandom(JSONArray randomList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(RandomTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < randomList.length(); i++) {

                JSONObject jsonObjectRandom = randomList.getJSONObject(i);

                Random random = new Random();
                random.Sync(jsonObjectRandom);
                ContentValues values = new ContentValues();

                values.put(RandomTable.COLUMN_CLUSTER, random.getCluster());
                values.put(RandomTable.COLUMN_DIST_ID, random.getDist_id());
                values.put(RandomTable.COLUMN_DIST_NAME, random.getDist_name());
                values.put(RandomTable.COLUMN_SUB_DIST_NAME, random.getSub_dist_name());
                values.put(RandomTable.COLUMN_HHNO, random.getHhno());
                long rowID = db.insert(RandomTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }


    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME + " WHERE " + UsersTable.COLUMN_USERNAME + "=? AND " + UsersTable.COLUMN_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {

                while (mCursor.moveToFirst()) {
//                    MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(Users.UsersTable.ROW_USERNAME));
                    MainApp.user = new Users().Hydrate(mCursor);
                }
                return true;
            }
        }
        return false;
    }

    public Long addPersonal(Personal personal) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PersonalTable.COLUMN_PROJECT_NAME, personal.getProjectName());
        values.put(PersonalTable.COLUMN_UID, personal.get_UID());
        values.put(PersonalTable.COLUMN_SYSDATE, personal.getSysdate());
        values.put(PersonalTable.COLUMN_A01, personal.getA01());
        values.put(PersonalTable.COLUMN_A02, personal.getA02());
        values.put(PersonalTable.COLUMN_A03, personal.getA03());
        values.put(PersonalTable.COLUMN_HH12, personal.getHh12());
        values.put(PersonalTable.COLUMN_HH13, personal.getHh13());
        values.put(PersonalTable.COLUMN_UUID, personal.get_UUID());
        values.put(PersonalTable.COLUMN_CSTATUS, personal.getCstatus());
        values.put(PersonalTable.COLUMN_CSTATUS96x, personal.getCstatus96x());
        values.put(PersonalTable.COLUMN_ENDINGDATETIME, personal.getEndingdatetime());
        values.put(PersonalTable.COLUMN_SA, personal.getsA());
        values.put(PersonalTable.COLUMN_SB, personal.getsB());
        values.put(PersonalTable.COLUMN_SC, personal.getsC());
        values.put(PersonalTable.COLUMN_SI, personal.getsI());
        values.put(PersonalTable.COLUMN_GPSLAT, personal.getGpsLat());
        values.put(PersonalTable.COLUMN_GPSLNG, personal.getGpsLng());
        values.put(PersonalTable.COLUMN_GPSDATE, personal.getGpsDT());
        values.put(PersonalTable.COLUMN_GPSACC, personal.getGpsAcc());
        values.put(PersonalTable.COLUMN_DEVICETAGID, personal.getDevicetagID());
        values.put(PersonalTable.COLUMN_DEVICEID, personal.getDeviceID());
        values.put(PersonalTable.COLUMN_APPVERSION, personal.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                PersonalTable.TABLE_NAME,
                PersonalTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addForm(Form form) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, form.getProjectName());
        values.put(FormsTable.COLUMN_UID, form.get_UID());
        values.put(FormsTable.COLUMN_SYSDATE, form.getSysdate());
        values.put(FormsTable.COLUMN_HH01, form.getHh01());
        values.put(FormsTable.COLUMN_HH02, form.getHh02());
        values.put(FormsTable.COLUMN_HH03, form.getHh03());
        values.put(FormsTable.COLUMN_HH12, form.getHh12());
        values.put(FormsTable.COLUMN_HH13, form.getHh13());
        values.put(FormsTable.COLUMN_REFNO, form.getRefno());
        values.put(FormsTable.COLUMN_ISTATUS, form.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS96x, form.getIstatus96x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, form.getEndingdatetime());
        values.put(FormsTable.COLUMN_SINFO, form.getsInfo());
        values.put(FormsTable.COLUMN_SH2, form.getsH2());
        values.put(FormsTable.COLUMN_SH3, form.getsH3());
        values.put(FormsTable.COLUMN_SH4, form.getsH4());
        values.put(FormsTable.COLUMN_GPSLAT, form.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, form.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, form.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, form.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, form.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, form.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, form.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public int updateFormID() {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_UID, form.get_UID());

// Which row to update, based on the ID
        String selection = FormsTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(form.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public VersionApp getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionAppTable._ID,
                VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = null;

        VersionApp allVC = new VersionApp();
        try {
            c = db.query(
                    VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public Collection<Form> getAllForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH01,
                FormsTable.COLUMN_HH02,
                FormsTable.COLUMN_HH03,
                FormsTable.COLUMN_HH12,
                FormsTable.COLUMN_HH13,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH2,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<Form>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                allForms.add(form.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Personal> getAllPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalTable._ID,
                PersonalTable.COLUMN_UID,
                PersonalTable.COLUMN_SYSDATE,
                PersonalTable.COLUMN_A01,
                PersonalTable.COLUMN_A02,
                PersonalTable.COLUMN_A03,
                PersonalTable.COLUMN_HH12,
                PersonalTable.COLUMN_HH13,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SB,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_SI,
                PersonalTable.COLUMN_GPSLAT,
                PersonalTable.COLUMN_GPSLNG,
                PersonalTable.COLUMN_GPSDATE,
                PersonalTable.COLUMN_GPSACC,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = PersonalTable.COLUMN_ID + " ASC";
        Collection<Personal> allPersonal = new ArrayList<Personal>();
        try {
            c = db.query(
                    PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Personal personal = new Personal();
                allPersonal.add(personal.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }


    public Collection<Form> checkFormExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH01,
                FormsTable.COLUMN_HH02,
                FormsTable.COLUMN_HH03,
                FormsTable.COLUMN_HH12,
                FormsTable.COLUMN_HH13,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH2,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<Form>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                allForms.add(form.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Personal> checkPersonalExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalTable._ID,
                PersonalTable.COLUMN_UID,
                PersonalTable.COLUMN_SYSDATE,
                PersonalTable.COLUMN_A01,
                PersonalTable.COLUMN_A02,
                PersonalTable.COLUMN_A03,
                PersonalTable.COLUMN_HH12,
                PersonalTable.COLUMN_HH13,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SB,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_SI,
                PersonalTable.COLUMN_GPSLAT,
                PersonalTable.COLUMN_GPSLNG,
                PersonalTable.COLUMN_GPSDATE,
                PersonalTable.COLUMN_GPSACC,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION,

        };
        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy =
                PersonalTable.COLUMN_ID + " ASC";

        Collection<Personal> allPersonal = new ArrayList<Personal>();
        try {
            c = db.query(
                    PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Personal personal = new Personal();
                allPersonal.add(personal.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    public List<Personal> checkAllPersonalExist(String clusterCode, String hhNo, String uuid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalTable._ID,
                PersonalTable.COLUMN_UID,
                PersonalTable.COLUMN_SYSDATE,
                PersonalTable.COLUMN_A01,
                PersonalTable.COLUMN_A02,
                PersonalTable.COLUMN_A03,
                PersonalTable.COLUMN_HH12,
                PersonalTable.COLUMN_HH13,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SB,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_SI,
                PersonalTable.COLUMN_GPSLAT,
                PersonalTable.COLUMN_GPSLNG,
                PersonalTable.COLUMN_GPSDATE,
                PersonalTable.COLUMN_GPSACC,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION,

        };
        String whereClause = PersonalTable.COLUMN_HH12 + "=? AND " + PersonalTable.COLUMN_HH13 + "=? AND " + PersonalTable.COLUMN_UUID + "=? AND (" + PersonalTable.COLUMN_CSTATUS + " is not null OR " + PersonalTable.COLUMN_CSTATUS + " !='')";
        String[] whereArgs = {clusterCode, hhNo, uuid};
        String groupBy = null;
        String having = null;

        String orderBy = PersonalTable.COLUMN_ID + " ASC";

        List<Personal> allPersonal = new ArrayList<>();
        try {
            c = db.query(
                    PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allPersonal.add(new Personal().Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }


    public Collection<Form> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH01,
                FormsTable.COLUMN_HH02,
                FormsTable.COLUMN_HH03,
                FormsTable.COLUMN_HH12,
                FormsTable.COLUMN_HH13,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH2,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
        };


        String whereClause = FormsTable.COLUMN_SYNCED + " is null AND " + FormsTable.COLUMN_ISTATUS + " != '' ";
        //String whereClause = FormsTable.COLUMN_ISTATUS +" != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<Form>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                allForms.add(form.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Personal> getUnsyncedPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalTable._ID,
                PersonalTable.COLUMN_UID,
                PersonalTable.COLUMN_SYSDATE,
                PersonalTable.COLUMN_A01,
                PersonalTable.COLUMN_A02,
                PersonalTable.COLUMN_A03,
                PersonalTable.COLUMN_HH12,
                PersonalTable.COLUMN_HH13,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_CSTATUS96x,
                PersonalTable.COLUMN_ENDINGDATETIME,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SB,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_SI,
                PersonalTable.COLUMN_GPSLAT,
                PersonalTable.COLUMN_GPSLNG,
                PersonalTable.COLUMN_GPSDATE,
                PersonalTable.COLUMN_GPSACC,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION,
        };


        String whereClause = PersonalTable.COLUMN_SYNCED + " is null AND " + PersonalTable.COLUMN_CSTATUS + " != '' ";
        //String whereClause = PersonalTable.COLUMN_ISTATUS +" != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                PersonalTable.COLUMN_ID + " ASC";

        Collection<Personal> allPersonal = new ArrayList<Personal>();
        try {
            c = db.query(
                    PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Personal personal = new Personal();
                allPersonal.add(personal.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    public Collection<Form> getTodayForms(String sysdate) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH12,
                FormsTable.COLUMN_HH13,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                form.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                form.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                form.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                form.setHh12(c.getString(c.getColumnIndex(FormsTable.COLUMN_HH12)));
                form.setHh13(c.getString(c.getColumnIndex(FormsTable.COLUMN_HH13)));
                form.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allForms.add(form);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Collection<Form> getFormsByCluster(String cluster) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH12,
                FormsTable.COLUMN_HH13,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_HH12 + " = ? ";
        String[] whereArgs = new String[]{cluster};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                form.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                form.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                form.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                form.setHh12(c.getString(c.getColumnIndex(FormsTable.COLUMN_HH12)));
                form.setHh13(c.getString(c.getColumnIndex(FormsTable.COLUMN_HH13)));
                form.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allForms.add(form);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public ArrayList<Form> getUnclosedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH01,
                FormsTable.COLUMN_HH02,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_SYNCED,
        };
        String whereClause = FormsTable.COLUMN_ISTATUS + " = ''";
        String[] whereArgs = null;
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";
        ArrayList<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form form = new Form();
                form.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                form.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                form.setSysdate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                form.setHh01(c.getString(c.getColumnIndex(FormsTable.COLUMN_HH01)));
                form.setHh02(c.getString(c.getColumnIndex(FormsTable.COLUMN_HH02)));
                form.setRefno(c.getString(c.getColumnIndex(FormsTable.COLUMN_REFNO)));
                form.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                form.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allForms.add(form);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }


    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, form.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS96x, form.getIstatus96x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, form.getEndingdatetime());

        // Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(form.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateMemberEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(PersonalTable.COLUMN_CSTATUS, personal.getCstatus());
        values.put(PersonalTable.COLUMN_CSTATUS96x, personal.getCstatus96x());
        values.put(PersonalTable.COLUMN_ENDINGDATETIME, personal.getEndingdatetime());

        // Which row to update, based on the ID
        String selection = PersonalTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(personal.get_ID())};

        return db.update(PersonalTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    //Get BLRandom data
    public List<Random> getClusters(String district, String cluster) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                RandomTable._ID,
                RandomTable.COLUMN_HHNO,
                RandomTable.COLUMN_SUB_DIST_NAME,
                RandomTable.COLUMN_DIST_NAME,
                RandomTable.COLUMN_DIST_ID,
                RandomTable.COLUMN_CLUSTER,
        };
        String whereClause = RandomTable.COLUMN_DIST_ID + "=? AND " + RandomTable.COLUMN_CLUSTER + "=?";
        String[] whereArgs = {district, cluster};
        String groupBy = null;
        String having = null;
        String orderBy = RandomTable._ID + " ASC";
        List<Random> allForms = new ArrayList<>();
        try {
            c = db.query(
                    RandomTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allForms.add(new Random().Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    public Random getClusterHH(String district, String cluster, String hhno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                RandomTable._ID,
                RandomTable.COLUMN_HHNO,
                RandomTable.COLUMN_SUB_DIST_NAME,
                RandomTable.COLUMN_DIST_NAME,
                RandomTable.COLUMN_DIST_ID,
                RandomTable.COLUMN_CLUSTER,
        };
        String whereClause = RandomTable.COLUMN_DIST_ID + "=? AND " + RandomTable.COLUMN_CLUSTER + "=? AND " + RandomTable.COLUMN_HHNO + "=?";
        String[] whereArgs = {district, cluster, hhno};
        String groupBy = null;
        String having = null;
        String orderBy = RandomTable._ID + " ASC";
        Random allForms = null;
        try {
            c = db.query(
                    RandomTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allForms = new Random().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    //Get Form already exist
    public Form getFilledForm(String district, String refno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_HH01,
                FormsTable.COLUMN_HH02,
                FormsTable.COLUMN_HH03,
                FormsTable.COLUMN_HH12,
                FormsTable.COLUMN_HH13,
                FormsTable.COLUMN_REFNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS96x,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SH2,
                FormsTable.COLUMN_SH3,
                FormsTable.COLUMN_SH4,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION
        };

//        String whereClause = "(" + FormsTable.COLUMN_ISTATUS + " is null OR " + FormsTable.COLUMN_ISTATUS + "='') AND " + FormsTable.COLUMN_CLUSTERCODE + "=? AND " + FormsTable.COLUMN_HHNO + "=?";
        String whereClause = FormsTable.COLUMN_HH13 + "=? AND " + FormsTable.COLUMN_REFNO + "=?";
        String[] whereArgs = {district, refno};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable._ID + " ASC";
        Form allForms = null;
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allForms = new Form().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }

    //Get Personal already exist
    public Personal getFilledPersonal(String district, String refno) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalTable._ID,
                PersonalTable.COLUMN_UID,
                PersonalTable.COLUMN_SYSDATE,
                PersonalTable.COLUMN_A01,
                PersonalTable.COLUMN_A02,
                PersonalTable.COLUMN_A03,
                PersonalTable.COLUMN_HH12,
                PersonalTable.COLUMN_HH13,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_CSTATUS96x,
                PersonalTable.COLUMN_ENDINGDATETIME,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SB,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_SI,
                PersonalTable.COLUMN_GPSLAT,
                PersonalTable.COLUMN_GPSLNG,
                PersonalTable.COLUMN_GPSDATE,
                PersonalTable.COLUMN_GPSACC,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION
        };

//        String whereClause = "(" + FormsTable.COLUMN_ISTATUS + " is null OR " + FormsTable.COLUMN_ISTATUS + "='') AND " + FormsTable.COLUMN_CLUSTERCODE + "=? AND " + FormsTable.COLUMN_HHNO + "=?";
        String whereClause = PersonalTable.COLUMN_HH13 + "=? AND " + PersonalTable.COLUMN_UUID + "=?";
        String[] whereArgs = {district, refno};
        String groupBy = null;
        String having = null;
        String orderBy = PersonalTable._ID + " ASC";
        Personal allPersonal = null;
        try {
            c = db.query(
                    PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allPersonal = new Personal().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    //Generic update FormColumn
    public int updatesFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(form.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //Generic update PersonalColumn
    public int updatesPersonalColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = PersonalTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(personal.get_ID())};

        return db.update(PersonalTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    // ANDROID DATABASE MANAGER
    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    //Generic Un-Synced Forms
    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormsTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    //Generic Un-Synced Personal
    public void updateSyncedPersonal(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();
        values.put(PersonalTable.COLUMN_SYNCED, true);
        values.put(PersonalTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = PersonalTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                PersonalTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }
}