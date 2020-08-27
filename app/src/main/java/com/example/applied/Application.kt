package com.example.applied

class Application(dateAdded: String, company : String, position : String, seniority : String, id : Int) {
    private var COMPANY = company
    private var POSITION = position
    private var SENIORITY = seniority
    private var COL_ID = id
    private var COL_DATE_ADDED = dateAdded

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

    fun modifyCompany(newName : String) {
        COMPANY = newName
    }

    fun modifyPosition(newPosition : String) {
        POSITION = newPosition
    }

    fun modifySeniority(newSeniority : String) {
        SENIORITY = newSeniority
    }
}