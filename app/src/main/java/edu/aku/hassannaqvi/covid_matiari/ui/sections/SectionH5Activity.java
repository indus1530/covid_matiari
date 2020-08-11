package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionH5Binding;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionH5Activity extends AppCompatActivity {

    ActivitySectionH5Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h5);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {

        /*bi.pb03.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0302.getId() || i == bi.pb0304.getId()) {
                bi.fldGrpSectionB01.setVisibility(View.GONE);
                Clear.clearAllFields(bi.fldGrpSectionB01);
            } else {
                if (personal.getHhModel().getGenderFemale())
                    bi.fldGrpSectionB01.setVisibility(View.VISIBLE);
                else
                    bi.fldGrpSectionB01.setVisibility(View.GONE);
            }
        }));*/

/*        if (personal.getHhModel().getGenderFemale()) {
            bi.fldGrpSectionB01.setVisibility(View.VISIBLE);
        }*/


        bi.h501.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.h501b.getId()) {
                Clear.clearAllFields(bi.fldGrpSectionh1);
            }
        }));

       /* bi.pb09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb10);
            }
        }));*/

        /*bi.pb09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pb0902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpb10);
            }
        }));*/

        //Skip for married
        /*int age = personal.getHhModel().getMemAge();
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
        }*/

    }


    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            SectionSubInfoActivity.Companion.setIstatusFlag(1);
            finish();
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        /*DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_SB, MainApp.personal.getsB());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("h501", bi.h501a.isChecked() ? "1"
                : bi.h501b.isChecked() ? "2"
                : "-1");

        json.put("h502", bi.h502.getText().toString());

        json.put("h50301", bi.h50301.isChecked() ? "1" : "-1");
        json.put("h50301x", bi.h50301x.getText().toString());
        json.put("h50301xx", bi.h50301xx.getText().toString());

        json.put("h50302", bi.h50302.isChecked() ? "2" : "-1");
        json.put("h50302x", bi.h50302x.getText().toString());
        json.put("h50302xx", bi.h50302xx.getText().toString());

        json.put("h50303", bi.h50303.isChecked() ? "3" : "-1");
        json.put("h50303x", bi.h50303x.getText().toString());
        json.put("h50303xx", bi.h50303xx.getText().toString());

        json.put("h50304", bi.h50304.isChecked() ? "4" : "-1");
        json.put("h50304x", bi.h50304x.getText().toString());
        json.put("h50304xx", bi.h50304xx.getText().toString());

        json.put("h50305", bi.h50305.isChecked() ? "5" : "-1");
        json.put("h50305x", bi.h50305x.getText().toString());
        json.put("h50305xx", bi.h50305xx.getText().toString());

        json.put("h50306", bi.h50306.isChecked() ? "6" : "-1");
        json.put("h50306x", bi.h50306x.getText().toString());
        json.put("h50306xx", bi.h50306xx.getText().toString());

        json.put("h50307", bi.h50307.isChecked() ? "7" : "-1");
        json.put("h50307x", bi.h50307x.getText().toString());
        json.put("h50307xx", bi.h50307xx.getText().toString());

        json.put("h50308", bi.h50308.isChecked() ? "8" : "-1");
        json.put("h50308x", bi.h50308x.getText().toString());
        json.put("h50308xx", bi.h50308xx.getText().toString());

        json.put("h50309", bi.h50309.isChecked() ? "9" : "-1");
        json.put("h50309x", bi.h50309x.getText().toString());
        json.put("h50309xx", bi.h50309xx.getText().toString());

        json.put("h503010", bi.h503010.isChecked() ? "10" : "-1");
        json.put("h503010x", bi.h503010x.getText().toString());
        json.put("h503010xx", bi.h503010xx.getText().toString());

        json.put("h503011", bi.h503011.isChecked() ? "11" : "-1");
        json.put("h503011x", bi.h503011x.getText().toString());
        json.put("h503011xx", bi.h503011xx.getText().toString());

        json.put("h503096", bi.h503096.isChecked() ? "96" : "-1");
        json.put("h503096x", bi.h503096x.getText().toString());
        json.put("h503096xx", bi.h503096xx.getText().toString());


        personal.setsB(json.toString());

        //    personal.getHhModel().setMarried(bi.pb0301.isChecked());

    }


    public void BtnEnd() {
        AppUtilsKt.openHHSpecificEndActivity(this);
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionH5);

    }


    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }
}