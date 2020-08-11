package edu.aku.hassannaqvi.covid_matiari.utils.db_utils;

import edu.aku.hassannaqvi.covid_matiari.contracts.FormsContract.FormsTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.PersonalContract;
import edu.aku.hassannaqvi.covid_matiari.contracts.RandomContract.RandomTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.UsersContract.UsersTable;
import edu.aku.hassannaqvi.covid_matiari.contracts.VersionAppContract.VersionAppTable;

public final class CreateTable {

    public static final String DATABASE_NAME = "covid_sero.db";
    public static final String DB_NAME = "covid_sero_copy.db";
    public static final String PROJECT_NAME = "covid_sero";
    public static final int DATABASE_VERSION = 1;

    public static final String SQL_CREATE_FORMS = "CREATE TABLE "
            + FormsTable.TABLE_NAME + "("
            + FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
            + FormsTable.COLUMN_DEVICEID + " TEXT,"
            + FormsTable.COLUMN_DEVICETAGID + " TEXT,"
            + FormsTable.COLUMN_UID + " TEXT,"
            + FormsTable.COLUMN_SYSDATE + " TEXT,"
            + FormsTable.COLUMN_HH01 + " TEXT,"
            + FormsTable.COLUMN_HH02 + " TEXT,"
            + FormsTable.COLUMN_HH03 + " TEXT,"
            + FormsTable.COLUMN_HH12 + " TEXT,"
            + FormsTable.COLUMN_HH13 + " TEXT,"
            + FormsTable.COLUMN_REFNO + " TEXT,"
            + FormsTable.COLUMN_GPSLAT + " TEXT,"
            + FormsTable.COLUMN_GPSLNG + " TEXT,"
            + FormsTable.COLUMN_GPSDATE + " TEXT,"
            + FormsTable.COLUMN_GPSACC + " TEXT,"
            + FormsTable.COLUMN_APPVERSION + " TEXT,"
            + FormsTable.COLUMN_SINFO + " TEXT,"
            + FormsTable.COLUMN_SH2 + " TEXT,"
            + FormsTable.COLUMN_SH3 + " TEXT,"
            + FormsTable.COLUMN_SH4 + " TEXT,"
            + FormsTable.COLUMN_SH5 + " TEXT,"
            + FormsTable.COLUMN_ENDINGDATETIME + " TEXT,"
            + FormsTable.COLUMN_ISTATUS + " TEXT,"
            + FormsTable.COLUMN_ISTATUS96x + " TEXT,"
            + FormsTable.COLUMN_SYNCED + " TEXT,"
            + FormsTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";

    public static final String SQL_CREATE_PERSONALS = "CREATE TABLE "
            + PersonalContract.PersonalTable.TABLE_NAME + "("
            + PersonalContract.PersonalTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PersonalContract.PersonalTable.COLUMN_PROJECT_NAME + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_DEVICEID + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_DEVICETAGID + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_UID + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SYSDATE + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_A01 + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_A02 + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_A03 + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_HH12 + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_HH13 + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_UUID + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_GPSLAT + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_GPSLNG + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_GPSDATE + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_GPSACC + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_APPVERSION + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SA + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SB + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SC + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SI + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_ENDINGDATETIME + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_CSTATUS + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_CSTATUS96x + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SYNCED + " TEXT,"
            + PersonalContract.PersonalTable.COLUMN_SYNCED_DATE + " TEXT"
            + " );";

    public static final String SQL_CREATE_USERS = "CREATE TABLE " + UsersTable.TABLE_NAME + "("
            + UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UsersTable.COLUMN_USERNAME + " TEXT,"
            + UsersTable.COLUMN_PASSWORD + " TEXT,"
            + UsersTable.DIST_ID + " TEXT,"
            + UsersTable.FULL_NAME + " TEXT"
            + " );";

    public static final String SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionAppTable.TABLE_NAME + " (" +
            VersionAppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
            VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
            VersionAppTable.COLUMN_PATH_NAME + " TEXT " +
            ");";

    public static final String SQL_CREATE_RANDOM = "CREATE TABLE " + RandomTable.TABLE_NAME + " (" +
            RandomTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            RandomTable.COLUMN_CLUSTER + " TEXT, " +
            RandomTable.COLUMN_HHNO + " TEXT, " +
            RandomTable.COLUMN_DIST_ID + " TEXT, " +
            RandomTable.COLUMN_SUB_DIST_NAME + " TEXT, " +
            RandomTable.COLUMN_DIST_NAME + " TEXT " +
            ");";

/*    public static final String SQL_ALTER_FORMS = "ALTER TABLE " +
            FormsTable.TABLE_NAME + " ADD COLUMN " +
            FormsTable.COLUMN_SYSDATE + " TEXT";
    public static final String SQL_ALTER_CHILD_TABLE = "ALTER TABLE " +
            ChildTable.TABLE_NAME + " ADD COLUMN " +
            ChildTable.COLUMN_SYSDATE + " TEXT";*/
}
