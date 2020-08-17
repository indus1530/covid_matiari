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

        /*if (form.getLocalDate() != null) {
            int maxYears = form.getLocalDate().getYear();
            int minYears = form.getLocalDate().minusYears(5).getYear();
            setYearOfBirth(minYears, maxYears);
        }*/

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
