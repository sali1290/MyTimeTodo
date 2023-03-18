package com.example.data.datasource.dao

import androidx.room.*
import com.example.data.model.WorkModel

@Dao
interface WorkDao {

    @Insert
    fun addWork(workModel: WorkModel)

    @Query("SELECT * FROM work_table")
    fun getAllWorks(): List<WorkModel>

    @Update
    fun updateWork(workModel: WorkModel)

    @Delete
    fun deleteWork(workId: Int)

}