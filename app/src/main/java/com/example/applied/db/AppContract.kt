package com.example.applied.db
import android.provider.BaseColumns

// defines constants to access data in database
class AppContract {

    // allows vals to be class level properties
    // behaves like static variables in Java
    companion object {
        const val DB_NAME : String= "com.example.applied.db"
        const val DB_VERSION : Int = 1
    }

    class AppEntry : BaseColumns {

        // allows vals to be class level properties
        // behaves like static variables in Java
        companion object {
            const val TABLE = "applications"
            const val COL_ID = "id"
            const val COL_APPLICATION_COMPANY = "company"
            const val COL_APPLICATION_POSITION = "position"
            const val COL_APPLICATION_SENIORITY = "seniority"
            const val COL_DATE_ADDED = "date_added"
        }

    }

}