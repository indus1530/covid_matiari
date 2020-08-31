package edu.aku.hassannaqvi.covid_matiari.ui.other;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.covid_matiari.CONSTANTS;
import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.adapter.SyncListAdapter;
import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySyncBinding;
import edu.aku.hassannaqvi.covid_matiari.models.Form;
import edu.aku.hassannaqvi.covid_matiari.models.SyncModel;
import edu.aku.hassannaqvi.covid_matiari.sync.GetAllData;
import edu.aku.hassannaqvi.covid_matiari.sync.SyncAllData;
import edu.aku.hassannaqvi.covid_matiari.sync.SyncAllPhotos;
import edu.aku.hassannaqvi.covid_matiari.sync.SyncDevice;

import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.DATABASE_NAME;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.DB_NAME;
import static edu.aku.hassannaqvi.covid_matiari.utils.db_utils.CreateTable.PROJECT_NAME;

public class SyncActivity extends AppCompatActivity implements SyncDevice.SyncDevicInterface {
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    String DirectoryName;
    DatabaseHelper db;
    SyncListAdapter syncListAdapter;
    ActivitySyncBinding bi;
    SyncModel model;
    SyncModel uploadmodel;
    List<SyncModel> list;
    List<SyncModel> uploadlist;
    Boolean listActivityCreated;
    Boolean uploadlistActivityCreated;
    String dtToday = new SimpleDateFormat("dd-MM-yy HH:mm").format(new Date().getTime());
    private boolean sync_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_sync);
        bi.setCallback(this);
        list = new ArrayList<>();
        uploadlist = new ArrayList<>();
        //bi.noItem.setVisibility(View.VISIBLE);
        bi.noDataItem.setVisibility(View.VISIBLE);
        listActivityCreated = true;
        uploadlistActivityCreated = true;
        sharedPref = getSharedPreferences("src", MODE_PRIVATE);
        editor = sharedPref.edit();
        db = MainApp.appInfo.getDbHelper();
        dbBackup();

        sync_flag = getIntent().getBooleanExtra(CONSTANTS.SYNC_LOGIN, false);

        bi.btnSync.setOnClickListener(v -> onSyncDataClick());
        bi.btnUpload.setOnClickListener(v -> syncServer());
        // setAdapter();
        setUploadAdapter();
    }

    public void onSyncDataClick() {

        bi.activityTitle.setText("Download Data");
        // Require permissions INTERNET & ACCESS_NETWORK_STATE
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            /*if (sync_flag) new SyncData(SyncActivity.this, MainApp.DIST_ID).execute(true);
            else new SyncDevice(SyncActivity.this, true).execute();*/
            new SyncData(SyncActivity.this, MainApp.DIST_ID).execute(true);
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    void setUploadAdapter() {
        syncListAdapter = new SyncListAdapter(uploadlist);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        bi.rvUploadList.setLayoutManager(mLayoutManager2);
        bi.rvUploadList.setItemAnimator(new DefaultItemAnimator());
        bi.rvUploadList.setAdapter(syncListAdapter);
        syncListAdapter.notifyDataSetChanged();
        if (syncListAdapter.getItemCount() > 0) {
            bi.noDataItem.setVisibility(View.GONE);
        } else {
            bi.noDataItem.setVisibility(View.VISIBLE);
        }
    }

    public void syncServer() {
        bi.activityTitle.setText("Upload Data");

//        if(true) return;

        // Require permissions INTERNET & ACCESS_NETWORK_STATE
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new SyncDevice(this, false).execute();
//  *******************************************************Forms*********************************
            Toast.makeText(getApplicationContext(), "Syncing Forms", Toast.LENGTH_SHORT).show();
            if (uploadlistActivityCreated) {
                uploadmodel = new SyncModel();
                uploadmodel.setstatusID(0);
                uploadlist.add(uploadmodel);
            }
            new SyncAllData(
                    this,
                    "Forms",
                    "updateSyncedForms",
                    Form.class,
                    MainApp._HOST_URL + MainApp._SERVER_URL,
                    FormsContract.FormsTable.TABLE_NAME,
                    db.getUnsyncedForms(), 0, syncListAdapter, uploadlist
            ).execute();

//  *******************************************************Personal*********************************
            Toast.makeText(getApplicationContext(), "Syncing Personal", Toast.LENGTH_SHORT).show();
            if (uploadlistActivityCreated) {
                uploadmodel = new SyncModel();
                uploadmodel.setstatusID(0);
                uploadlist.add(uploadmodel);
            }
            new SyncAllData(
                    this,
                    "Personal",
                    "updateSyncedPersonal",
                    Form.class,
                    MainApp._HOST_URL + MainApp._SERVER_URL,
                    PersonalContract.PersonalTable.TABLE_NAME,
                    db.getUnsyncedPersonal(), 1, syncListAdapter, uploadlist
            ).execute();

            bi.noDataItem.setVisibility(View.GONE);

            uploadlistActivityCreated = false;

            SharedPreferences syncPref = getSharedPreferences("SyncInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = syncPref.edit();

            editor.putString("LastDataUpload", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
            editor.apply();

        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }

    public void dbBackup() {


        if (sharedPref.getBoolean("flag", false)) {

            String dt = sharedPref.getString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));

            if (!dt.equals(new SimpleDateFormat("dd-MM-yy").format(new Date()))) {
                editor.putString("dt", new SimpleDateFormat("dd-MM-yy").format(new Date()));
                editor.apply();
            }

            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + PROJECT_NAME);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                DirectoryName = folder.getPath() + File.separator + sharedPref.getString("dt", "");
                folder = new File(DirectoryName);
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }
                if (success) {

                    try {
                        File dbFile = new File(this.getDatabasePath(DATABASE_NAME).getPath());
                        FileInputStream fis = new FileInputStream(dbFile);

                        String outFileName = DirectoryName + File.separator + DB_NAME;

                        // Open the empty db as the output stream
                        OutputStream output = new FileOutputStream(outFileName);

                        // Transfer bytes from the inputfile to the outputfile
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        // Close the streams
                        output.flush();
                        output.close();
                        fis.close();
                    } catch (IOException e) {
                        Log.e("dbBackup:", e.getMessage());
                    }

                }

            } else {
                Toast.makeText(this, "Not create folder", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void processFinish(boolean flag) {
        if (flag) {
            MainApp.appInfo.updateTagName(SyncActivity.this);
            new SyncData(SyncActivity.this, MainApp.DIST_ID).execute(sync_flag);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

    public void uploadPhotos(View view) {

        String fileName = "";
        String appFolder = PROJECT_NAME;

        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        Log.d("Files", "Path: " + sdDir);
        File directory = new File(String.valueOf(sdDir), appFolder);
        Log.d("Directory", "uploadPhotos: " + directory);
        if (directory.exists()) {
            File[] files = directory.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.getPath().endsWith(".jpg") || file.getPath().endsWith(".jpeg"));
                }
            });


            Log.d("Files", "Count: " + files.length);
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    Log.d("Files", "FileName:" + files[i].getName());
                    SyncAllPhotos syncAllPhotos = new SyncAllPhotos(files[i].getName(), this);
                    syncAllPhotos.execute();

                    try {
                        //3000 ms delay to process upload of next file.
                        Thread.sleep(3000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                editor.putString("LastPhotoUpload", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                editor.apply();
            } else {
                Toast.makeText(this, "No photos to upload.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No photos were taken", Toast.LENGTH_SHORT).show();
        }

    }

    private class SyncData extends AsyncTask<Boolean, String, String> {

        private Context mContext;
        private String distID;

        private SyncData(Context mContext, String districtId) {
            this.mContext = mContext;
            this.distID = districtId;
        }

        @Override
        protected String doInBackground(Boolean... booleans) {
            runOnUiThread(() -> {

                new SyncDevice(mContext, false).execute();

                if (booleans[0]) {
//                  getting Users!!
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "User", syncListAdapter, list).execute();

//                    Getting App Version
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "VersionApp", syncListAdapter, list).execute();

//                    Getting App Random
                    if (listActivityCreated) {
                        model = new SyncModel();
                        model.setstatusID(0);
                        list.add(model);
                    }
                    new GetAllData(mContext, "Random", syncListAdapter, list).execute();

                }

                listActivityCreated = false;
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(() -> {
                editor.putString("LastDataDownload", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
                editor.apply();
                editor.putBoolean("flag", true);
                editor.commit();

                dbBackup();

            }, 1200);
        }
    }
}
