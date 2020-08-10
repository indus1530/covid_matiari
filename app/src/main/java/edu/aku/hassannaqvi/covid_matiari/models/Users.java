package edu.aku.hassannaqvi.covid_matiari.models;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.contracts.UsersContract.UsersTable;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class Users {

    private static final String TAG = "Users_CONTRACT";
    Long id;
    String username;
    String password;
    String distId;
    String fullname;
//    String REGION_DSS;

    public Users() {
        // Default Constructor
    }


    public Long getUserID() {
        return this.id;
    }

    public void setId(int id) {
        this.id = (long) id;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Users Sync(JSONObject jsonObject) throws JSONException {
        this.username = jsonObject.getString(UsersTable.COLUMN_USERNAME);
        this.password = jsonObject.getString(UsersTable.COLUMN_PASSWORD);
        this.distId = jsonObject.getString(UsersTable.DIST_ID);
        this.fullname = jsonObject.getString(UsersTable.FULL_NAME);
        return this;

    }

    public Users Hydrate(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(UsersTable._ID));
        this.username = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME));
        this.password = cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_PASSWORD));
        this.distId = cursor.getString(cursor.getColumnIndex(UsersTable.DIST_ID));
        this.fullname = cursor.getString(cursor.getColumnIndex(UsersTable.FULL_NAME));
        return this;
    }

}