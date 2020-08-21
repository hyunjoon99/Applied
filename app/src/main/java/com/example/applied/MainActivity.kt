package com.example.applied

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.applied.db.AppContract
import com.example.applied.db.AppDBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val mAppListView = findViewById<ListView>(R.id.list_applications)
    private var mAdapter : AppAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // initialize AppDBHelper
        val mHelper = AppDBHelper(this)

        // add new application FAB onclick function
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            // inflate layout
            val dialogView = layoutInflater.inflate(R.layout.dialog_application_add, null)
            // grab values from EditText in layout
            val companyEditText = dialogView.findViewById<EditText>(R.id.editTextCompany)
            val positionEditText = dialogView.findViewById<EditText>(R.id.editTextPosition)
            val seniorityEditText = dialogView.findViewById<EditText>(R.id.editTextSeniority)

            // create alert dialog
            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle("Add New Application")
                .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                    val company = companyEditText.text.toString()
                    val position = positionEditText.text.toString()
                    val seniority = seniorityEditText.text.toString()

                    // store inputs in ContentValues object
                    val db: SQLiteDatabase = mHelper.writableDatabase
                    val values = ContentValues().apply {
                        put(AppContract.AppEntry.COL_APPLICATION_COMPANY, company)
                        put(AppContract.AppEntry.COL_APPLICATION_POSITION, position)
                        put(AppContract.AppEntry.COL_APPLICATION_SENIORITY, seniority)
                    }

                    // insert ContentValues into db
                    db.insertWithOnConflict(
                        AppContract.AppEntry.TABLE,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                    db.close()

                    // show message that application was added
                    Snackbar.make(view, "Application Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

                }) // end of setPositiveButton
                .setNegativeButton("cancel", null)
                .create()

            dialog.setView(dialogView)
            dialog.show()
        } // end of FAB functionality

    } // end of onCreate

    private fun updateUI(mHelper: AppDBHelper) {
        // create appList ArrayList
        val appList : ArrayList<Application> = ArrayList()

        // display applications stored in db
        val db : SQLiteDatabase = mHelper.readableDatabase
        val projection = arrayOf(
            AppContract.AppEntry.COL_ID,
            AppContract.AppEntry.COL_APPLICATION_COMPANY,
            AppContract.AppEntry.COL_APPLICATION_POSITION,
            AppContract.AppEntry.COL_APPLICATION_SENIORITY
        )

        val cursor : Cursor = db.query(
            AppContract.AppEntry.TABLE, // Table to query
            projection,
            null,
            null,
            null,
            null,
            null
        )

        while(cursor.moveToNext()) {
            val company = cursor.getColumnIndex(AppContract.AppEntry.COL_APPLICATION_COMPANY)
            val position = cursor.getColumnIndex(AppContract.AppEntry.COL_APPLICATION_POSITION)
            val seniority = cursor.getColumnIndex(AppContract.AppEntry.COL_APPLICATION_SENIORITY)
            //Log.i(TAG, "Application Company: " + cursor.getString(idx))
            // db values -> Application object
            val app = Application(cursor.getString(company), cursor.getString(position), cursor.getString(seniority))
            appList.add(app)
        }

        // Application object -> Application Adapter
        if (mAdapter == null) {
            mAdapter = AppAdapter(this,
                appList
            )
            mAppListView.adapter = mAdapter
        } else {
            mAdapter!!.clear()
            mAdapter!!.addAll(appList)
            mAdapter!!.notifyDataSetChanged()
        }
        cursor.close()
        db.close()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    } // end of onCreateOptionsMenu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    } // end of onOptionsItemSelected
} // end of class