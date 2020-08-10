package edu.aku.hassannaqvi.covid_matiari.models;


import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.contracts.RandomContract.RandomTable;

public class Random {

    private static final String TAG = "Random_CONTRACT";
    String dist_id;
    String dist_name;
    String sub_dist_name;
    String hhno;
    String cluster;

    public Random() {
        // Default Constructor
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public String getSub_dist_name() {
        return sub_dist_name;
    }

    public void setSub_dist_name(String sub_dist_name) {
        this.sub_dist_name = sub_dist_name;
    }

    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();
        try {
            json.put(RandomTable.COLUMN_DIST_ID, this.dist_id == null ? JSONObject.NULL : this.dist_id);
            json.put(RandomTable.COLUMN_DIST_NAME, this.dist_name == null ? JSONObject.NULL : this.dist_name);
            json.put(RandomTable.COLUMN_SUB_DIST_NAME, this.sub_dist_name == null ? JSONObject.NULL : this.sub_dist_name);
            json.put(RandomTable.COLUMN_CLUSTER, this.cluster == null ? JSONObject.NULL : this.cluster);
            json.put(RandomTable.COLUMN_HHNO, this.hhno == null ? JSONObject.NULL : this.hhno);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Random Sync(JSONObject jsonObject) throws JSONException {
        this.dist_id = jsonObject.getString(RandomTable.COLUMN_DIST_ID);
        this.dist_name = jsonObject.getString(RandomTable.COLUMN_DIST_NAME);
        this.sub_dist_name = jsonObject.getString(RandomTable.COLUMN_SUB_DIST_NAME);
        this.hhno = jsonObject.getString(RandomTable.COLUMN_HHNO);
        this.cluster = jsonObject.getString(RandomTable.COLUMN_CLUSTER);
        return this;
    }

    public Random Hydrate(Cursor cursor) {
        this.dist_id = cursor.getString(cursor.getColumnIndex(RandomTable.COLUMN_DIST_ID));
        this.dist_name = cursor.getString(cursor.getColumnIndex(RandomTable.COLUMN_DIST_NAME));
        this.sub_dist_name = cursor.getString(cursor.getColumnIndex(RandomTable.COLUMN_SUB_DIST_NAME));
        this.hhno = cursor.getString(cursor.getColumnIndex(RandomTable.COLUMN_HHNO));
        this.cluster = cursor.getString(cursor.getColumnIndex(RandomTable.COLUMN_CLUSTER));
        return this;
    }
}