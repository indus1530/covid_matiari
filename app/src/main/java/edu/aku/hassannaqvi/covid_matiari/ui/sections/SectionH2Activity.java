package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionH2Binding;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity;

import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionH2Activity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionH2Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h2);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {

        bi.hb04.setOnCheckedChangeListener((radioGroup, checkId) -> {
            Clear.clearAllFields(bi.llb0401);
        });

        bi.hb07.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb0702.getId()) {
                Clear.clearAllFields(bi.fldGrpSecHA01);
            }
        }));

        bi.hb09.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb0902.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb10);
            }
        }));

        bi.hb11.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb1102.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb12);
            }
        }));

        bi.hb13.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb1302.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb14);
            }
        }));

        bi.hb15.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.hb1502.getId()) {
                Clear.clearAllFields(bi.fldGrpCVhb16);
            }
        }));

    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            saveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (updateDB()) {
            finish();
            startActivity(new Intent(this, SectionH301Activity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.fldGrpSectionH2))
            return false;
        int totalmember = (TextUtils.isEmpty(bi.hb10.getText()) ? 0 : Integer.parseInt(bi.hb10.getText().toString().trim()))
                + (TextUtils.isEmpty(bi.hb12.getText()) ? 0 : Integer.parseInt(bi.hb12.getText().toString().trim()))
                + (TextUtils.isEmpty(bi.hb14.getText()) ? 0 : Integer.parseInt(bi.hb14.getText().toString().trim()))
                + (TextUtils.isEmpty(bi.hb16.getText()) ? 0 : Integer.parseInt(bi.hb16.getText().toString().trim()));

        if (totalmember == 0) {
            return Validator.emptyCustomTextBox(this, bi.hb08present, "Invalid Total Count Please check again");
        } else if (totalmember != Integer.parseInt(bi.hb08present.getText().toString())) {
            return Validator.emptyCustomTextBox(this, bi.hb08present, "Invalid Total Count Please check again");
        } else if (Integer.parseInt(bi.hb08present.getText().toString()) > Integer.parseInt(bi.hb08.getText().toString())) {
            return Validator.emptyCustomTextBox(this, bi.hb08, "Total Members Cannot be less than present members");
        }
        return true;
    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH2, MainApp.form.getsH2());
        return updcount == 1;
    }

    public void BtnEnd() {
        AppUtilsKt.contextEndActivity(this);
    }

    private void saveDraft() throws JSONException {

        JSONObject sH2 = new JSONObject();

        sH2.put("hb01", bi.hb01.getText().toString());

        sH2.put("hb02", bi.hb02.getText().toString());

        sH2.put("hb03", bi.hb03.getText().toString());

        sH2.put("hb04", bi.hb0401.isChecked() ? "1"
                : bi.hb0402.isChecked() ? "2"
                : "-1");

        sH2.put("hb05", bi.hb05.getText().toString());

        sH2.put("hb06", bi.hb06.getText().toString());

        sH2.put("hb07", bi.hb0701.isChecked() ? "1"
                : bi.hb0702.isChecked() ? "2"
                : "-1");

        //    sH2.put("hb08", bi.hb08.getText().toString());

        sH2.put("hb08present", bi.hb08present.getText().toString());
        sH2.put("hb09", bi.hb0901.isChecked() ? "1"
                : bi.hb0902.isChecked() ? "2"
                : "-1");

        sH2.put("hb10", bi.hb10.getText().toString());

        sH2.put("hb11", bi.hb1101.isChecked() ? "1"
                : bi.hb1102.isChecked() ? "2"
                : "-1");

        sH2.put("hb12", bi.hb12.getText().toString());

        sH2.put("hb13", bi.hb1301.isChecked() ? "1"
                : bi.hb1302.isChecked() ? "2"
                : "-1");

        sH2.put("hb14", bi.hb14.getText().toString());

        sH2.put("hb15", bi.hb1501.isChecked() ? "1"
                : bi.hb1502.isChecked() ? "2"
                : "-1");

        sH2.put("hb16", bi.hb16.getText().toString());


        MainApp.form.setsH2((String.valueOf(sH2)));

    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

    @Override
    public void endSecActivity(boolean flag) {
        if (!formValidation()) return;
        try {
            saveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (updateDB()) {
            SectionSubInfoActivity.Companion.setIstatusFlag(88);
            finish();
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    public void showTooltipView(View view) {
        AppUtilsKt.showTooltip(this, view);
    }
}