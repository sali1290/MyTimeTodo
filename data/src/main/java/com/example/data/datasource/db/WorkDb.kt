package com.example.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.datasource.dao.WorkDao
import com.example.data.datasource.utility.DateConverter
import com.example.data.model.WorkModel

@Database(entities = [WorkModel::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class WorkDb : RoomDatabase() {

    abstract fun workDao(): WorkDao

}