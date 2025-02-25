package com.example.exploretartu.dao

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Task(
    var taskID: String,
    var taskName: String,
    var taskLocation: ArrayList<Double>,
    var indoor: Boolean,
    var price: Double,
    var minPersons: Int,
    var maxPersons: Int
): Parcelable
{
    constructor(): this(
        "",
        "",
        arrayListOf(),
        true,
        -1.0,
        0,
        0
    )
}