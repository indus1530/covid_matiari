package edu.aku.hassannaqvi.covid_matiari.ui.other

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.validatorcrawler.aliazaz.Validator
import edu.aku.hassannaqvi.covid_matiari.R
import edu.aku.hassannaqvi.covid_matiari.core.MainApp
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivityPiEndingBinding
import edu.aku.hassannaqvi.covid_matiari.ui.sections.SectionSubInfoActivity
import java.text.SimpleDateFormat
import java.util.*

class PIEndingActivity : AppCompatActivity() {
    lateinit var bi: ActivityPiEndingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_pi_ending)
        bi.callback = this

        val check = intent.getBooleanExtra("complete", false)
        if (check) {
            bi.istatus01.isEnabled = true
            bi.istatus02.isEnabled = false
            bi.istatus03.isEnabled = false
            bi.istatus96.isEnabled = false
        } else {
            bi.istatus01.isEnabled = false
            bi.istatus02.isEnabled = true
            bi.istatus03.isEnabled = true
            bi.istatus96.isEnabled = true
        }
    }

    fun BtnEnd() {
        if (!formValidation()) return
        saveDraft()
        if (updateDB()) {
            SectionSubInfoActivity.mainVModel.setMemberList(MainApp.personal)
            SectionSubInfoActivity.istatusFlag = 1
            finish()
//            startActivity(Intent(this, SectionSubInfoActivity::class.java).putExtra(ROUTE_SUBINFO, 1))
        } else {
            Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDraft() {
        MainApp.personal.cstatus = if (bi.istatus01.isChecked) "1" else if (bi.istatus02.isChecked) "2" else if (bi.istatus03.isChecked) "3" else if (bi.istatus96.isChecked) "96" else "0"
        MainApp.personal.cstatus96x = bi.istatus96x.text.toString()
        MainApp.personal.endingdatetime = SimpleDateFormat("dd-MM-yy HH:mm").format(Date().time)
    }


    private fun updateDB(): Boolean {
        val db = MainApp.appInfo.dbHelper
        val updcount = db.updateMemberEnding()
        return if (updcount == 1) {
            true
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun formValidation(): Boolean {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd)
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "You Can't go back", Toast.LENGTH_LONG).show()
    }
}