package edu.aku.hassannaqvi.covid_matiari.viewmodel

import android.content.Context
import edu.aku.hassannaqvi.covid_matiari.core.DatabaseHelper
import edu.aku.hassannaqvi.covid_matiari.models.Form
import edu.aku.hassannaqvi.covid_matiari.models.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

suspend fun getAllHHChildFromDB(context: Context, form: Form): MutableList<Personal> = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.checkAllPersonalExist(form.hh12, form.hh13, form._UID)
}

suspend fun populatingChild(context: Context, form: Form) {
    coroutineScope {
        val def = getAllHHChildFromDB(context, form)
    }
}