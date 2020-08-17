package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Instant;

import java.util.Calendar;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionIm2Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.JSONUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.DateUtils;
import kotlin.Pair;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;


public class SectionIM2Activity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionIm2Binding bi;
    boolean im03Flag = false, imFlag = true;
    Instant dtInstant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_im2);
        bi.setCallback(this);

        if (form.getLocalDate() != null) {
            int maxYears = form.getLocalDate().getYear();
            int minYears = form.getLocalDate().minusYears(5).getYear();
            setYearOfBirth(minYears, maxYears);
        }

    }

    private void setYearOfBirth(int minYears, int maxYears) {
        /*bi.im04yy.setMinvalue(minYears);
        bi.im04yy.setMaxvalue(maxYears);*/
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

        json.put("im41bcgdd", bi.im41bcgdd.getText().toString().trim().isEmpty() ? "-1" : bi.im41bcgdd.getText().toString());
        json.put("im41bcgmm", bi.im41bcgmm.getText().toString().trim().isEmpty() ? "-1" : bi.im41bcgmm.getText().toString());
        json.put("im41bcgyy", bi.im41bcgyy.getText().toString().trim().isEmpty() ? "-1" : bi.im41bcgyy.getText().toString());

        json.put("im42opv0dd", bi.im42opv0dd.getText().toString().trim().isEmpty() ? "-1" : bi.im42opv0dd.getText().toString());
        json.put("im42opv0mm", bi.im42opv0mm.getText().toString().trim().isEmpty() ? "-1" : bi.im42opv0mm.getText().toString());
        json.put("im42opv0yy", bi.im42opv0yy.getText().toString().trim().isEmpty() ? "-1" : bi.im42opv0yy.getText().toString());

        json.put("im43opv1dd", bi.im43opv1dd.getText().toString().trim().isEmpty() ? "-1" : bi.im43opv1dd.getText().toString());
        json.put("im43opv1mm", bi.im43opv1mm.getText().toString().trim().isEmpty() ? "-1" : bi.im43opv1mm.getText().toString());
        json.put("im43opv1yy", bi.im43opv1yy.getText().toString().trim().isEmpty() ? "-1" : bi.im43opv1yy.getText().toString());

        json.put("im44penta1dd", bi.im44penta1dd.getText().toString().trim().isEmpty() ? "-1" : bi.im44penta1dd.getText().toString());
        json.put("im44penta1mm", bi.im44penta1mm.getText().toString().trim().isEmpty() ? "-1" : bi.im44penta1mm.getText().toString());
        json.put("im44penta1yy", bi.im44penta1yy.getText().toString().trim().isEmpty() ? "-1" : bi.im44penta1yy.getText().toString());

        json.put("im45pcv1dd", bi.im45pcv1dd.getText().toString().trim().isEmpty() ? "-1" : bi.im45pcv1dd.getText().toString());
        json.put("im45pcv1mm", bi.im45pcv1mm.getText().toString().trim().isEmpty() ? "-1" : bi.im45pcv1mm.getText().toString());
        json.put("im45pcv1yy", bi.im45pcv1yy.getText().toString().trim().isEmpty() ? "-1" : bi.im45pcv1yy.getText().toString());

        json.put("im46rv1dd", bi.im46rv1dd.getText().toString().trim().isEmpty() ? "-1" : bi.im46rv1dd.getText().toString());
        json.put("im46rv1mm", bi.im46rv1mm.getText().toString().trim().isEmpty() ? "-1" : bi.im46rv1mm.getText().toString());
        json.put("im46rv1yy", bi.im46rv1yy.getText().toString().trim().isEmpty() ? "-1" : bi.im46rv1yy.getText().toString());

        json.put("im47opv2dd", bi.im47opv2dd.getText().toString().trim().isEmpty() ? "-1" : bi.im47opv2dd.getText().toString());
        json.put("im47opv2mm", bi.im47opv2mm.getText().toString().trim().isEmpty() ? "-1" : bi.im47opv2mm.getText().toString());
        json.put("im47opv2yy", bi.im47opv2yy.getText().toString().trim().isEmpty() ? "-1" : bi.im47opv2yy.getText().toString());

        json.put("im48penta2dd", bi.im48penta2dd.getText().toString().trim().isEmpty() ? "-1" : bi.im48penta2dd.getText().toString());
        json.put("im48penta2mm", bi.im48penta2mm.getText().toString().trim().isEmpty() ? "-1" : bi.im48penta2mm.getText().toString());
        json.put("im48penta2yy", bi.im48penta2yy.getText().toString().trim().isEmpty() ? "-1" : bi.im48penta2yy.getText().toString());

        json.put("im49pcv2dd", bi.im49pcv2dd.getText().toString().trim().isEmpty() ? "-1" : bi.im49pcv2dd.getText().toString());
        json.put("im49pcv2mm", bi.im49pcv2mm.getText().toString().trim().isEmpty() ? "-1" : bi.im49pcv2mm.getText().toString());
        json.put("im49pcv2yy", bi.im49pcv2yy.getText().toString().trim().isEmpty() ? "-1" : bi.im49pcv2yy.getText().toString());

        json.put("im410rv2dd", bi.im410rv2dd.getText().toString().trim().isEmpty() ? "-1" : bi.im410rv2dd.getText().toString());
        json.put("im410rv2mm", bi.im410rv2mm.getText().toString().trim().isEmpty() ? "-1" : bi.im410rv2mm.getText().toString());
        json.put("im410rv2yy", bi.im410rv2yy.getText().toString().trim().isEmpty() ? "-1" : bi.im410rv2yy.getText().toString());

        json.put("im411opv3dd", bi.im411opv3dd.getText().toString().trim().isEmpty() ? "-1" : bi.im411opv3dd.getText().toString());
        json.put("im411opv3mm", bi.im411opv3mm.getText().toString().trim().isEmpty() ? "-1" : bi.im411opv3mm.getText().toString());
        json.put("im411opv3yy", bi.im411opv3yy.getText().toString().trim().isEmpty() ? "-1" : bi.im411opv3yy.getText().toString());

        json.put("im412penta3dd", bi.im412penta3dd.getText().toString().trim().isEmpty() ? "-1" : bi.im412penta3dd.getText().toString());
        json.put("im412penta3mm", bi.im412penta3mm.getText().toString().trim().isEmpty() ? "-1" : bi.im412penta3mm.getText().toString());
        json.put("im412penta3yy", bi.im412penta3yy.getText().toString().trim().isEmpty() ? "-1" : bi.im412penta3yy.getText().toString());

        json.put("im413pcv3dd", bi.im413pcv3dd.getText().toString().trim().isEmpty() ? "-1" : bi.im413pcv3dd.getText().toString());
        json.put("im413pcv3mm", bi.im413pcv3mm.getText().toString().trim().isEmpty() ? "-1" : bi.im413pcv3mm.getText().toString());
        json.put("im413pcv3yy", bi.im413pcv3yy.getText().toString().trim().isEmpty() ? "-1" : bi.im413pcv3yy.getText().toString());

        json.put("im414ipvdd", bi.im414ipvdd.getText().toString().trim().isEmpty() ? "-1" : bi.im414ipvdd.getText().toString());
        json.put("im414ipvmm", bi.im414ipvmm.getText().toString().trim().isEmpty() ? "-1" : bi.im414ipvmm.getText().toString());
        json.put("im414ipvyy", bi.im414ipvyy.getText().toString().trim().isEmpty() ? "-1" : bi.im414ipvyy.getText().toString());

        json.put("im415measles1dd", bi.im415measles1dd.getText().toString().trim().isEmpty() ? "-1" : bi.im415measles1dd.getText().toString());
        json.put("im415measles1mm", bi.im415measles1mm.getText().toString().trim().isEmpty() ? "-1" : bi.im415measles1mm.getText().toString());
        json.put("im415measles1yy", bi.im415measles1yy.getText().toString().trim().isEmpty() ? "-1" : bi.im415measles1yy.getText().toString());

        json.put("im416measles2dd", bi.im416measles2dd.getText().toString().trim().isEmpty() ? "-1" : bi.im416measles2dd.getText().toString());
        json.put("im416measles2mm", bi.im416measles2mm.getText().toString().trim().isEmpty() ? "-1" : bi.im416measles2mm.getText().toString());
        json.put("im416measles2yy", bi.im416measles2yy.getText().toString().trim().isEmpty() ? "-1" : bi.im416measles2yy.getText().toString());

        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(personal.getsI()), json);

            personal.setsI(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean formValidation() {
        if (!imFlag) {
            Toast.makeText(this, "Invalid date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        /*if (bi.im011.isChecked() && (TextUtils.isEmpty(bi.frontFileName.getText()) || TextUtils.isEmpty(bi.backFileName.getText()))) {
            Toast.makeText(this, "No Photos attached", Toast.LENGTH_SHORT).show();
            return false;
        }*/
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
            startActivity(new Intent(this, SectionIM3Activity.class));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    private Pair<String, String> getMonthAndYearFromDate(String date) {
        Calendar cal = DateUtils.getCalendarDate(date.replace("-", "/"));
        int curdate = form.getLocalDate().getDayOfMonth();
        int curmonth = form.getLocalDate().getMonthValue();
        int curyear = form.getLocalDate().getYear();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        if (day > curdate) {
            curmonth -= 1;
        }
        if (month > curmonth) {
            curyear -= 1;
            curmonth += 12;
        }
        return new Pair<>(String.valueOf(curmonth - month), String.valueOf(curyear - year));
    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, PIEndingActivity.class);
    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

/*    public void takePhoto(int id) {
        Intent intent = new Intent(this, TakePhoto.class);
        intent.putExtra("picID", personal.getHh12() + "_" + personal.getHh13() + "_" + personal.getMemberSerial() + "_");
        intent.putExtra("childName", personal.getMemberName());
        if (id == 1) {
            intent.putExtra("picView", "front".toUpperCase());
            startActivityForResult(intent, 1); // Activity is started with requestCode 1 = Front
        } else {
            intent.putExtra("picView", "back".toUpperCase());
            startActivityForResult(intent, 2); // Activity is started with requestCode 2 = Back
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, requestCode + "_" + resultCode, Toast.LENGTH_SHORT).show();

            String fileName = data.getStringExtra("FileName");

            // Check if the requestCode 1 = Front : 2 = Back -- resultCode 1 = Success : 2 = Failure
            // Results received with requestCode 1 = Front
            if (requestCode == 1 && resultCode == 1) {
                Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
                bi.frontFileName.setText(fileName);
                bi.frontPhoto.setEnabled(false);
            } else if (requestCode == 1) {
                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
                bi.frontFileName.setText("Photo not taken.");
            }

            // Results received with requestCode 2 = Back
            if (requestCode == 2 && resultCode == 1) {
                Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();
                bi.backFileName.setText(fileName);
                bi.backPhoto.setEnabled(false);
            } else if (requestCode == 2) {
                Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();
                bi.backFileName.setText("Photo not taken.");
            }
        }
    }*/

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
