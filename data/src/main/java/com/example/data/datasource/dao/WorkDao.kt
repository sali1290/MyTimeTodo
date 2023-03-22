package com.example.data.datasource.dao

import androidx.room.*
import com.example.data.model.WorkModel

@Dao
interface WorkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWork(workModel: WorkModel): Long

    @Query("SELECT * FROM work_table")
    fun getAllWorks(): List<WorkModel>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWork(workModel: WorkModel): Int

    @Delete
    fun deleteWork(workModel: WorkModel): Int

}