package com.example.applied

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applied.db.AppContract
import com.example.applied.db.AppDBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    // helper for database
    private lateinit var mHelper : AppDBHelper
    // helpers for managing RecyclerView
    private lateinit var mAppRecyclerView : RecyclerView
    private lateinit var linearLayoutManager : LinearLayoutManager
    private var mAdapter : AppAdapter? = null
    // date format
    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // set layout manager
        linearLayoutManager = LinearLayoutManager(this)

        // declare RecyclerView and set layout manager
        mAppRecyclerView = findViewById(R.id.list_applications)
        mAppRecyclerView.layoutManager = linearLayoutManager

        // initialize AppDBHelper
        mHelper = AppDBHelper(this)

        // update list display
        updateUI()

        /*
            fab onclick functionality
            stores a new application row in database
         */
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
                    val currentDate = sdf.format(Date())
                    Log.i(TAG, "Current date: $currentDate")

                    // store inputs in ContentValues object
                    val db: SQLiteDatabase = mHelper.writableDatabase
                    val values = ContentValues().apply {
                        put(AppContract.AppEntry.COL_DATE_APPLIED, currentDate)
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

                    // update ui
                    updateUI()

                    // show message that application was added
                    Snackbar.make(view, "Application Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()

                }) // end of setPositiveButton
                .setNegativeButton("cancel", null)
                .create()

            dialog.setView(dialogView)
            dialog.show()
        } // end of FAB functionality

        /*
            Open dialog for editing an application
            updates information in db with a SQLite UPDATE command,
            or deletes with a SQLite DELETE command
         */
        mAdapter!!.onItemClick = { application ->
            Log.d(TAG, application.getCompany())
            // get layout
            val dialogView = layoutInflater.inflate(R.layout.dialog_application_edit, null)
            // grab values from EditText in layout
            val companyEditText = dialogView.findViewById<EditText>(R.id.editTextAppCompany)
            val positionEditText = dialogView.findViewById<EditText>(R.id.editTextAppPosition)
            val seniorityEditText = dialogView.findViewById<EditText>(R.id.editTextAppSeniority)
            val appliedEditText = dialogView.findViewById<EditText>(R.id.editTextAppliedDate)
            val interviewEditText  = dialogView.findViewById<EditText>(R.id.editTextInterviewDate)
            val offerEditText = dialogView.findViewById<EditText>(R.id.editTextOfferDate)
            val rejectEditText = dialogView.findViewById<EditText>(R.id.editTextRejectDate)

            // set the text in layout to information stored in application object
            companyEditText.setText(application?.getCompany())
            positionEditText.setText(application?.getPosition())
            seniorityEditText.setText(application?.getSeniority())
            appliedEditText.setText(application?.getDateAdded())
            interviewEditText.setText(application?.getDateInterview())
            offerEditText.setText(application?.getDateOffer())
            rejectEditText.setText(application?.getDateReject())

            // open dialog
            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle("View Application")
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                    // get database
                    val db : SQLiteDatabase = mHelper.writableDatabase
                    // store strings in ContentValues object
                    val cv = ContentValues()

                    // get strings in editText
                    val company : String = companyEditText.text.toString()
                    val position : String = positionEditText.text.toString()
                    val seniority : String = seniorityEditText.text.toString()
                    // insert strings into ContentValues
                    cv.put(AppContract.AppEntry.COL_APPLICATION_COMPANY, company)
                    cv.put(AppContract.AppEntry.COL_APPLICATION_POSITION, position)
                    cv.put(AppContract.AppEntry.COL_APPLICATION_SENIORITY, seniority)

                    // get dates from editText
                    val dateApplied = try {sdf.parse(appliedEditText.text.toString())} catch (e: Throwable) {null}
                    val dateInterview = try {sdf.parse(interviewEditText.text.toString())} catch (e: Throwable) {null}
                    val dateOffer = try {sdf.parse(offerEditText.text.toString())} catch (e: Throwable) {null}
                    val dateReject = try {sdf.parse(rejectEditText.text.toString())} catch (e: Throwable) {null}

                    // insert dates into ContentValues
                    if (dateApplied == null)
                        cv.put(AppContract.AppEntry.COL_DATE_APPLIED, application?.getDateAdded())
                    else
                        cv.put(AppContract.AppEntry.COL_DATE_APPLIED, sdf.format(dateApplied))
                    if (dateInterview == null)
                        cv.put(AppContract.AppEntry.COL_DATE_INTERVIEW, application?.getDateInterview())
                    else
                        cv.put(AppContract.AppEntry.COL_DATE_INTERVIEW, sdf.format(dateInterview))
                    if (dateOffer == null)
                        cv.put(AppContract.AppEntry.COL_DATE_OFFER, application?.getDateOffer())
                    else
                        cv.put(AppContract.AppEntry.COL_DATE_OFFER, sdf.format(dateOffer))
                    if (dateReject == null)
                        cv.put(AppContract.AppEntry.COL_DATE_REJECT, application?.getDateReject())
                    else
                        cv.put(AppContract.AppEntry.COL_DATE_REJECT, sdf.format(dateReject))

                    // update database with ContentValues
                    db.update(AppContract.AppEntry.TABLE, cv, AppContract.AppEntry.COL_ID + "=" + application?.getID().toString(), null)
                    db.close()
                    updateUI()
                    // show message that application was updated
                    Snackbar.make(mAppRecyclerView, "Application Updated", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                })
                .setNeutralButton("Delete", DialogInterface.OnClickListener {dialog, which ->
                    // get database
                    val db : SQLiteDatabase = mHelper.writableDatabase
                    // deleted based on row id
                    val id = application?.getID().toString()
                    db.delete(AppContract.AppEntry.TABLE,
                        AppContract.AppEntry.COL_ID + " =?",
                        arrayOf(id))
                    db.close()
                    updateUI()
                    // show message that application was deleted
                    Snackbar.make(mAppRecyclerView, "Application Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                })
                .setNegativeButton("Close", null)
                .create()

            dialog.setView(dialogView)
            dialog.show()
        }
    } // end of onCreate

    /*
        Updates the UI and the ArrayList<Application> in mAdapter;
        creates Application objects for each row in SQLite db in order to display them,
     */
    private fun updateUI() {
        // create appList ArrayList
        val appList : ArrayList<Application> = ArrayList()

        // display applications stored in db
        val db : SQLiteDatabase = mHelper.readableDatabase
        val projection = arrayOf(
            AppContract.AppEntry.COL_ID,
            AppContract.AppEntry.COL_DATE_APPLIED,
            AppContract.AppEntry.COL_DATE_INTERVIEW,
            AppContract.AppEntry.COL_DATE_OFFER,
            AppContract.AppEntry.COL_DATE_REJECT,
            AppContract.AppEntry.COL_APPLICATION_COMPANY,
            AppContract.AppEntry.COL_APPLICATION_POSITION,
            AppContract.AppEntry.COL_APPLICATION_SENIORITY
        )
        // create cursor to parse through db
        val cursor : Cursor = db.query(
            AppContract.AppEntry.TABLE, // Table to query
            projection,
            null,
            null,
            null,
            null,
            null
        )
        // parse through db with cursor, adding to list
        while(cursor.moveToNext()) {
            val company = cursor.getColumnIndex(AppContract.AppEntry.COL_APPLICATION_COMPANY)
            val position = cursor.getColumnIndex(AppContract.AppEntry.COL_APPLICATION_POSITION)
            val seniority = cursor.getColumnIndex(AppContract.AppEntry.COL_APPLICATION_SENIORITY)
            val id = cursor.getColumnIndex(AppContract.AppEntry.COL_ID)
            val dateAdded = cursor.getColumnIndex(AppContract.AppEntry.COL_DATE_APPLIED)
            val dateInterview = cursor.getColumnIndex(AppContract.AppEntry.COL_DATE_INTERVIEW)
            val dateOffer = cursor.getColumnIndex(AppContract.AppEntry.COL_DATE_OFFER)
            val dateReject = cursor.getColumnIndex(AppContract.AppEntry.COL_DATE_REJECT)

            // db values -> Application object
            val app = Application(
                cursor.getString(dateAdded),
                cursor.getString(dateInterview),
                cursor.getString(dateOffer),
                cursor.getString(dateReject),
                cursor.getString(company),
                cursor.getString(position),
                cursor.getString(seniority),
                cursor.getInt(id)
            )
            // add Application object to List<Application>
            appList.add(app)
        }

        // Attach adapter to ListView
        if (mAdapter == null) {
            mAdapter = AppAdapter(this, appList)
            mAppRecyclerView.adapter = mAdapter
        } else {
            mAdapter!!.clear()
            mAdapter!!.addAll(appList)
            mAdapter!!.notifyDataSetChanged()
        }
        cursor.close()
        db.close()
    }

    /*
        Inflate the menu; this adds items to the action bar if it is present.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    } // end of onCreateOptionsMenu

    /*
        Handle action bar item clicks here. The action bar will
        automatically handle clicks on the Home/Up button, so long
        as you specify a parent activity in AndroidManifest.xml.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    } // end of onOptionsItemSelected
} // end of class