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
import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionH301Binding;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionH301Activity extends AppCompatActivity {

    ActivitySectionH301Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h301);
        bi.setCallback(this);

        bi.nh303.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.nh303b || i == R.id.nh303c) {
                Clear.clearAllFields(bi.fldGrnh304);
                bi.fldGrnh304.setVisibility(View.GONE);
            } else {
                bi.fldGrnh304.setVisibility(View.VISIBLE);
            }
        });

        bi.nh30498.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bi.nh30499.setChecked(false);
                bi.nh304.setEnabled(false);
                bi.nh304.setText(null);
                bi.nh304.setError(null);
            } else {
                bi.nh304.setEnabled(true);
            }
        });

        bi.nh30499.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bi.nh30498.setChecked(false);
                bi.nh304.setEnabled(false);
                bi.nh304.setText(null);
                bi.nh304.setError(null);
            } else {
                bi.nh304.setEnabled(true);
            }
        });

        bi.nh305.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.nh305b) {
                Clear.clearAllFields(bi.fldGrpnh306);
                bi.fldGrpnh306.setVisibility(View.GONE);
            } else {
                bi.fldGrpnh306.setVisibility(View.VISIBLE);
            }
        });

//        nh307
        bi.nh307.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.nh307h || i == R.id.nh307i) {
                Clear.clearAllFields(bi.fldGrpnh308);
                bi.fldGrpnh308.setVisibility(View.GONE);
            } else {
                bi.fldGrpnh308.setVisibility(View.VISIBLE);
            }
        });

