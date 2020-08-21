package com.example.applied

class Application(company : String, position : String, seniority : String) {
    private var COMPANY = company
    private var POSITION = position
    private var SENIORITY = seniority

    fun getCompany() : String{
        return COMPANY
    }

    fun getPosition() : String {
        return POSITION
    }

    fun getSeniority() : String {
        return SENIORITY
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