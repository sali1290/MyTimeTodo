package com.example.domain.model

import java.io.Serializable
import java.util.*

data class Work(

    val id: Int,

    val title: String,

    val time: Date?,

    val body: String,

    val color: String,

) : Serializable
