package com.example.applied

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.applied.db.AppDBHelper

class GraphActivity : AppCompatActivity() {
    private val TAG = "GraphActivity"
    // helper for database
    private lateinit var mHelper : AppDBHelper

    override fun OnCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graphs)
        setSupportActionBar(findViewById(R.id.toolbar))


    }
}