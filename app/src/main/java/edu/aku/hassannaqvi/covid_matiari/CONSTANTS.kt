package edu.aku.hassannaqvi.covid_matiari

class CONSTANTS {
    companion object {
        //For Login
        const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0
        const val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 2
        const val TWO_MINUTES = 1000 * 60 * 2
        const val MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Long = 1 // in Meters
        const val MINIMUM_TIME_BETWEEN_UPDATES: Long = 1000 // in Milliseconds

        const val MEMBER_ITEM = 101
        const val SERIAL_EXTRA = "key"
        const val MINYEAR = 1940
        const val MAXYEAR = 2020

        const val SYNC_LOGIN = "sync_login"

        //For CHC Section
        const val IM03FLAG = "im03_flag"
        const val IM01CARDSEEN = "im01_card_seen"

        //Login Result Code
        const val LOGIN_RESULT_CODE = 10101
        const val LOGIN_SPLASH_FLAG = "splash_flag"

        //SubInfo
        const val SUB_INFO_END_FLAG = "sub_info_end_flag"
        const val FSTATUS_END_FLAG = "fstatus_end_flag"

        //Child Activity
        const val CHILD_ENDING_AGE_ISSUE = "childAgeIssue"
        const val CHILD_NO_ANSWER = "childNoAns"
        const val MEMBER_SERIAL = "serial_extra"

        //Dashboard Activity
        const val ROUTE_SUBINFO = "complete_current_section"

    }
}