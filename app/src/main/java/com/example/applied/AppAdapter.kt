package com.example.applied

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.SimpleDateFormat

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

    override fun getItem(position : Int) : Application {
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
        // get column id
        val idTextView = rowView.findViewById<TextView>(R.id.col_id)
        // get date added
        val statusTextView = rowView.findViewById<TextView>(R.id.status)

        // get application object from dataSource
        val application = getItem(position) as Application

        // get the most recent status
        val sdf = SimpleDateFormat("yyyy-mm-dd")

        var index = 1
        var recentDate = sdf.parse(application.getDateAdded())
        var recentIndex = 0
        var date : String?
        while (index < 4) {
            date = when (index) {
                1 -> application.getDateInterview()
                2 -> application.getDateOffer()
                3 -> application.getDateReject()
                else -> null
            }
            if (date != null) {
                val formatted = sdf.parse(date)
                if (formatted != null) {
                    if (formatted > recentDate) {
                        recentDate = formatted
                        recentIndex = index
                    } else if (formatted == recentDate && index > recentIndex) {
                        recentIndex = index
                    }
                }
            }
            index += 1
        }


        // update view to display whats in application
        companyTextView.text = application.getCompany()
        positionTextView.text = application.getPosition()
        seniorityTextView.text = application.getSeniority()
        idTextView.text = application.getID().toString()

        when (recentIndex) {
            0 -> statusTextView.text = context.getString(R.string.Applied)
            1 -> statusTextView.text = context.getString(R.string.Interviews)
            2 -> statusTextView.text = context.getString(R.string.Offered)
            3 -> statusTextView.text = context.getString(R.string.Rejected)
            else -> statusTextView.text = context.getString(R.string.Applied)
        }
        //statusTextView.text = application.getDateAdded()

        return rowView
    }

    fun clear() {
        dataList.clear()
    }

    fun addAll(newList : ArrayList<Application>) {
        dataList = ArrayList(newList)
    }
}