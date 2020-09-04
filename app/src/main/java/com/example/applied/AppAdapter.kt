package com.example.applied

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/*
    ONLY WORKS WITH ITEM_APPLICATION LAYOUT AND WITH APPLICATION OBJECTS, NOTHING ELSE
*/
class AppAdapter(private val context : Context,
                 private val dataSource : ArrayList<Application>) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var dataList = dataSource
    var onItemClick: ((Application) -> Unit)? = null

    /*
        Override functions
     */
    override fun onBindViewHolder(holder: AppAdapter.ViewHolder, position: Int) {
        val application = dataList[position]
        holder.bindApplication(application)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppAdapter.ViewHolder {
        val rowView = parent.inflate(R.layout.item_application, false)
        return ViewHolder(rowView)
    }

    override fun getItemCount() : Int {
        return dataList.size
    }

    override fun getItemId(position : Int) : Long {
        return position.toLong()
    }

    fun clear() {
        dataList.clear()
    }

    fun addAll(newList : ArrayList<Application>) {
        dataList = ArrayList(newList)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        //val application: TextView = itemView.
        private var view: View = itemView
        private var app : Application? = null

        fun bindApplication(app : Application) {
            this.app = app
            // get TextViews from view
            val companyTextView = view.findViewById<TextView>(R.id.application_company)
            val positionTextView = view.findViewById<TextView>(R.id.application_position)
            val seniorityTextView = view.findViewById<TextView>(R.id.application_seniority)
            val idTextView = view.findViewById<TextView>(R.id.col_id)
            val statusTextView = view.findViewById<TextView>(R.id.status)

            // get the most recent status date
            val recentIndex = app.getRecentDate()

            // update TextViews to display whats in application
            companyTextView.text = app.getCompany()
            positionTextView.text = app.getPosition()
            seniorityTextView.text = app.getSeniority()
            idTextView.text = app.getID().toString()
            statusTextView.text =  when(recentIndex) {
                0 -> context.getString(R.string.Applied)
                1 -> context.getString(R.string.Interviews)
                2 -> context.getString(R.string.Offered)
                3 -> context.getString(R.string.Rejected)
                else -> context.getString(R.string.Applied)
            }
        } // end of bindApplication

        init {
            //itemView.setOnClickListener(this)
            itemView.setOnClickListener {
                onItemClick?.invoke(dataList[adapterPosition])
            }
        }

        override fun onClick(view: View) {
            val string = app!!.getCompany()
            Log.d("AppAdapter", "OnClick Function: $string")

        }

    }
}