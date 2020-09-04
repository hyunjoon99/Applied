package com.example.applied

import java.text.SimpleDateFormat

class Application(dateAdded: String, dateInterview : String?, dateOffer : String?, dateReject : String?,
                  company : String, position : String, seniority : String, id : Int) {
    private var COMPANY = company
    private var POSITION = position
    private var SENIORITY = seniority
    private var COL_ID = id
    private var COL_DATE_ADDED = dateAdded
    private var COL_DATE_INTERVIEW = dateInterview
    private var COL_DATE_OFFER = dateOffer
    private var COL_DATE_REJECT = dateReject

    fun getCompany() : String{
        return COMPANY
    }

    fun getPosition() : String {
        return POSITION
    }

    fun getSeniority() : String {
        return SENIORITY
    }

    fun getID() : Int {
        return COL_ID
    }

    fun getDateAdded() : String {
        return COL_DATE_ADDED
    }

    fun getDateInterview() : String? {
        return COL_DATE_INTERVIEW
    }

    fun getDateOffer() : String? {
        return COL_DATE_OFFER
    }

    fun getDateReject() : String? {
        return COL_DATE_REJECT
    }

    /*
        Returns the most recent application process stage,
        0: applied, 1: interviews, 2: offered, 3: rejected
     */
    fun getRecentDate() : Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        // start with applied date
        var recentDate = sdf.parse(COL_DATE_ADDED)
        var recentIndex = 0
        // variables for loop
        var date : String?
        var index = 1
        while (index < 4) {
            date = when (index) {
                1 -> COL_DATE_INTERVIEW
                2 -> COL_DATE_OFFER
                3 -> COL_DATE_REJECT
                else -> null
            }
            if (date != null) {
                val formatted = sdf.parse(date)
                if (formatted != null) {
                    if (formatted > recentDate) {
                        recentDate = formatted
                        recentIndex = index
                        // if same date, so to a later progression
                    } else if (formatted == recentDate && index > recentIndex) {
                        recentIndex = index
                    }
                }
            }
            index += 1
        }
        return recentIndex
    }
}