package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.net.ParseException;
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
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionIm1Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.DateRepository;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.DateUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.model.AgeModel;
import kotlin.Pair;

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
        setupSkips();

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


    private void setupSkips() {

        bi.im01.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.cvim02);
            Clear.clearAllFields(bi.cvim02a);
            Clear.clearAllFields(bi.cvim04);
            bi.cvim02.setVisibility(View.GONE);
            bi.cvim02a.setVisibility(View.GONE);
            bi.cvim04.setVisibility(View.GONE);
            if (i == bi.im011.getId() || i == bi.im013.getId() || i == bi.im015.getId()) {
                bi.cvim04.setVisibility(View.VISIBLE);
            } else if (i == bi.im012.getId() || i == bi.im014.getId()) {
                bi.cvim02.setVisibility(View.VISIBLE);
            }
        });

        bi.im02.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.cvim02a);
            Clear.clearAllFields(bi.cvim04);
            bi.cvim02a.setVisibility(View.GONE);
            bi.cvim04.setVisibility(View.GONE);
            if (i == bi.im021.getId()) {
                bi.cvim04.setVisibility(View.VISIBLE);
            } else if (i == bi.im022.getId()) {
                bi.cvim02a.setVisibility(View.VISIBLE);
            }
        });


        bi.im04yy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dtInstant = null;
                imFlag = true;
                if (!bi.im011.isChecked() || bi.im04dd1.isChecked()) return;
                String txt01, txt02, txt03;
                bi.im04dd.setEnabled(true);
                bi.im04mm.setEnabled(true);
                if (!TextUtils.isEmpty(bi.im04dd.getText()) && !TextUtils.isEmpty(bi.im04mm.getText()) && !TextUtils.isEmpty(bi.im04yy.getText())) {
                    txt01 = bi.im04dd.getText().toString();
                    txt02 = bi.im04mm.getText().toString();
                    txt03 = bi.im04yy.getText().toString();
                } else return;
                if ((!bi.im04dd.isRangeTextValidate()) ||
                        (!bi.im04mm.isRangeTextValidate()) ||
                        (!bi.im04yy.isRangeTextValidate()))
                    return;
                int day = bi.im04dd.getText().toString().equals("98") ? 15 : Integer.parseInt(txt01);
                int month = Integer.parseInt(txt02);
                int year = Integer.parseInt(txt03);

                AgeModel age;
                if (form.getLocalDate() != null)
                    age = DateRepository.Companion.getCalculatedAge(form.getLocalDate(), year, month, day);
                else
                    age = DateRepository.Companion.getCalculatedAge(year, month, day);
                if (age == null) {
                    bi.im04yy.setError("Invalid date!!");
                    imFlag = false;
                } else {
                    imFlag = true;
                    bi.im04dd.setEnabled(false);
                    bi.im04mm.setEnabled(false);
                    //Setting Date
                    try {
                        dtInstant = Instant.parse(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(
                                bi.im04dd.getText().toString() + "-" + bi.im04mm.getText().toString() + "-" + bi.im04yy.getText().toString()
                        )) + "T06:24:01Z");

                    } catch (ParseException | java.text.ParseException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
    }

    private boolean formValidation() {
        if (!imFlag) {
            Toast.makeText(this, "Invalid date!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    public void BtnContinue() {

        if (formValidation()) {

            if (dtInstant != null)
                personal.setCalculatedDOB(LocalDateTime.ofInstant(dtInstant, ZoneId.systemDefault()).toLocalDate());

            //Calculate months
            boolean monthFlag = true;
            if (personal.getCalculatedDOB() != null) {
                Pair<String, String> month_year;
                if (bi.im021.isChecked() && dtInstant != null)
                    month_year = getMonthAndYearFromDate(LocalDateTime.ofInstant(dtInstant, ZoneId.systemDefault()).toLocalDate().toString());
                else month_year = getMonthAndYearFromDate(personal.getCalculatedDOB().toString());
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
                    startActivity(new Intent(this, bi.im04yy.getText().toString().trim().isEmpty() ? SectionIM3Activity.class : SectionIM2Activity.class));
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
