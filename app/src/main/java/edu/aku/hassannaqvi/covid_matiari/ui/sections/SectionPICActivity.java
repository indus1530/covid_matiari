package edu.aku.hassannaqvi.covid_matiari.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.covid_matiari.R;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper;
import edu.aku.hassannaqvi.covid_matiari.core.MainApp;
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionPicBinding;
import edu.aku.hassannaqvi.covid_matiari.ui.other.PIEndingActivity;
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt;

import static edu.aku.hassannaqvi.covid_matiari.utils.app_utils.AppUtilsKt.contextBackActivity;

public class SectionPICActivity extends AppCompatActivity {

    ActivitySectionPicBinding bi;
    boolean stickerType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_pic);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {

        bi.pc01.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pc012.getId()) {
                Clear.clearAllFields(bi.fldGrpSectionC01);
            }
        }));

    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            startActivity(new Intent(this, MainApp.personal.getHhModel().getMemAge() < 5 ? SectionCHCActivity.class : PIEndingActivity.class).putExtra("complete", true));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesPersonalColumn(PersonalContract.PersonalTable.COLUMN_SC, MainApp.personal.getsC());
        return updcount == 1;
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("pc01", bi.pc011.isChecked() ? "1"
                : bi.pc012.isChecked() ? "2"
                : "-1");
        json.put("pc02", bi.pc02.getText().toString());
        json.put("pc02a", bi.pc02a.getText().toString());
        json.put("pc03", bi.pc03.getText().toString());
        json.put("pc03a", bi.pc03a1.isChecked() ? "1"
                : bi.pc03a2.isChecked() ? "2"
                : "-1");
        json.put("pc03b", bi.pc03b.getText().toString());
        json.put("pc04", bi.pc04.getText().toString());
        json.put("pc04a", bi.pc04a.getText().toString());
        json.put("pc05", bi.pc05.getText().toString());
        json.put("pc06", bi.pc06.getText().toString());


        MainApp.personal.setsC(json.toString());

    }

    public void BtnEnd() {
        AppUtilsKt.openEndActivity(this, PIEndingActivity.class);
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionC);

    }

    @Override
    public void onBackPressed() {
        contextBackActivity(this);
    }

    public void btnScan(int type) {
        stickerType = type == 1;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                .setPrompt("Scan sticker")
                .setCameraId(0)  // Use a specific camera of the device
                .setBeepEnabled(false)
                .setBarcodeImageEnabled(true)
                .setOrientationLocked(false)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                if (stickerType) {
                    bi.pc03.setText(null);
                    bi.pc03.setEnabled(true);
                } else {
                    bi.pc03b.setText(null);
                    bi.pc03b.setEnabled(true);
                }
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                if (stickerType) {
                    bi.pc03.setText(result.getContents().trim());
                    bi.pc03.setEnabled(false);
                } else {
                    bi.pc03b.setText(result.getContents().trim());
                    bi.pc03b.setEnabled(false);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}