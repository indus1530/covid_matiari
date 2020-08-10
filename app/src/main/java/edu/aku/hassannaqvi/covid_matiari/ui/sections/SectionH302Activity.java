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
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionH302Binding;
import edu.aku.hassannaqvi.covid_matiari.utils.JSONUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionH302Activity extends AppCompatActivity {

    ActivitySectionH302Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_h302);
        bi.setCallback(this);

        bi.nh315.setOnCheckedChangeListener((group, checkedId) -> {
            if (bi.nh315a.isChecked()) {
                Clear.clearAllFields(bi.fldGrpnh316);
                bi.fldGrpnh316.setVisibility(View.GONE);
            } else {
                bi.fldGrpnh316.setVisibility(View.VISIBLE);
            }
        });

//        nh321
        bi.nh321.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.nh321b) {
                Clear.clearAllFields(bi.fldGrpnh322);
                bi.fldGrpnh322.setVisibility(View.GONE);
            } else {
                bi.fldGrpnh322.setVisibility(View.VISIBLE);
            }
        });

//        nh323
        bi.nh323.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.nh323b) {
                Clear.clearAllFields(bi.fldGrpnh324);
                bi.fldGrpnh324.setVisibility(View.GONE);
            } else {
                bi.fldGrpnh324.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(this, SectionH4Activity.class));
            } else {
                Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        AppUtilsKt.openHHSpecificEndActivity(this);
    }

    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.fldGrpH302))
            return false;
        if (bi.nh323b.isChecked())
            return true;
        int total_324 = Integer.parseInt(bi.nh324a.getText().toString().trim())
                + Integer.parseInt(bi.nh324b.getText().toString().trim())
                + Integer.parseInt(bi.nh324c.getText().toString().trim())
                + Integer.parseInt(bi.nh324d.getText().toString().trim())
                + Integer.parseInt(bi.nh324e.getText().toString().trim())
                + Integer.parseInt(bi.nh324f.getText().toString().trim())
                + Integer.parseInt(bi.nh324g.getText().toString().trim());

        if (total_324 == 0) {
            return Validator.emptyCustomTextBox(this, bi.qtxtNh324, "Total count cannot be 0");
        }
        return true;
    }

    private void saveDraft() throws JSONException {

        JSONObject sH3 = new JSONObject();

//        nh312
        sH3.put("nh312a", bi.nh312a1.isChecked() ? "1" : bi.nh312a2.isChecked() ? "2" : "-1");
        sH3.put("nh312b", bi.nh312b1.isChecked() ? "1" : bi.nh312b2.isChecked() ? "2" : "-1");
        sH3.put("nh312c", bi.nh312c1.isChecked() ? "1" : bi.nh312c2.isChecked() ? "2" : "-1");
        sH3.put("nh312d", bi.nh312d1.isChecked() ? "1" : bi.nh312d2.isChecked() ? "2" : "-1");
        sH3.put("nh312e", bi.nh312e1.isChecked() ? "1" : bi.nh312e2.isChecked() ? "2" : "-1");
        sH3.put("nh312f", bi.nh312f1.isChecked() ? "1" : bi.nh312f2.isChecked() ? "2" : "-1");
        sH3.put("nh312g", bi.nh312g1.isChecked() ? "1" : bi.nh312g2.isChecked() ? "2" : "-1");
        sH3.put("nh312h", bi.nh312h1.isChecked() ? "1" : bi.nh312h2.isChecked() ? "2" : "-1");
        sH3.put("nh312i", bi.nh312i1.isChecked() ? "1" : bi.nh312i2.isChecked() ? "2" : "-1");

//        nh313
        sH3.put("nh313a", bi.nh313a.isChecked() ? "1" : "-1");
        sH3.put("nh313b", bi.nh313b.isChecked() ? "2" : "-1");
        sH3.put("nh313c", bi.nh313c.isChecked() ? "3" : "-1");
        sH3.put("nh313d", bi.nh313d.isChecked() ? "4" : "-1");
        sH3.put("nh313e", bi.nh313e.isChecked() ? "5" : "-1");
        sH3.put("nh313f", bi.nh313f.isChecked() ? "6" : "-1");
        sH3.put("nh31396", bi.nh31396.isChecked() ? "96" : "-1");
        sH3.put("nh31396x", bi.nh31396x.getText().toString());

//        nh314
        sH3.put("nh314", bi.nh314a.isChecked() ? "1"
                : bi.nh314b.isChecked() ? "2"
                : bi.nh314c.isChecked() ? "3"
                : bi.nh314d.isChecked() ? "4"
                : bi.nh314e.isChecked() ? "5"
                : bi.nh314f.isChecked() ? "6"
                : bi.nh314g.isChecked() ? "7"
                : bi.nh314h.isChecked() ? "8"
                : bi.nh314i.isChecked() ? "9"
                : bi.nh314j.isChecked() ? "10"
                : bi.nh314k.isChecked() ? "11"
                : bi.nh31496.isChecked() ? "96"
                : "-1");
        sH3.put("nh31496x", bi.nh31496x.getText().toString());
//       nh315
        sH3.put("nh315", bi.nh315a.isChecked() ? "1"
                : bi.nh315b.isChecked() ? "2"
                : bi.nh315c.isChecked() ? "3"
                : bi.nh31596.isChecked() ? "96"
                : "-1");
        sH3.put("nh31596x", bi.nh31596x.getText().toString());
//        nh316
        sH3.put("nh316", bi.nh316a.isChecked() ? "1"
                : bi.nh316b.isChecked() ? "2"
                : "-1");

//        nh317
        sH3.put("nh317", bi.nh317a.isChecked() ? "11"
                : bi.nh317b.isChecked() ? "12"
                : bi.nh317c.isChecked() ? "21"
                : bi.nh317d.isChecked() ? "22"
                : bi.nh317e.isChecked() ? "31"
                : bi.nh317f.isChecked() ? "32"
                : bi.nh317g.isChecked() ? "33"
                : bi.nh317h.isChecked() ? "34"
                : bi.nh317i.isChecked() ? "35"
                : bi.nh317j.isChecked() ? "36"
                : bi.nh317k.isChecked() ? "37"
                : bi.nh31796.isChecked() ? "96"
                : "-1");
        sH3.put("nh31796x", bi.nh31796x.getText().toString());

//        nh318

        sH3.put("nh318", bi.nh318a.isChecked() ? "11"
                : bi.nh318b.isChecked() ? "12"
                : bi.nh318c.isChecked() ? "13"
                : bi.nh318d.isChecked() ? "21"
                : bi.nh318e.isChecked() ? "22"
                : bi.nh318f.isChecked() ? "23"
                : bi.nh318g.isChecked() ? "24"
                : bi.nh318h.isChecked() ? "31"
                : bi.nh318i.isChecked() ? "32"
                : bi.nh318j.isChecked() ? "33"
                : bi.nh318k.isChecked() ? "34"
                : bi.nh318l.isChecked() ? "35"
                : bi.nh318m.isChecked() ? "36"
                : bi.nh318n.isChecked() ? "37"
                : bi.nh31896.isChecked() ? "96"
                : "-1");
        sH3.put("nh31896x", bi.nh31896x.getText().toString());

//          nh319
        sH3.put("nh319", bi.nh319a.isChecked() ? "11"
                : bi.nh319b.isChecked() ? "12"
                : bi.nh319c.isChecked() ? "13"
                : bi.nh319d.isChecked() ? "21"
                : bi.nh319e.isChecked() ? "22"
                : bi.nh319f.isChecked() ? "23"
                : bi.nh319g.isChecked() ? "24"
                : bi.nh319h.isChecked() ? "25"
                : bi.nh319i.isChecked() ? "26"
                : bi.nh319j.isChecked() ? "27"
                : bi.nh319k.isChecked() ? "31"
                : bi.nh319l.isChecked() ? "32"
                : bi.nh319m.isChecked() ? "33"
                : bi.nh319n.isChecked() ? "34"
                : bi.nh319o.isChecked() ? "35"
                : bi.nh319p.isChecked() ? "36"
                : bi.nh31996.isChecked() ? "96"
                : "-1");
        sH3.put("nh31996x", bi.nh31996x.getText().toString());

//        nh320
        sH3.put("nh320", bi.nh320.getText().toString());

//        nh321
        sH3.put("nh321", bi.nh321a.isChecked() ? "1"
                : bi.nh321b.isChecked() ? "2"
                : "-1");

//        nh322
        sH3.put("nh322", bi.nh322a.isChecked() ? "1"
                : bi.nh322b.isChecked() ? "2"
                : bi.nh32298.isChecked() ? "98"
                : "-1");
        sH3.put("nh322acr", bi.nh322acr.getText().toString());
        sH3.put("nh322can", bi.nh322can.getText().toString());

//        nh323
        sH3.put("nh323", bi.nh323a.isChecked() ? "1"
                : bi.nh323b.isChecked() ? "2"
                : "-1");
//        nh324
        sH3.put("nh324a", bi.nh324a.getText().toString());
        sH3.put("nh324b", bi.nh324b.getText().toString());
        sH3.put("nh324c", bi.nh324c.getText().toString());
        sH3.put("nh324d", bi.nh324d.getText().toString());
        sH3.put("nh324e", bi.nh324e.getText().toString());
        sH3.put("nh324f", bi.nh324f.getText().toString());
        sH3.put("nh324g", bi.nh324g.getText().toString());

        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(form.getsH3()), sH3);

            form.setsH3(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }
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