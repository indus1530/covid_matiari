package edu.aku.hassannaqvi.covid_matiari.ui.sections

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.aku.hassannaqvi.covid_matiari.CONSTANTS
import edu.aku.hassannaqvi.covid_matiari.R
import edu.aku.hassannaqvi.covid_matiari.adapter.MemberListAdapter
import edu.aku.hassannaqvi.covid_matiari.core.MainApp
import edu.aku.hassannaqvi.covid_matiari.databinding.ActivitySectionSubInfoBinding
import edu.aku.hassannaqvi.covid_matiari.models.Personal
import edu.aku.hassannaqvi.covid_matiari.ui.other.EndingActivity
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.EndSectionActivity
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.contextEndActivity
import edu.aku.hassannaqvi.covid_matiari.viewmodel.MainVModel
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

class SectionSubInfoActivity : AppCompatActivity(), EndSectionActivity {

    private lateinit var bi: ActivitySectionSubInfoBinding
    private var flagNewForm = false
    private var flagInCompleteForm = false
    private var hhFlag = false
    private var serial = 0
    private var memFlag = false
    private lateinit var adapter: MemberListAdapter

    companion object {
        lateinit var mainVModel: MainVModel
        var istatusFlag = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_sub_info)
        bi.callback = this
        bi.formScroll.callback = this
        mainVModel = this.run {
            ViewModelProvider(this).get(MainVModel::class.java)
        }
        bi.txtCluster.text = MainApp.form.hh12
        bi.txtHHNo.text = MainApp.form.hh13
        istatusFlag = 0

        setupRecyclerView(mutableListOf())

        mainVModel.members.observe(this, Observer {
            serial = it.size + 1
            adapter.setMList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        setUI()
    }

    fun onHHViewClick() {
        if (!hhFlag) return
        startActivity(Intent(this, SectionH2Activity::class.java))
    }

    fun onChildViewClick() {
//        if (!memFlag) return
        startActivity(Intent(this, SectionPIAActivity::class.java).putExtra(CONSTANTS.MEMBER_SERIAL, serial))
    }

    fun onFabBtnMenuClick() {
        SheetMenu(
                "Select your Action",
                listOf(
                        ActionItem(
                                0,
                                "Force Stop",
                                getDrawable(R.drawable.ic_warning_black_24dp)
                        )
                        ,
                        ActionItem(
                                1,
                                "End Household",
                                getDrawable(R.drawable.ic_closed_caption_black_24dp)
                        )
                ),
                onClick = { item: ActionItem ->
                    run {
                        when (item.id) {
                            0 -> {
                                if (flagNewForm || flagInCompleteForm) return@run
                                contextEndActivity(this, false)
                            }
                            else -> {
                                if (flagNewForm) return@run
                                endActivityStatus()
                            }
                        }
                    }

                }, layoutProvider = GridLayoutProvider()
        ).show(this)

    }

    private fun setUI() {
        when (istatusFlag) {
            0 -> {
                hhFlag = true
                memFlag = false
                flagNewForm = true
                bi.instruction.text = getString(R.string.hhformInfo)
            }
            1 -> {
                bi.formScroll.hhScroll.name.text = "HOUSEHOLD FORM COMPLETED"
                bi.formScroll.hhScroll.status.visibility = View.VISIBLE
                hhFlag = false
                memFlag = true
                flagNewForm = false
                bi.instruction.text = getString(R.string.memberforminfo)
            }
            88 -> endFormHH()
            99 -> endFormHH()
        }


    }

    private fun endFormHH() {
        bi.formScroll.hhScroll.name.text = "HOUSEHOLD FORM COMPLETED"
        bi.formScroll.childScroll.name.text = "MEMBER FORM IS BLOCKED\nContact Team Leader"
        bi.formScroll.hhScroll.status.visibility = View.VISIBLE
        hhFlag = false
        memFlag = false
        flagNewForm = false
        flagInCompleteForm = true
        bi.instruction.text = getString(R.string.end_interview)
    }

    override fun endSecActivity(flag: Boolean) {
        finish()
        startActivity(Intent(this, EndingActivity::class.java)
                .putExtra("complete", flag)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    private fun endActivityStatus() {
        contextEndActivity(this@SectionSubInfoActivity, istatusFlag != 99)
    }

    private fun setupRecyclerView(membersLst: MutableList<Personal>) {
        adapter = MemberListAdapter(this, membersLst)
        bi.formScroll.recyclerViewChildren.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bi.formScroll.recyclerViewChildren.adapter = adapter
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Sorry. You can't able to go back!!", Toast.LENGTH_SHORT).show()
    }
}
