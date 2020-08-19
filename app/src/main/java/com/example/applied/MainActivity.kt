package com.example.applied

import android.content.DialogInterface
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.dialog_application_add.*


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        // add new application FAB onclick function
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val dialogView = layoutInflater.inflate(R.layout.dialog_application_add, null)
            val companyEditText = dialogView.findViewById<EditText>(R.id.editTextCompany)
            val positionEditText = dialogView.findViewById<EditText>(R.id.editTextPosition)
            val seniorityEditText = dialogView.findViewById<EditText>(R.id.editTextSeniority)

            val dialog: AlertDialog = AlertDialog.Builder(this)
                .setTitle("Add New Application")
                .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                    val company = companyEditText.text.toString()
                    Log.i(TAG, "Company name to add: $company")
                    val position = positionEditText.text.toString()
                    Log.i(TAG, "Position to add: $position")
                    val seniority = seniorityEditText.text.toString()
                    Log.i(TAG, "Seniority status to add: $seniority")
                })
                .setNegativeButton("cancel", null)
                .create()

            dialog.setView(dialogView)
            dialog.show()
        }
    } // end of onCreate

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