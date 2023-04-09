package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "work_table")
data class WorkModel(

    @ColumnInfo(name = "work_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "work_title")
    val title: String,

    @ColumnInfo(name = "work_time")
    val time: Date?,

    @ColumnInfo(name = "work_body")
    val body: String,

    @ColumnInfo(name = "work_color")
    val color: String,

)
