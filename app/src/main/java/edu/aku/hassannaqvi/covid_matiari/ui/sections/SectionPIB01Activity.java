package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
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
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionPib01Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionPIB01Activity extends AppCompatActivity {

    ActivitySectionPib01Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pib01);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {

        bi.pb0201.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.fldGrpCVpb0202));

        bi.pb03.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0302.getId() || i == bi.pb0304.getId()) {
                bi.fldGrpSectionB01.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpSectionB01);
            } else {
                if (personal.getHhModel().getGenderFemale())
                    bi.fldGrpSectionB01.setVisibility(View.VISIBLE);
                else
                    bi.fldGrpSectionB01.setVisibility(View.GONE);
            }
        }));

/*        if (personal.getHhModel().getGenderFemale()) {
            bi.fldGrpSectionB01.setVisibility(View.VISIBLE);
        }*/


        bi.pb04.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0402.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb05);
            }
        }));

        bi.pb09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb10);
            }
        }));

        bi.pb09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb10);
            }
        }));

        //Skip for married
        int age = personal.getHhModel().getMemAge();
        if (age < 15) {
            bi.fldGrpCVpb03.setVisibility(View.GONE);
            bi.fldGrpSectionB01.setVisibility(View.GONE);
            if (age <= 5) {
                bi.fldGrpSectionB02.setVisibility(View.GONE);
                bi.fldGrpCVpb09.setVisibility(View.GONE);
            } else {
                bi.fldGrpSectionB02.setVisibility(View.VISIBLE);
                bi.fldGrpCVpb09.setVisibility(View.VISIBLE);
            }
        } else {
            bi.fldGrpCVpb03.setVisibility(View.VISIBLE);
            bi.fldGrpSectionB02.setVisibility(View.VISIBLE);
            bi.fldGrpCVpb09.setVisibility(View.VISIBLE);
            if (personal.getHhModel().getGenderFemale()) {
                bi.fldGrpSectionB01.setVisibility(View.VISIBLE);
            }
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
            startActivity(new Intent(this, SectionPIB02Activity.class));
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

        json.put("pb011", bi.pb011a.isChecked() ? "1"
                : bi.pb011b.isChecked() ? "2"
                : "-1");

        json.put("pb012", bi.pb012a.isChecked() ? "1"
                : bi.pb012b.isChecked() ? "2"
                : "-1");

        json.put("pb013", bi.pb013a.isChecked() ? "1"
                : bi.pb013b.isChecked() ? "2"
                : "-1");

        json.put("pb13c", bi.pb13ca.isChecked() ? "1"
                : bi.pb13cb.isChecked() ? "2"
                : "-1");

        json.put("pb014", bi.pb014a.isChecked() ? "1"
                : bi.pb014b.isChecked() ? "2"
                : "-1");

        json.put("pb015", bi.pb015a.isChecked() ? "1"
                : bi.pb015b.isChecked() ? "2"
                : "-1");

        json.put("pb016", bi.pb016a.isChecked() ? "1"
                : bi.pb016b.isChecked() ? "2"
                : "-1");

        json.put("pb017", bi.pb017a.isChecked() ? "1"
                : bi.pb017b.isChecked() ? "2"
                : "-1");

        json.put("pb018", bi.pb018a.isChecked() ? "1"
                : bi.pb018b.isChecked() ? "2"
                : "-1");

        json.put("pb019", bi.pb019a.isChecked() ? "1"
                : bi.pb019b.isChecked() ? "2"
                : "-1");

        json.put("pb0110", bi.pb0110a.isChecked() ? "1"
                : bi.pb0110b.isChecked() ? "2"
                : "-1");

        json.put("pb0111", bi.pb0111a.isChecked() ? "1"
                : bi.pb0111b.isChecked() ? "2"
                : "-1");

        json.put("pb0112", bi.pb0112a.isChecked() ? "1"
                : bi.pb0112b.isChecked() ? "2"
                : "-1");

        json.put("pb0113", bi.pb0113a.isChecked() ? "1"
                : bi.pb0113b.isChecked() ? "2"
                : "-1");

        json.put("pb0114", bi.pb0114a.isChecked() ? "1"
                : bi.pb0114b.isChecked() ? "2"
                : "-1");

        json.put("pb0115", bi.pb0115a.isChecked() ? "1"
                : bi.pb0115b.isChecked() ? "2"
                : "-1");

        json.put("pb021", bi.pb021a.isChecked() ? "1"
                : bi.pb021b.isChecked() ? "2"
                : "-1");

        json.put("pb022", bi.pb022a.isChecked() ? "1"
                : bi.pb022b.isChecked() ? "2"
                : "-1");

        json.put("pb023", bi.pb023a.isChecked() ? "1"
                : bi.pb023b.isChecked() ? "2"
                : "-1");

        json.put("pb024", bi.pb024a.isChecked() ? "1"
                : bi.pb024b.isChecked() ? "2"
                : "-1");

        json.put("pb025", bi.pb025a.isChecked() ? "1"
                : bi.pb025b.isChecked() ? "2"
                : "-1");

        json.put("pb026", bi.pb026a.isChecked() ? "1"
                : bi.pb026b.isChecked() ? "2"
                : "-1");

        json.put("pb027", bi.pb027a.isChecked() ? "1"
                : bi.pb027b.isChecked() ? "2"
                : "-1");

        json.put("pb028", bi.pb028a.isChecked() ? "1"
                : bi.pb028b.isChecked() ? "2"
                : bi.pb02x.isChecked() ? "96"
                : "-1");

        json.put("pb0201", bi.pb0201a.isChecked() ? "1"
                : bi.pb0201b.isChecked() ? "2"
                : "-1");

        json.put("pb0202a", bi.pb0202a.isChecked() ? "1" : "-1");
        json.put("pb0202b", bi.pb0202b.isChecked() ? "2" : "-1");
        json.put("pb0202c", bi.pb0202c.isChecked() ? "3" : "-1");
        json.put("pb0202d", bi.pb0202d.isChecked() ? "4" : "-1");
        json.put("pb0202e", bi.pb0202e.isChecked() ? "5" : "-1");
        json.put("pb0202f", bi.pb0202f.isChecked() ? "6" : "-1");
        json.put("pb0202g", bi.pb0202g.isChecked() ? "7" : "-1");
        json.put("pb0202h", bi.pb0202h.isChecked() ? "8" : "-1");
        json.put("pb0202i", bi.pb0202i.isChecked() ? "9" : "-1");
        json.put("pb0202j", bi.pb0202j.isChecked() ? "10" : "-1");
        json.put("pb0202k", bi.pb0202k.isChecked() ? "11" : "-1");
        json.put("pb0202l", bi.pb0202l.isChecked() ? "12" : "-1");
        json.put("pb0202m", bi.pb0202m.isChecked() ? "13" : "-1");


        json.put("pb03", bi.pb0301.isChecked() ? "1"
                : bi.pb0302.isChecked() ? "2"
                : bi.pb0303.isChecked() ? "3"
                : bi.pb0304.isChecked() ? "4"
                : "-1");

        json.put("pb04", bi.pb0401.isChecked() ? "1"
                : bi.pb0402.isChecked() ? "2"
                : bi.pb043.isChecked() ? "3"
                : "-1");

        json.put("pb05", bi.pb0501.isChecked() ? "1"
                : bi.pb0502.isChecked() ? "2"
                : bi.pb0503.isChecked() ? "3"
                : "-1");

        json.put("pb05a", bi.pb05a01.isChecked() ? "1"
                : bi.pb05a02.isChecked() ? "2"
                : "-1");

        json.put("pb05b01", bi.pb05b01.isChecked() ? "1" : "-1");
        json.put("pb05b02", bi.pb05b02.isChecked() ? "2" : "-1");
        json.put("pb05b03", bi.pb05b03.isChecked() ? "3" : "-1");
        json.put("pb05b04", bi.pb05b04.isChecked() ? "4" : "-1");
        json.put("pb05c01", bi.pb05c01.isChecked() ? "1" : "-1");
        json.put("pb05c02", bi.pb05c02.isChecked() ? "2" : "-1");
        json.put("pb05c03", bi.pb05c03.isChecked() ? "3" : "-1");
        json.put("pb05c04", bi.pb05c04.isChecked() ? "4" : "-1");
        json.put("pb05c05", bi.pb05c05.isChecked() ? "5" : "-1");
        json.put("pb05d01", bi.pb05d01.isChecked() ? "1" : "-1");
        json.put("pb05d02", bi.pb05d02.isChecked() ? "2" : "-1");
        json.put("pb05d03", bi.pb05d03.isChecked() ? "3" : "-1");
        json.put("pb05d04", bi.pb05d04.isChecked() ? "4" : "-1");
        json.put("pb05d05", bi.pb05d05.isChecked() ? "5" : "-1");
        json.put("pb05d06", bi.pb05d06.isChecked() ? "6" : "-1");
        json.put("pb05d07", bi.pb05d07.isChecked() ? "7" : "-1");
        json.put("pb05d08", bi.pb05d08.isChecked() ? "8" : "-1");
        json.put("pb05d09", bi.pb05d09.isChecked() ? "9" : "-1");
        json.put("pb05d10", bi.pb05d10.isChecked() ? "10" : "-1");
        json.put("pb05d11", bi.pb05d11.isChecked() ? "11" : "-1");
        json.put("pb05d12", bi.pb05d12.isChecked() ? "12" : "-1");
        json.put("pb05d13", bi.pb05d13.isChecked() ? "13" : "-1");
        json.put("pb05d14", bi.pb05d14.isChecked() ? "14" : "-1");
        json.put("pb05d15", bi.pb05d15.isChecked() ? "15" : "-1");

        json.put("pb05e", bi.pb05e01.isChecked() ? "1"
                : bi.pb05e02.isChecked() ? "2"
                : "-1");

        json.put("pb05f", bi.pb05f01.isChecked() ? "1"
                : bi.pb05f02.isChecked() ? "2"
                : "-1");

        json.put("pb05g", bi.pb05g.getText().toString());

        json.put("pb05h01", bi.pb05h01.isChecked() ? "1" : "-1");
        json.put("pb05h02", bi.pb05h02.isChecked() ? "2" : "-1");
        json.put("pb05h03", bi.pb05h03.isChecked() ? "3" : "-1");
        json.put("pb05h04", bi.pb05h04.isChecked() ? "4" : "-1");
        json.put("pb05h05", bi.pb05h05.isChecked() ? "5" : "-1");
        json.put("pb05h06", bi.pb05h06.isChecked() ? "6" : "-1");
        json.put("pb05h07", bi.pb05h07.isChecked() ? "7" : "-1");
        json.put("pb05h08", bi.pb05h08.isChecked() ? "8" : "-1");

        json.put("pb05i", bi.pb05i01.isChecked() ? "1"
                : bi.pb05i02.isChecked() ? "2"
                : bi.pb05i03.isChecked() ? "3"
                : bi.pb05i04.isChecked() ? "4"
                : bi.pb05i05.isChecked() ? "5"
                : bi.pb05i06.isChecked() ? "6"
                : bi.pb05i07.isChecked() ? "7"
                : "-1");

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


        personal.setsB(json.toString());

        personal.getHhModel().setMarried(bi.pb0301.isChecked());

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