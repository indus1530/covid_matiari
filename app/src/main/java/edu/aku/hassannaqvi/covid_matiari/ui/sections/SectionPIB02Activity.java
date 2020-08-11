package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionPib02Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.JSONUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionPIB02Activity extends AppCompatActivity {

    ActivitySectionPib02Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pib02);
        bi.setCallback(this);
        setupSkips();


    }


    private void setupSkips() {

        bi.pb1101.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb1101b.getId()) {
                bi.fldGrpCVpb12.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVpb12);
            } else {
                bi.fldGrpCVpb12.setVisibility(View.VISIBLE);
            }
        }));

        bi.pb1102.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb1102b.getId()) {
                bi.fldGrpCVpb12.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVpb12);
            } else {
                bi.fldGrpCVpb12.setVisibility(View.VISIBLE);
            }
        }));

        bi.pb1103.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb1103b.getId()) {
                bi.fldGrpCVpb12.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVpb12);
            } else {
                bi.fldGrpCVpb12.setVisibility(View.VISIBLE);
            }
        }));

        bi.pb1104.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb1104b.getId()) {
                bi.fldGrpCVpb12.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpCVpb12);
            } else {
                bi.fldGrpCVpb12.setVisibility(View.VISIBLE);
            }
        }));

        bi.pb13.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == bi.pb132.getId())
                Clear.clearAllFields(bi.fldGrpCVpb14);
        });

        bi.pb15.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb15b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb16);
            }
        }));

        bi.pb17.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb17b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb18);
            }
        }));

        bi.pb18n.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(bi.pb18n.getText())) {
                    int count = Integer.parseInt(bi.pb18n.getText().toString());
                    bi.fldGrpCVpb1801.setVisibility(count < 1 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1802.setVisibility(count < 2 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1803.setVisibility(count < 3 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1804.setVisibility(count < 4 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1805.setVisibility(count < 5 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1806.setVisibility(count < 6 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1807.setVisibility(count < 7 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpb1808.setVisibility(count < 8 ? View.GONE : View.VISIBLE);
                } else {
                    bi.fldGrpCVpb1801.setVisibility(View.GONE);
                    bi.fldGrpCVpb1802.setVisibility(View.GONE);
                    bi.fldGrpCVpb1803.setVisibility(View.GONE);
                    bi.fldGrpCVpb1804.setVisibility(View.GONE);
                    bi.fldGrpCVpb1805.setVisibility(View.GONE);
                    bi.fldGrpCVpb1806.setVisibility(View.GONE);
                    bi.fldGrpCVpb1807.setVisibility(View.GONE);
                    bi.fldGrpCVpb1808.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
             /*   if (!bi.pb18n.getText().toString().equals("")) {


                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 1) {
                        bi.pb1801n.setVisibility(View.GONE);
                    } else {
                        bi.pb1801n.setVisibility(View.VISIBLE);
                    }
                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 2) {
                        bi.pb1802n.setVisibility(View.GONE);
                    } else {
                        bi.pb1802n.setVisibility(View.VISIBLE);
                    }

                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 3) {
                        bi.pb1803n.setVisibility(View.GONE);
                    } else {
                        bi.pb1803n.setVisibility(View.VISIBLE);
                    }

                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 4) {
                        bi.pb1804n.setVisibility(View.GONE);
                    } else {
                        bi.pb1804n.setVisibility(View.VISIBLE);
                    }
                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 5) {
                        bi.pb1805n.setVisibility(View.GONE);
                    } else {
                        bi.pb1805n.setVisibility(View.VISIBLE);
                    }
                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 6) {
                        bi.pb1806n.setVisibility(View.GONE);
                    } else {
                        bi.pb1806n.setVisibility(View.VISIBLE);
                    }
                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 7) {
                        bi.pb1807n.setVisibility(View.GONE);
                    } else {
                        bi.pb1807n.setVisibility(View.VISIBLE);
                    }
                    if (Integer.valueOf(bi.pb18n.getText().toString()) < 8) {
                        bi.pb1808n.setVisibility(View.GONE);
                    } else {
                        bi.pb1808n.setVisibility(View.VISIBLE);
                    }
                } else {
                    bi.pb1801n.setVisibility(View.GONE);
                    bi.pb1802n.setVisibility(View.GONE);
                    bi.pb1803n.setVisibility(View.GONE);
                    bi.pb1804n.setVisibility(View.GONE);
                    bi.pb1805n.setVisibility(View.GONE);
                    bi.pb1806n.setVisibility(View.GONE);
                    bi.pb1807n.setVisibility(View.GONE);
                    bi.pb1808n.setVisibility(View.GONE);
                }*/
            }

        });

        //Skip on age
        int age = personal.getHhModel().getMemAge();
        if (age <= 5) {
            bi.fldGrpCVpb11text.setVisibility(View.GONE);
            bi.fldGrpCVpb12.setVisibility(View.GONE);
        }

    }


    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, SectionPICActivity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_SB, MainApp.personal.getsB());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();


        json.put("pb06", bi.pb0601.isChecked() ? "1"
                : bi.pb0602.isChecked() ? "2"
                : "-1");

        json.put("pb06a", bi.pb06a01.isChecked() ? "1"
                : bi.pb06a02.isChecked() ? "2"
                : bi.pb06a03.isChecked() ? "3"
                : bi.pb06a04.isChecked() ? "4"
                : bi.pb06a05.isChecked() ? "5"
                : bi.pb06a06.isChecked() ? "6"
                : "-1");

        json.put("pb06b01", bi.pb06b01.isChecked() ? "1" : "-1");
        json.put("pb06b02", bi.pb06b02.isChecked() ? "2" : "-1");
        json.put("pb06b03", bi.pb06b03.isChecked() ? "3" : "-1");
        json.put("pb06b04", bi.pb06b04.isChecked() ? "4" : "-1");
        json.put("pb06b05", bi.pb06b05.isChecked() ? "5" : "-1");
        json.put("pb06b06", bi.pb06b06.isChecked() ? "6" : "-1");
        json.put("pb06b07", bi.pb06b07.isChecked() ? "7" : "-1");
        json.put("pb06b08", bi.pb06b08.isChecked() ? "8" : "-1");
        json.put("pb06b09", bi.pb06b09.isChecked() ? "9" : "-1");
        json.put("pb06b10", bi.pb06b10.isChecked() ? "10" : "-1");
        json.put("pb06b11", bi.pb06b11.isChecked() ? "11" : "-1");
        json.put("pb06b12", bi.pb06b12.isChecked() ? "12" : "-1");
        json.put("pb06b13", bi.pb06b13.isChecked() ? "13" : "-1");
        json.put("pb06b14", bi.pb06b14.isChecked() ? "14" : "-1");
        json.put("pb06b15", bi.pb06b15.isChecked() ? "15" : "-1");

        json.put("pb06c", bi.pb06c01.isChecked() ? "1"
                : bi.pb06c02.isChecked() ? "2"
                : "-1");

        json.put("pb06d", bi.pb06d01.isChecked() ? "1"
                : bi.pb06d02.isChecked() ? "2"
                : bi.pb06d03.isChecked() ? "3"
                : bi.pb06d04.isChecked() ? "4"
                : bi.pb06d05.isChecked() ? "5"
                : bi.pb06d06.isChecked() ? "6"
                : bi.pb06d07.isChecked() ? "7"
                : "-1");

        json.put("pb06e", bi.pb06e01.isChecked() ? "1"
                : bi.pb06e02.isChecked() ? "2"
                : "-1");

        json.put("pb06f", bi.pb06f01.isChecked() ? "1"
                : bi.pb06f02.isChecked() ? "2"
                : bi.pb06f03.isChecked() ? "3"
                : bi.pb06f04.isChecked() ? "4"
                : bi.pb06f05.isChecked() ? "5"
                : bi.pb06f06.isChecked() ? "6"
                : bi.pb06f07.isChecked() ? "7"
                : "-1");

        json.put("pb06g", bi.pb06g01.isChecked() ? "1"
                : bi.pb06g02.isChecked() ? "2"
                : bi.pb06g03.isChecked() ? "3"
                : "-1");

        json.put("pb06h", bi.pb06h01.isChecked() ? "1"
                : bi.pb06h02.isChecked() ? "2"
                : bi.pb06h03.isChecked() ? "3"
                : bi.pb06h04.isChecked() ? "4"
                : bi.pb06h05.isChecked() ? "5"
                : bi.pb06h06.isChecked() ? "6"
                : bi.pb06h07.isChecked() ? "7"
                : "-1");

        json.put("pb07", bi.pb0701.isChecked() ? "1"
                : bi.pb0702.isChecked() ? "2"
                : "-1");

        json.put("pb08", bi.pb0801.isChecked() ? "1"
                : bi.pb0802.isChecked() ? "2"
                : "-1");

        json.put("pb09", bi.pb0901.isChecked() ? "1"
                : bi.pb0902.isChecked() ? "2"
                : "-1");

        json.put("pb10", bi.pb10.getText().toString());


        json.put("pb1101", bi.pb1101a.isChecked() ? "1"
                : bi.pb1101b.isChecked() ? "2"
                : "-1");

        json.put("pb1102", bi.pb1102a.isChecked() ? "1"
                : bi.pb1102b.isChecked() ? "2"
                : "-1");

        json.put("pb1103", bi.pb1103a.isChecked() ? "1"
                : bi.pb1103b.isChecked() ? "2"
                : "-1");

        json.put("pb1104", bi.pb1104a.isChecked() ? "1"
                : bi.pb1104b.isChecked() ? "2"
                : "-1");

        json.put("pb12", bi.pb12.getText().toString());

        json.put("pb13", bi.pb131.isChecked() ? "1"
                : bi.pb132.isChecked() ? "2"
                : "-1");

        json.put("pb14dist", bi.pb14dist.getText().toString());
        json.put("pb14city", bi.pb14city.getText().toString());
        json.put("pb14country", bi.pb14country.getText().toString());

        json.put("pb15", bi.pb15a.isChecked() ? "1"
                : bi.pb15b.isChecked() ? "2"
                : "-1");

        json.put("pb16", bi.pb16.getText().toString());

        json.put("pb17", bi.pb17a.isChecked() ? "1"
                : bi.pb17b.isChecked() ? "2"
                : "-1");

        json.put("pb18n", bi.pb18n.getText().toString());
        json.put("pb1801n", bi.pb1801n.getText().toString());
        json.put("pb1801ad", bi.pb1801ad.getText().toString());

        json.put("pb1802n", bi.pb1802n.getText().toString());
        json.put("pb1802ad", bi.pb1802ad.getText().toString());

        json.put("pb1803n", bi.pb1803n.getText().toString());
        json.put("pb1803ad", bi.pb1803ad.getText().toString());

        json.put("pb1804n", bi.pb1804n.getText().toString());
        json.put("pb1804ad", bi.pb1804ad.getText().toString());

        json.put("pb1805n", bi.pb1805n.getText().toString());
        json.put("pb1805ad", bi.pb1805ad.getText().toString());

        json.put("pb1806n", bi.pb1806n.getText().toString());
        json.put("pb1806ad", bi.pb1806ad.getText().toString());

        json.put("pb1807n", bi.pb1807n.getText().toString());
        json.put("pb1807ad", bi.pb1807ad.getText().toString());

        json.put("pb1808n", bi.pb1808n.getText().toString());
        json.put("pb1808ad", bi.pb1808ad.getText().toString());


        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(personal.getsB()), json);

            personal.setsB(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, PIEndingActivity.class);
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionB);

    }


    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}