package com.example.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.datasource.dao.WorkDao
import com.example.data.model.WorkModel

@Database(entities = [WorkModel::class], version = 1)
abstract class WorkDb : RoomDatabase() {

    abstract fun workDao(): WorkDao

}