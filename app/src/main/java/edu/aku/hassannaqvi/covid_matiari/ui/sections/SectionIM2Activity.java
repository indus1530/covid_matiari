package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.edittextpicker.aliazaz.EditTextPicker;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionIm2Binding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.JSONUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.DateRepository;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.DateUtils;
import edu.aku.hassannaqvi.covid_matiari.utils.date_utils.model.AgeModel;
import kotlin.Pair;

import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.form;
import static edu.aku.hassannaqvi.covid_matiari.core.MainApp.personal;
import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;


public class SectionIM2Activity extends AppCompatActivity implements EndSectionActivity {

    ActivitySectionIm2Binding bi;
    boolean im03Flag = false, imFlag = true;
    boolean im07;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_im2);
        bi.setCallback(this);
        setupTextWatchers();
        setupYears();

        //imo7 Check
/*        im07 = getIntent().getBooleanExtra(IM01CARDSEEN, false);
        if (im07) {
            Clear.clearAllFields(bi.fldGrpCVim07, false);
            bi.im071.setChecked(true);
        }*/

    }

    private void setupYears() {
        EditTextPicker[] yearEdit = new EditTextPicker[]{
                bi.im41bcgyy
                , bi.im42opv0yy
                , bi.im43opv1yy
                , bi.im44penta1yy
                , bi.im45pcv1yy
                , bi.im46rv1yy
                , bi.im47opv2yy
                , bi.im48penta2yy
                , bi.im49pcv2yy
                , bi.im410rv2yy
                , bi.im411opv3yy
                , bi.im412penta3yy
                , bi.im413pcv3yy
                , bi.im414ipvyy
                , bi.im415measles1yy
                , bi.im416measles2yy};

        if (form.getCalculatedDOB() != null) {
            int maxYears = form.getCalculatedDOB().getYear();
            int minYears = form.getCalculatedDOB().minusYears(2).getYear();

            for (EditTextPicker edit : yearEdit) {
                edit.setMinvalue(minYears);
                edit.setMaxvalue(maxYears);
            }
        }

    }

    private LocalDate getLocalDate(EditTextPicker[] editTextsArray) {
        if (editTextsArray.length < 3) return null;
        EditTextPicker editTextPicker01 = editTextsArray[0];
        EditTextPicker editTextPicker02 = editTextsArray[1];
        EditTextPicker editTextPicker03 = editTextsArray[2];
        if (TextUtils.isEmpty(editTextPicker01.getText()) || TextUtils.isEmpty(editTextPicker02.getText()) || TextUtils.isEmpty(editTextPicker03.getText()))
            return null;
        String txt01 = editTextPicker01.getText().toString();
        if (txt01.trim().equals("44") || txt01.trim().equals("97") || txt01.trim().equals("66") || txt01.trim().equals("88"))
            return null;
        try {
            String txt02 = editTextPicker02.getText().toString();
            String txt03 = editTextPicker03.getText().toString();
            Instant instant = Instant.parse(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd-MM-yyyy").parse(
                    txt01 + "-" + txt02 + "-" + txt03
            )) + "T06:24:01Z");
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearEditTextFields(ArrayList<EditTextPicker[]> respArrayList) {
        if (respArrayList == null) return;
        for (EditTextPicker[] editItems : respArrayList) {
            for (EditTextPicker items : editItems) {
                items.setText(null);
                items.setError(null);
            }
        }
    }

    private void setupTextWatchers() {

        EditTextPicker[] BCG = new EditTextPicker[]{bi.im41bcgdd, bi.im41bcgmm, bi.im41bcgyy};
        editTextImplementation("BCG", BCG, null, null);

        EditTextPicker[] OPV0 = new EditTextPicker[]{bi.im42opv0dd, bi.im42opv0mm, bi.im42opv0yy};
        EditTextPicker[] OPV1 = new EditTextPicker[]{bi.im43opv1dd, bi.im43opv1mm, bi.im43opv1yy};
        EditTextPicker[] OPV2 = new EditTextPicker[]{bi.im47opv2dd, bi.im47opv2mm, bi.im47opv2yy};
        EditTextPicker[] OPV3 = new EditTextPicker[]{bi.im411opv3dd, bi.im411opv3mm, bi.im411opv3yy};
        editTextImplementation("OPV0", OPV0, null, new ArrayList<EditTextPicker[]>() {
            {
                add(OPV1);
                add(OPV2);
                add(OPV3);
            }
        });
        editTextImplementation("OPV1", OPV1, OPV0, null);
        editTextImplementation("OPV2", OPV2, OPV1, null);
        editTextImplementation("OPV3", OPV3, OPV2, null);

        EditTextPicker[] PENTA1 = new EditTextPicker[]{bi.im44penta1dd, bi.im44penta1mm, bi.im44penta1yy};
        EditTextPicker[] PENTA2 = new EditTextPicker[]{bi.im48penta2dd, bi.im48penta2mm, bi.im48penta2yy};
        EditTextPicker[] PENTA3 = new EditTextPicker[]{bi.im412penta3dd, bi.im412penta3mm, bi.im412penta3yy};
        editTextImplementation("PENTA1", PENTA1, null, new ArrayList<EditTextPicker[]>() {
            {
                add(PENTA2);
                add(PENTA3);
            }
        });
        editTextImplementation("PENTA2", PENTA2, PENTA1, null);
        editTextImplementation("PENTA3", PENTA3, PENTA2, null);

        EditTextPicker[] PCV1 = new EditTextPicker[]{bi.im45pcv1dd, bi.im45pcv1mm, bi.im45pcv1yy};
        EditTextPicker[] PCV2 = new EditTextPicker[]{bi.im49pcv2dd, bi.im49pcv2mm, bi.im49pcv2yy};
        EditTextPicker[] PCV3 = new EditTextPicker[]{bi.im413pcv3dd, bi.im413pcv3mm, bi.im413pcv3yy};
        editTextImplementation("PCV1", PCV1, null, new ArrayList<EditTextPicker[]>() {
            {
                add(PCV2);
                add(PCV3);
            }
        });
        editTextImplementation("PCV2", PCV2, PCV1, null);
        editTextImplementation("PCV3", PCV3, PCV2, null);

        EditTextPicker[] RV1 = new EditTextPicker[]{bi.im46rv1dd, bi.im46rv1mm, bi.im46rv1yy};
        EditTextPicker[] RV2 = new EditTextPicker[]{bi.im410rv2dd, bi.im410rv2mm, bi.im410rv2yy};
        editTextImplementation("RV1", RV1, null, new ArrayList<EditTextPicker[]>() {
            {
                add(RV2);
            }
        });
        editTextImplementation("RV2", RV2, RV1, null);

        EditTextPicker[] IPV = new EditTextPicker[]{bi.im414ipvdd, bi.im414ipvmm, bi.im414ipvyy};
        editTextImplementation("IPV", IPV, null, null);

        EditTextPicker[] MEASLES1 = new EditTextPicker[]{bi.im415measles1dd, bi.im415measles1mm, bi.im415measles1yy};
        EditTextPicker[] MEASLES2 = new EditTextPicker[]{bi.im416measles2dd, bi.im416measles2mm, bi.im416measles2yy};
        editTextImplementation("MEASLES1", MEASLES1, null, new ArrayList<EditTextPicker[]>() {
            {
                add(MEASLES2);
            }
        });
        editTextImplementation("MEASLES2", MEASLES2, MEASLES1, null);
    }

    public void editTextImplementation(String type, EditTextPicker[] editTextsArray, EditTextPicker[] respTextsArray, ArrayList<EditTextPicker[]> respArrayList) {
        if (editTextsArray.length != 3) return;
        EditTextPicker editTextPicker01 = editTextsArray[0];
        EditTextPicker editTextPicker02 = editTextsArray[1];
        EditTextPicker editTextPicker03 = editTextsArray[2];

        if (form.getCalculatedDOB() != null) {
            int minYears = form.getCalculatedDOB().getYear();
            int maxYears = form.getLocalDate().getYear();
            editTextPicker03.setMinvalue(minYears);
            editTextPicker03.setMaxvalue(maxYears);
        }

        editTextPicker01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextPicker03.setText(null);
                editTextPicker03.setError(null);

                editTextPicker02.setEnabled(true);
                editTextPicker03.setEnabled(true);

                daysFlag = true;
                imFlag = true;

                String txt01;
                if (!TextUtils.isEmpty(editTextPicker01.getText())) {
                    txt01 = editTextPicker01.getText().toString();

                    if (txt01.trim().equals("44") || txt01.trim().equals("97") || txt01.trim().equals("66") || txt01.trim().equals("88")) {
                        editTextPicker02.setText(null);
                        editTextPicker03.setText(null);
                        editTextPicker02.setEnabled(false);
                        editTextPicker03.setEnabled(false);
                        editTextPicker01.setRangedefaultvalue(Float.parseFloat(txt01));

                        daysFlag = false;
                        imFlag = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPicker02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editTextPicker03.setText(null);
                editTextPicker03.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPicker03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String txt01, txt02, txt03;
                    LocalDate respLocalDate = null;
                    if (!daysFlag) return;
                    editTextPicker01.setEnabled(true);
                    editTextPicker02.setEnabled(true);
                    if (!TextUtils.isEmpty(editTextPicker01.getText()) && !TextUtils.isEmpty(editTextPicker02.getText()) && !TextUtils.isEmpty(editTextPicker03.getText())) {
                        txt01 = editTextPicker01.getText().toString();
                        txt02 = editTextPicker02.getText().toString();
                        txt03 = editTextPicker03.getText().toString();
                    } else return;
                    clearEditTextFields(respArrayList);
                    if (respTextsArray != null) {
                        respLocalDate = getLocalDate(respTextsArray);
                        int minYears, maxYears;
                        if (respLocalDate != null) {
                            minYears = respLocalDate.getYear();
                        } else {
                            minYears = form.getCalculatedDOB().getYear();
                        }
                        maxYears = form.getLocalDate().getYear();
                        editTextPicker03.setMinvalue(minYears);
                        editTextPicker03.setMaxvalue(maxYears);
                    }
                    if ((!editTextPicker01.isRangeTextValidate() || txt01.trim().equals("44") || txt01.trim().equals("97") || txt01.trim().equals("66") || txt01.trim().equals("88")) ||
                            (!editTextPicker02.isRangeTextValidate()) ||
                            (!editTextPicker03.isRangeTextValidate()))
                        return;
                    int day = Integer.parseInt(txt01);
                    int month = Integer.parseInt(txt02);
                    int year = Integer.parseInt(txt03);

                    AgeModel age;
                    age = DateRepository.Companion.getCalculatedAge(form.getLocalDate(), Integer.parseInt(editTextsArray[2].getText().toString()), Integer.parseInt(editTextsArray[1].getText().toString()), Integer.parseInt(editTextsArray[0].getText().toString()));
                    if (age == null) {
                        editTextPicker03.setError("Invalid date!!");
                        imFlag = false;
                        return;
                    }
                    if (respLocalDate != null) {
                        age = DateRepository.Companion.getCalculatedAge(Objects.requireNonNull(getLocalDate(editTextsArray)), Integer.parseInt(respTextsArray[2].getText().toString()), Integer.parseInt(respTextsArray[1].getText().toString()), Integer.parseInt(respTextsArray[0].getText().toString()), false);
                    } else if (form.getCalculatedDOB() != null)
                        age = DateRepository.Companion.getCalculatedAge(Objects.requireNonNull(getLocalDate(editTextsArray)), form.getCalculatedDOB().getYear(), form.getCalculatedDOB().getMonthValue(), form.getCalculatedDOB().getDayOfMonth(), false);
                    else
                        age = DateRepository.Companion.getCalculatedAge(year, month, day);
                    if (age == null) {
                        editTextPicker03.setError("Invalid date!!");
                        imFlag = false;
                    } else {
                        imFlag = true;
                        editTextPicker01.setEnabled(false);
                        editTextPicker02.setEnabled(false);
                    }
                } catch (Exception ignored) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