//        nh308
        bi.nh308.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.nh308b) {
                Clear.clearAllFields(bi.fldGrpnh309);
                bi.fldGrpnh309.setVisibility(View.GONE);
            } else {
                Clear.clearAllFields(bi.fldGrpnh309);
                bi.fldGrpnh309.setVisibility(View.VISIBLE);
            }
        });

    }

    public void BtnContinue() {
        if (formValidation()) {
            try {
                saveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (updateDB()) {
                finish();
                startActivity(new Intent(this, SectionH302Activity.class));
            } else {
                Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        AppUtilsKt.openHHSpecificEndActivity(this);
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpH301);
    }

    private void saveDraft() throws JSONException {

        JSONObject sH3 = new JSONObject();

        sH3.put("nh301", bi.nh301a.isChecked() ? "1"
                : bi.nh301b.isChecked() ? "2"
                : bi.nh301c.isChecked() ? "3"
                : bi.nh301d.isChecked() ? "4"
                : bi.nh301e.isChecked() ? "5"
                : bi.nh301f.isChecked() ? "6"
                : bi.nh301g.isChecked() ? "7"
                : bi.nh30196.isChecked() ? "96"
                : "-1");
        sH3.put("nh30196x", bi.nh30196x.getText().toString());

        sH3.put("nh302", bi.nh302a.isChecked() ? "1"
                : bi.nh302b.isChecked() ? "2"
                : bi.nh30296.isChecked() ? "96" : "-1");
        sH3.put("nh30296x", bi.nh30296x.getText().toString());

        sH3.put("nh303a", bi.nh303a1.isChecked() ? "1"
                : bi.nh303a2.isChecked() ? "2"
                : "-1");

        sH3.put("nh303", bi.nh303b.isChecked() ? "1"
                : bi.nh303c.isChecked() ? "2"
                : bi.nh303d.isChecked() ? "3"
                : bi.nh303e.isChecked() ? "4"
                : bi.nh303f.isChecked() ? "5"
                : bi.nh303g.isChecked() ? "6"
                : bi.nh303h.isChecked() ? "7"
                : bi.nh303i.isChecked() ? "8"
                : bi.nh303j.isChecked() ? "9"
                : bi.nh303k.isChecked() ? "10"
                : bi.nh303l.isChecked() ? "11"
                : bi.nh303m.isChecked() ? "12"
                : bi.nh303n.isChecked() ? "13"
                : bi.nh303o.isChecked() ? "14"
                : bi.nh303p.isChecked() ? "15"
                : bi.nh303q.isChecked() ? "16"
                : bi.nh30396.isChecked() ? "96"
                : "-1");
        sH3.put("nh30396x", bi.nh30396x.getText().toString());

        sH3.put("nh304", bi.nh30498.isChecked() ? "998" : bi.nh30499.isChecked() ? "000" : bi.nh304.getText().toString());

        sH3.put("nh305", bi.nh305a.isChecked() ? "1"
                : bi.nh305b.isChecked() ? "2"
                : "-1");

        sH3.put("nh306", bi.nh306a.isChecked() ? "1"
                : bi.nh306b.isChecked() ? "2"
                : bi.nh306c.isChecked() ? "3"
                : bi.nh306d.isChecked() ? "4"
                : bi.nh306e.isChecked() ? "5"
                : bi.nh306f.isChecked() ? "6"
                : bi.nh30696.isChecked() ? "96"
                : "-1");
        sH3.put("nh30696x", bi.nh30696x.getText().toString());


        sH3.put("nh307", bi.nh307a.isChecked() ? "1"
                : bi.nh307b.isChecked() ? "2"
                : bi.nh307c.isChecked() ? "3"
                : bi.nh307d.isChecked() ? "4"
                : bi.nh307e.isChecked() ? "5"
                : bi.nh307f.isChecked() ? "6"
                : bi.nh307g.isChecked() ? "7"
                : bi.nh307h.isChecked() ? "8"
                : bi.nh307i.isChecked() ? "9"
                : bi.nh30796.isChecked() ? "96"
                : "-1");

        sH3.put("nh30796x", bi.nh30796x.getText().toString());

        sH3.put("nh308", bi.nh308a.isChecked() ? "1"
                : bi.nh308b.isChecked() ? "2"
                : "-1");

        sH3.put("nh309", bi.nh309.getText().toString());
//        nh310
        sH3.put("nh310", bi.nh310a.isChecked() ? "1"
                : bi.nh310b.isChecked() ? "2"
                : bi.nh310c.isChecked() ? "3"
                : bi.nh310d.isChecked() ? "4"
                : bi.nh310e.isChecked() ? "5"
                : bi.nh310f.isChecked() ? "6"
                : bi.nh310g.isChecked() ? "7"
                : bi.nh31096.isChecked() ? "96"
                : "-1");
        sH3.put("nh31096x", bi.nh31096x.getText().toString());
//        nh311
        sH3.put("nh31101", bi.nh31101a.isChecked() ? "1"
                : bi.nh31101b.isChecked() ? "2"
                : "-1");

        sH3.put("nh31102", bi.nh31102a.isChecked() ? "1"
                : bi.nh31102b.isChecked() ? "2"
                : "-1");

        sH3.put("nh31103", bi.nh31103a.isChecked() ? "1"
                : bi.nh31103b.isChecked() ? "2"
                : "-1");

        sH3.put("nh31104", bi.nh31104a.isChecked() ? "1"
                : bi.nh31104b.isChecked() ? "2"
                : "-1");

        sH3.put("nh31105", bi.nh31105a.isChecked() ? "1"
                : bi.nh31105b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31106", bi.nh31106a.isChecked() ? "1"
                : bi.nh31106b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31107", bi.nh31107a.isChecked() ? "1"
                : bi.nh31107b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31108", bi.nh31108a.isChecked() ? "1"
                : bi.nh31108b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31109", bi.nh31109a.isChecked() ? "1"
                : bi.nh31109b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31110", bi.nh31110a.isChecked() ? "1"
                : bi.nh31110b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31111", bi.nh31111a.isChecked() ? "1"
                : bi.nh31111b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31112", bi.nh31112a.isChecked() ? "1"
                : bi.nh31112b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31113", bi.nh31113a.isChecked() ? "1"
                : bi.nh31113b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31114", bi.nh31114a.isChecked() ? "1"
                : bi.nh31114b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31115", bi.nh31115a.isChecked() ? "1"
                : bi.nh31115b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31116", bi.nh31116a.isChecked() ? "1"
                : bi.nh31116b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31117", bi.nh31117a.isChecked() ? "1"
                : bi.nh31117b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31118", bi.nh31118a.isChecked() ? "1"
                : bi.nh31118b.isChecked() ? "2"
                : "-1");
        sH3.put("nh31119", bi.nh31119a.isChecked() ? "1"
                : bi.nh31119b.isChecked() ? "2"
                : "-1");

        form.setsH3(String.valueOf(sH3));

    }

    private boolean updateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH3, MainApp.form.getsH3());
        return updcount == 1;
    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

}
