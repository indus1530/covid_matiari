package edu.aku.hassannaqvi.covid_matiari.models

data class HHModel @JvmOverloads constructor(var clusterCode: String, var hhno: String, var memAge: Int, var genderFemale: Boolean, var married: Boolean = false)