package com.example.mytimetodo.utility

import java.util.Calendar

fun getCurrentTime(): Pair<Int, Int> {
    val calender = Calendar.getInstance()
    val hour = calender.get(Calendar.HOUR_OF_DAY)
    val minute = calender.get(Calendar.MINUTE)

    return Pair(hour, minute)
}
