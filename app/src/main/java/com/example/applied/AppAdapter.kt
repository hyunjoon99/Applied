package com.example.applied

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/*
    ONLY WORKS WITH ITEM_APPLICATION LAYOUT AND WITH APPLICATION OBJECTS, NOTHING ELSE
*/
class AppAdapter(private val context : Context,
                 private val dataSource : ArrayList<Application>) : BaseAdapter() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var dataList = dataSource

    override fun getCount() : Int {
        return dataList.size
    }

    override fun getItem(position : Int) : Any {
        return dataList[position]
    }

    override fun getItemId(position : Int) : Long {
        return position.toLong()
    }

    override fun getView(position : Int, convertView : View?, parent : ViewGroup) : View {
        val rowView = inflater.inflate(R.layout.item_application, parent, false)
        // get company name
        val companyTextView = rowView.findViewById<TextView>(R.id.application_company)
        // get position
        val positionTextView = rowView.findViewById<TextView>(R.id.application_position)
        // get seniority
        val seniorityTextView = rowView.findViewById<TextView>(R.id.application_seniority)

        // get application object from dataSource
        val application = getItem(position) as Application

        // update view to display whats in application
        companyTextView.text = application.getCompany()
        positionTextView.text = application.getPosition()
        seniorityTextView.text = application.getSeniority()

        return rowView
    }

    fun clear() {
        dataList.clear()
    }

    fun addAll(newList : ArrayList<Application>) {
        dataList = ArrayList(newList)
    }
}