package edu.aku.hassannaqvi.covid_matiari.data

import edu.aku.hassannaqvi.covid_matiari.models.Users
import java.util.*

fun userTestData(username: String): Users {

    val users = Users()
    users.userName = username
    users.fullname = username.toUpperCase(Locale.getDefault())

    return users
}