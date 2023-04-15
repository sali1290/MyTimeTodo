package com.example.domain.model

import java.io.Serializable
import java.util.Date

data class Work(

    val id: Int,

    val title: String,

    val time: Date?,

    val body: String,

    val color: String,

    val isAlarmSet: Boolean

) : Serializable
