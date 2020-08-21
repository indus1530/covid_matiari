package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.edittextpicker.aliazaz.EditTextPicker;
import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionH5Binding;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

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

        bi.h501.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.h501b.getId()) {
                Clear.clearAllFields(bi.fldGrpSectionh1);
            }
        }));

        editTextImplementation(bi.h50301x, bi.h50301xx);
        editTextImplementation(bi.h50302x, bi.h50302xx);
        editTextImplementation(bi.h50303x, bi.h50303xx);
        editTextImplementation(bi.h50304x, bi.h50304xx);
        editTextImplementation(bi.h50305x, bi.h50305xx);
        editTextImplementation(bi.h50306x, bi.h50306xx);
        editTextImplementation(bi.h50307x, bi.h50307xx);
        editTextImplementation(bi.h50308x, bi.h50308xx);
        editTextImplementation(bi.h50309x, bi.h50309xx);
        editTextImplementation(bi.h503010x, bi.h503010xx);
        editTextImplementation(bi.h503011x, bi.h503011xx);
        editTextImplementation(bi.h503096x, bi.h503096xx);
    }


    public void editTextImplementation(EditTextPicker edit01, EditTextPicker edit02) {

        edit01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(edit01.getText()))
                    return;
                edit02.setMaxvalue(Integer.parseInt(edit01.getText().toString().trim()));
            }
        });

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
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesFormColumn(FormsContract.FormsTable.COLUMN_SH5, MainApp.form.getsH5());
        return updcount == 1;

    }


    private void SaveDraft() throws JSONException {

        JSONObject sH5 = new JSONObject();

        sH5.put("h501", bi.h501a.isChecked() ? "1"
                : bi.h501b.isChecked() ? "2"
                : "-1");

        sH5.put("h502", bi.h502.getText().toString());

        sH5.put("h50301", bi.h50301.isChecked() ? "1" : "-1");
        sH5.put("h50301x", bi.h50301x.getText().toString());
        sH5.put("h50301xx", bi.h50301xx.getText().toString());

        sH5.put("h50302", bi.h50302.isChecked() ? "2" : "-1");
        sH5.put("h50302x", bi.h50302x.getText().toString());
        sH5.put("h50302xx", bi.h50302xx.getText().toString());

        sH5.put("h50303", bi.h50303.isChecked() ? "3" : "-1");
        sH5.put("h50303x", bi.h50303x.getText().toString());
        sH5.put("h50303xx", bi.h50303xx.getText().toString());

        sH5.put("h50304", bi.h50304.isChecked() ? "4" : "-1");
        sH5.put("h50304x", bi.h50304x.getText().toString());
        sH5.put("h50304xx", bi.h50304xx.getText().toString());

        sH5.put("h50305", bi.h50305.isChecked() ? "5" : "-1");
        sH5.put("h50305x", bi.h50305x.getText().toString());
        sH5.put("h50305xx", bi.h50305xx.getText().toString());

        sH5.put("h50306", bi.h50306.isChecked() ? "6" : "-1");
        sH5.put("h50306x", bi.h50306x.getText().toString());
        sH5.put("h50306xx", bi.h50306xx.getText().toString());

        sH5.put("h50307", bi.h50307.isChecked() ? "7" : "-1");
        sH5.put("h50307x", bi.h50307x.getText().toString());
        sH5.put("h50307xx", bi.h50307xx.getText().toString());

        sH5.put("h50308", bi.h50308.isChecked() ? "8" : "-1");
        sH5.put("h50308x", bi.h50308x.getText().toString());
        sH5.put("h50308xx", bi.h50308xx.getText().toString());

        sH5.put("h50309", bi.h50309.isChecked() ? "9" : "-1");
        sH5.put("h50309x", bi.h50309x.getText().toString());
        sH5.put("h50309xx", bi.h50309xx.getText().toString());

        sH5.put("h503010", bi.h503010.isChecked() ? "10" : "-1");
        sH5.put("h503010x", bi.h503010x.getText().toString());
        sH5.put("h503010xx", bi.h503010xx.getText().toString());

        sH5.put("h503011", bi.h503011.isChecked() ? "11" : "-1");
        sH5.put("h503011x", bi.h503011x.getText().toString());
        sH5.put("h503011xx", bi.h503011xx.getText().toString());

        sH5.put("h503096", bi.h503096.isChecked() ? "96" : "-1");
        sH5.put("h503096x", bi.h503096x.getText().toString());
        sH5.put("h503096xx", bi.h503096xx.getText().toString());


        MainApp.form.setsH5((String.valueOf(sH5)));

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