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
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.Calendar;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionIm1Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.DateUtils;
import kotlin.Pair;

import static edu.aku.hassannaqvi.covid_matiari.CONSTANTS.IM01CARDSEEN;
import static edu.aku.hassannaqvi.covid_matiari.CONSTANTS.IM03FLAG;
import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.openWarningActivity;


public class SectionIM1Activity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionIm1Binding bi;
    boolean im03Flag = false, imFlag = true;
    Instant dtInstant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_im1);
        bi.setCallback(this);

        if (form.getLocalDate() != null) {
            int maxYears = form.getLocalDate().getYear();
            int minYears = form.getLocalDate().minusYears(5).getYear();
            setYearOfBirth(minYears, maxYears);
        }

    }

    private void setYearOfBirth(int minYears, int maxYears) {
        bi.im04yy.setMinvalue(minYears);
        bi.im04yy.setMaxvalue(maxYears);
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_SI, MainApp.personal.getsI());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("im01", bi.im011.isChecked() ? "1"
                : bi.im012.isChecked() ? "2"
                : bi.im013.isChecked() ? "3"
                : bi.im015.isChecked() ? "5"
                : bi.im014.isChecked() ? "4"
                : "-1");

        json.put("im02", bi.im021.isChecked() ? "1"
                : bi.im022.isChecked() ? "2"
                : "-1");

        json.put("im02a", bi.im02a1.isChecked() ? "1"
                : bi.im02a2.isChecked() ? "2"
                : bi.im02a3.isChecked() ? "3"
                : bi.im02a4.isChecked() ? "4"
                : bi.im02a5.isChecked() ? "5"
                : bi.im02a6.isChecked() ? "6"
                : bi.im02a96.isChecked() ? "96"
                : "-1");

        json.put("im04dd", bi.im04dd.getText().toString().trim().isEmpty() ? "-1" : bi.im04dd.getText().toString());
        json.put("im04mm", bi.im04mm.getText().toString().trim().isEmpty() ? "-1" : bi.im04mm.getText().toString());
        json.put("im04yy", bi.im04yy.getText().toString().trim().isEmpty() ? "-1" : bi.im04yy.getText().toString());
        json.put("im04dd1", bi.im04dd1.isChecked() ? "98" : "-1");

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

        json.put("im05", bi.im051.isChecked() ? "1"
                : bi.im052.isChecked() ? "2"
                : bi.im053.isChecked() ? "98"
                : "-1");

        json.put("im06", bi.im061.isChecked() ? "1"
                : bi.im062.isChecked() ? "2"
                : "-1");

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

        personal.setsI(String.valueOf(json));

        if (dtInstant != null)
            form.setCalculatedDOB(LocalDateTime.ofInstant(dtInstant, ZoneId.systemDefault()).toLocalDate());
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

        if (formValidation()) {
            //Calculate months
            boolean monthFlag = true;
            if (form.getCalculatedDOB() != null || dtInstant != null) {
                Pair<String, String> month_year;
                if (bi.im021.isChecked() && dtInstant != null)
                    month_year = getMonthAndYearFromDate(LocalDateTime.ofInstant(dtInstant, ZoneId.systemDefault()).toLocalDate().toString());
                else month_year = getMonthAndYearFromDate(form.getCalculatedDOB().toString());
                int totalMonths = Integer.parseInt(month_year.getFirst()) + Integer.parseInt(month_year.getSecond()) * 12;
                monthFlag = totalMonths < 60;
            }
            if (monthFlag) {
                try {
                    SaveDraft();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (UpdateDB()) {
                    finish();
                    startActivity(new Intent(this, SectionCHDActivity.class).putExtra(IM03FLAG, !im03Flag).putExtra(IM01CARDSEEN, bi.im021.isChecked() || bi.im022.isChecked()));
                } else {
                    Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
                }
            } else
                openWarningActivity(this, "Current Child age leads to End this form.\nDo you want to Continue?");

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
