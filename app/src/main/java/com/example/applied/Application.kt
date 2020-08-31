package com.example.applied

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
}