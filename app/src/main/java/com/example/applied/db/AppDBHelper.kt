package com.example.applied.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// helper class to open database
class AppDBHelper : SQLiteOpenHelper {

    constructor(context: Context) : super(context, AppContract.DB_NAME, null, AppContract.DB_VERSION)

    override fun onCreate(db: SQLiteDatabase) {
        val createTable : String = "CREATE TABLE " + AppContract.AppEntry.TABLE + " ( " +
                AppContract.AppEntry.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AppContract.AppEntry.COL_DATE_APPLIED + " TEXT NOT NULL, " +
                AppContract.AppEntry.COL_DATE_INTERVIEW + " TEXT DEFAULT NULL, " +
                AppContract.AppEntry.COL_DATE_OFFER + " TEXT DEFAULT NULL, " +
                AppContract.AppEntry.COL_DATE_REJECT + " TEXT DEFAULT NULL, " +
                AppContract.AppEntry.COL_APPLICATION_COMPANY + " TEXT NOT NULL, " +
                AppContract.AppEntry.COL_APPLICATION_POSITION + " TEXT NOT NULL, " +
                AppContract.AppEntry.COL_APPLICATION_SENIORITY + " TEXT NOT NULL);"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + AppContract.AppEntry.TABLE)
        onCreate(db)
    }
}