package com.example.applied

class Application(company : String, position : String, seniority : String, id : Int) {
    private var COMPANY = company
    private var POSITION = position
    private var SENIORITY = seniority
    private var COL_ID = id

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