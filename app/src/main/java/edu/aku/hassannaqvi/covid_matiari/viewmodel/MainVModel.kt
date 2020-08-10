package edu.aku.hassannaqvi.covid_matiari.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.aku.hassannaqvi.covid_matiari.models.Form
import edu.aku.hassannaqvi.covid_matiari.models.Personal
import kotlinx.coroutines.launch

class MainVModel : ViewModel() {

    var members = MutableLiveData<MutableList<Personal>>()
        private set

    // Functions for Member viewmodel
    fun setMemberList(item: Personal) {
        var lst = members.value
        if (lst.isNullOrEmpty())
            lst = mutableListOf()
        lst.add(item)
        members.value = lst
    }

    fun populateChildListU5(context: Context, form: Form) {
        viewModelScope.launch {
            var lst = getAllHHChildFromDB(context, form)
            if (lst.isNullOrEmpty()) lst = mutableListOf()
            members.value = lst
        }
    }

}