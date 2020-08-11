package edu.aku.hassannaqvi.covid_matiari.models;

import android.database.Cursor;

import androidx.lifecycle.LiveData;

import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract.FormsTable;

/**
 * Created by hassan.naqvi on 11/30/2016.
 */

public class Form extends LiveData<Form> {

    private String _ID = "";
    private String _UID = "";
    private String sysdate = "";
    private String hh01 = ""; // Date
    private String hh02 = ""; // Time
    private String hh03 = ""; // Interviewer
    private String hh12 = ""; // Cluster
    private String hh13 = ""; // HHNO
    private String refno = ""; // Reference Number
    private String istatus = ""; // Interview Status
    private String istatus96x = ""; // Interview Status
    private String endingdatetime = "";
    private String gpsLat = "";
    private String gpsLng = "";
    private String gpsDT = "";
    private String gpsAcc = "";
    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion = "";
    private String sInfo = "";
    private String sH2 = "";
    private String sH3 = "";
    private String sH4 = "";
    private String sH5 = "";

    //Date Settings
    private LocalDate localDate = null, calculatedDOB = null;

    public Form() {
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }


    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }


    public LocalDate getCalculatedDOB() {
        return calculatedDOB;
    }

    public void setCalculatedDOB(LocalDate calculatedDOB) {
        this.calculatedDOB = calculatedDOB;
    }


    public String getHh12() {
        return hh12;
    }

    public void setHh12(String hh12) {
        this.hh12 = hh12;
    }

    public String getsInfo() {
        return sInfo;
    }

    public void setsInfo(String sInfo) {
        this.sInfo = sInfo;
    }

    public String getHh13() {
        return hh13;
    }

    public void setHh13(String hh13) {
        this.hh13 = hh13;
    }

    public String getsH2() {
        return sH2;
    }

    public void setsH2(String sH2) {
        this.sH2 = sH2;
    }

    public String getsH3() {
        return sH3;
    }

    public void setsH3(String sH3) {
        this.sH3 = sH3;
    }

    public String getsH4() {
        return sH4;
    }

    public void setsH4(String sH4) {
        this.sH4 = sH4;
    }

    public String getsH5() {
        return sH5;
    }

    public void setsH5(String sH5) {
        this.sH5 = sH5;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getProjectName() {
        return "covid_sero";
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String get_UID() {
        return _UID;
    }

    public void set_UID(String _UID) {
        this._UID = _UID;
    }

    public String getHh01() {
        return hh01;
    }

    public void setHh01(String hh01) {
        this.hh01 = hh01;
    }

    public String getHh02() {
        return hh02;
    }

    public void setHh02(String hh02) {
        this.hh02 = hh02;
    }

    public String getHh03() {
        return hh03;
    }

    public void setHh03(String hh03) {
        this.hh03 = hh03;
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }


    public String getIstatus96x() {
        return istatus96x;
    }

    public void setIstatus96x(String istatus96x) {
        this.istatus96x = istatus96x;
    }


    public String getEndingdatetime() {
        return endingdatetime;
    }

    public void setEndingdatetime(String endingdatetime) {
        this.endingdatetime = endingdatetime;
    }


    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }


    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }


    public String getGpsDT() {
        return gpsDT;
    }

    public void setGpsDT(String gpsDT) {
        this.gpsDT = gpsDT;
    }


    public String getGpsAcc() {
        return gpsAcc;
    }

    public void setGpsAcc(String gpsAcc) {
        this.gpsAcc = gpsAcc;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }


    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }


    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }


    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }


    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }


    public Form Sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(FormsTable.COLUMN_ID);
        this._UID = jsonObject.getString(FormsTable.COLUMN_UID);
        this.sysdate = jsonObject.getString(FormsTable.COLUMN_SYSDATE);
        this.hh01 = jsonObject.getString(FormsTable.COLUMN_HH01);
        this.hh02 = jsonObject.getString(FormsTable.COLUMN_HH02);
        this.hh03 = jsonObject.getString(FormsTable.COLUMN_HH03);
        this.hh12 = jsonObject.getString(FormsTable.COLUMN_HH12);
        this.hh13 = jsonObject.getString(FormsTable.COLUMN_HH13);
        this.refno = jsonObject.getString(FormsTable.COLUMN_REFNO);
        this.istatus = jsonObject.getString(FormsTable.COLUMN_ISTATUS);
        this.istatus96x = jsonObject.getString(FormsTable.COLUMN_ISTATUS96x);
        this.endingdatetime = jsonObject.getString(FormsTable.COLUMN_ENDINGDATETIME);
        this.gpsLat = jsonObject.getString(FormsTable.COLUMN_GPSLAT);
        this.gpsLng = jsonObject.getString(FormsTable.COLUMN_GPSLNG);
        this.gpsDT = jsonObject.getString(FormsTable.COLUMN_GPSDATE);
        this.gpsAcc = jsonObject.getString(FormsTable.COLUMN_GPSACC);
        this.deviceID = jsonObject.getString(FormsTable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(FormsTable.COLUMN_DEVICETAGID);
        this.synced = jsonObject.getString(FormsTable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(FormsTable.COLUMN_SYNCED_DATE);
        this.appversion = jsonObject.getString(FormsTable.COLUMN_SYNCED_DATE);
        this.sInfo = jsonObject.getString(FormsContract.FormsTable.COLUMN_SINFO);
        this.sH2 = jsonObject.getString(FormsTable.COLUMN_SH2);
        this.sH3 = jsonObject.getString(FormsTable.COLUMN_SH3);
        this.sH4 = jsonObject.getString(FormsTable.COLUMN_SH4);
        this.sH5 = jsonObject.getString(FormsTable.COLUMN_SH5);

        return this;
    }

    public Form Hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ID));
        this._UID = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_UID));
        this.sysdate = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SYSDATE));
        this.hh01 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HH01));
        this.hh02 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HH02));
        this.hh03 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HH03));
        this.hh12 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HH12));
        this.hh13 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_HH13));
        this.refno = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_REFNO));
        this.istatus = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ISTATUS));
        this.istatus96x = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ISTATUS96x));
        this.endingdatetime = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_ENDINGDATETIME));
        this.gpsLat = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_GPSACC));
        this.deviceID = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_DEVICETAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_APPVERSION));
        this.sInfo = cursor.getString(cursor.getColumnIndex(FormsContract.FormsTable.COLUMN_SINFO));
        this.sH2 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SH2));
        this.sH3 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SH3));
        this.sH4 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SH4));
        this.sH5 = cursor.getString(cursor.getColumnIndex(FormsTable.COLUMN_SH5));

        return this;
    }

    //TODO: Try this instead of toJSONObject
    @NotNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Form.class);
    }

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();

        try {
            json.put(FormsTable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
            json.put(FormsTable.COLUMN_SYSDATE, this.sysdate == null ? JSONObject.NULL : this.sysdate);
            json.put(FormsTable.COLUMN_UID, this._UID == null ? JSONObject.NULL : this._UID);
            json.put(FormsTable.COLUMN_HH01, this.hh01 == null ? JSONObject.NULL : this.hh01);
            json.put(FormsTable.COLUMN_HH02, this.hh02 == null ? JSONObject.NULL : this.hh02);
            json.put(FormsTable.COLUMN_HH03, this.hh03 == null ? JSONObject.NULL : this.hh03);
            json.put(FormsTable.COLUMN_HH12, this.hh12 == null ? JSONObject.NULL : this.hh12);
            json.put(FormsTable.COLUMN_HH13, this.hh13 == null ? JSONObject.NULL : this.hh13);
            json.put(FormsTable.COLUMN_REFNO, this.refno == null ? JSONObject.NULL : this.refno);
            json.put(FormsTable.COLUMN_ISTATUS, this.istatus == null ? JSONObject.NULL : this.istatus);
            json.put(FormsTable.COLUMN_ISTATUS96x, this.istatus96x == null ? JSONObject.NULL : this.istatus96x);
            json.put(FormsTable.COLUMN_ENDINGDATETIME, this.endingdatetime == null ? JSONObject.NULL : this.endingdatetime);

            if (this.sInfo != null && !this.sInfo.equals("")) {
                json.put(FormsTable.COLUMN_SINFO, new JSONObject(this.sInfo));
            }
            if (this.sH2 != null && !this.sH2.equals("")) {
                json.put(FormsTable.COLUMN_SH2, new JSONObject(this.sH2));
            }
            if (this.sH3 != null && !this.sH3.equals("")) {
                json.put(FormsTable.COLUMN_SH3, new JSONObject(this.sH3));
            }
            if (this.sH4 != null && !this.sH4.equals("")) {
                json.put(FormsTable.COLUMN_SH4, new JSONObject(this.sH4));
            }
            if (this.sH5 != null && !this.sH5.equals("")) {
                json.put(FormsTable.COLUMN_SH5, new JSONObject(this.sH5));
            }

            json.put(FormsTable.COLUMN_GPSLAT, this.gpsLat == null ? JSONObject.NULL : this.gpsLat);
            json.put(FormsTable.COLUMN_GPSLNG, this.gpsLng == null ? JSONObject.NULL : this.gpsLng);
            json.put(FormsTable.COLUMN_GPSDATE, this.gpsDT == null ? JSONObject.NULL : this.gpsDT);
            json.put(FormsTable.COLUMN_GPSACC, this.gpsAcc == null ? JSONObject.NULL : this.gpsAcc);
            json.put(FormsTable.COLUMN_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
            json.put(FormsTable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
            json.put(FormsTable.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);

            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
