package com.example.studentcity.models.database

data class Stuff(
    val post:Post,
    val photo:String
) : Human() {

    override fun toString(): String {
        return String.format("%s %s %s", surname, firstname, patronymic)
    }
}