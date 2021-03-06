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
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionIm3Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.JSONUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity;

import static edu.aku.hassannaqvi.covid_matiari.CONSTANTS.IM01CARDSEEN;
import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;


public class SectionIM3Activity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionIm3Binding bi;
    boolean im07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_im3);
        bi.setCallback(this);
        setupSkips();

        //imo7 Check
        im07 = getIntent().getBooleanExtra(IM01CARDSEEN, false);
        if (im07) {
            bi.cvim08.setVisibility(View.GONE);
            bi.llim0923a.setVisibility(View.GONE);
            bi.llim23.setVisibility(View.VISIBLE);
        }
    }

    private void setupSkips() {
        bi.im08.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == bi.im082.getId()) {
                bi.llim0923a.setVisibility(View.GONE);
                Clear.clearAllFields(bi.llim0923a);
                bi.llim23.setVisibility(View.GONE);
                Clear.clearAllFields(bi.llim23);
            } else {
                bi.llim0923a.setVisibility(View.VISIBLE);
                bi.llim23.setVisibility(View.VISIBLE);
            }
        });
        bi.im10.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.llim1113));
        bi.im23.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.cvim23a);
            Clear.clearAllFields(bi.cvim24);
        });
        bi.im25.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.cvim26));
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_SI, personal.getsI());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("im08", bi.im081.isChecked() ? "1"
                : bi.im082.isChecked() ? "2"
                : bi.im083.isChecked() ? "98"
                : "-1");

        json.put("im09", bi.im091.isChecked() ? "1"
                : bi.im092.isChecked() ? "2"
                : bi.im093.isChecked() ? "98"
                : "-1");

        json.put("im10", bi.im101.isChecked() ? "1"
                : bi.im102.isChecked() ? "2"
                : bi.im103.isChecked() ? "98"
                : "-1");

        json.put("im11", bi.im111.isChecked() ? "1"
                : bi.im112.isChecked() ? "2"
                : bi.im113.isChecked() ? "98"
                : "-1");

        json.put("im12", bi.im12.getText().toString().trim().isEmpty() ? "-1" : bi.im12.getText().toString());

        json.put("im13", bi.im131.isChecked() ? "1"
                : bi.im132.isChecked() ? "2"
                : bi.im133.isChecked() ? "98"
                : "-1");

        json.put("im14", bi.im141.isChecked() ? "1"
                : bi.im142.isChecked() ? "2"
                : bi.im143.isChecked() ? "98"
                : "-1");

        json.put("im15", bi.im15.getText().toString().trim().isEmpty() ? "-1" : bi.im15.getText().toString());

        json.put("im16", bi.im161.isChecked() ? "1"
                : bi.im162.isChecked() ? "2"
                : bi.im163.isChecked() ? "98"
                : "-1");

        json.put("im17", bi.im17.getText().toString().trim().isEmpty() ? "-1" : bi.im17.getText().toString());

        json.put("im18", bi.im181.isChecked() ? "1"
                : bi.im182.isChecked() ? "2"
                : bi.im183.isChecked() ? "98"
                : "-1");

        json.put("im19", bi.im19.getText().toString().trim().isEmpty() ? "-1" : bi.im19.getText().toString());

        json.put("im20", bi.im201.isChecked() ? "1"
                : bi.im202.isChecked() ? "2"
                : bi.im203.isChecked() ? "98"
                : "-1");

        json.put("im21", bi.im211.isChecked() ? "1"
                : bi.im212.isChecked() ? "2"
                : bi.im213.isChecked() ? "98"
                : "-1");

        json.put("im22", bi.im22.getText().toString().trim().isEmpty() ? "-1" : bi.im22.getText().toString());

        json.put("im23", bi.im231.isChecked() ? "1"
                : bi.im232.isChecked() ? "2"
                : bi.im233.isChecked() ? "3"
                : bi.im236.isChecked() ? "6"
                : "-1");
        json.put("im236x", bi.im236x.getText().toString().trim().isEmpty() ? "-1" : bi.im236x.getText().toString());

        json.put("im23a", bi.im23a1.isChecked() ? "1"
                : bi.im23a2.isChecked() ? "2"
                : bi.im23a3.isChecked() ? "3"
                : bi.im23a96.isChecked() ? "96"
                : "-1");
        json.put("im23a96x", bi.im23a96x.getText().toString().trim().isEmpty() ? "-1" : bi.im23a96x.getText().toString());

        json.put("im24", bi.im241.isChecked() ? "1"
                : bi.im242.isChecked() ? "2"
                : bi.im243.isChecked() ? "3"
                : bi.im244.isChecked() ? "4"
                : bi.im245.isChecked() ? "5"
                : bi.im246.isChecked() ? "6"
                : bi.im247.isChecked() ? "7"
                : bi.im248.isChecked() ? "8"
                : bi.im249.isChecked() ? "9"
                : bi.im2410.isChecked() ? "10"
                : bi.im2411.isChecked() ? "11"
                : bi.im2412.isChecked() ? "12"
                : bi.im2413.isChecked() ? "13"
                : bi.im2414.isChecked() ? "14"
                : bi.im2415.isChecked() ? "15"
                : bi.im2416.isChecked() ? "16"
                : bi.im2417.isChecked() ? "98"
                : bi.im2499.isChecked() ? "98"
                : "-1");
        json.put("im2417x", bi.im2417x.getText().toString().trim().isEmpty() ? "-1" : bi.im2417x.getText().toString());

        json.put("im25", bi.im2501.isChecked() ? "1"
                : bi.im2502.isChecked() ? "2"
                : "-1");

        json.put("im2601", bi.im2601.isChecked() ? "1" : "-1");
        json.put("im2602", bi.im2602.isChecked() ? "2" : "-1");
        json.put("im2603", bi.im2603.isChecked() ? "3" : "-1");
        json.put("im2604", bi.im2604.isChecked() ? "4" : "-1");
        json.put("im2605", bi.im2605.isChecked() ? "5" : "-1");
        json.put("im2606", bi.im2606.isChecked() ? "6" : "-1");
        json.put("im2607", bi.im2607.isChecked() ? "7" : "-1");
        json.put("im2608", bi.im2608.isChecked() ? "8" : "-1");
        json.put("im2609", bi.im2609.isChecked() ? "9" : "-1");
        json.put("im2610", bi.im2610.isChecked() ? "10" : "-1");
        json.put("im2611", bi.im2611.isChecked() ? "11" : "-1");
        json.put("im2612", bi.im2612.isChecked() ? "12" : "-1");
        json.put("im2613", bi.im2613.isChecked() ? "13" : "-1");
        json.put("im2614", bi.im2614.isChecked() ? "14" : "-1");
        json.put("im2615", bi.im2615.isChecked() ? "15" : "-1");
        json.put("im2616", bi.im2616.isChecked() ? "16" : "-1");
        json.put("im2617", bi.im2617.isChecked() ? "17" : "-1");
        json.put("im2618", bi.im2618.isChecked() ? "18" : "-1");
        json.put("im2619", bi.im2619.isChecked() ? "19" : "-1");
        json.put("im2620", bi.im2620.isChecked() ? "20" : "-1");
        json.put("im2621", bi.im2621.isChecked() ? "21" : "-1");
        json.put("im2622", bi.im2622.isChecked() ? "22" : "-1");
        json.put("im2696", bi.im2696.isChecked() ? "96" : "-1");

        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(personal.getsI()), json);

            personal.setsI(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, PIEndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, PIEndingActivity.class);
    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

    @Override
    public void endSecActivity(boolean flag) {
        try {
            SaveDraft();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, PIEndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }
}
