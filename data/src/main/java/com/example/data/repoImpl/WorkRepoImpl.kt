package com.example.data.repoImpl

import com.example.data.datasource.dao.WorkDao
import com.example.data.mapper.WorkMapper
import com.example.data.model.WorkModel
import com.example.domain.model.Work
import com.example.domain.repo.WorkRepo
import kotlinx.coroutines.delay
import java.io.IOException
import javax.inject.Inject

class WorkRepoImpl @Inject constructor(
    private val dbDao: WorkDao,
    private val mapper: WorkMapper
) : WorkRepo {
    override suspend fun getAllWorks(): List<Work> {
        val workList: List<WorkModel> = dbDao.getAllWorks()
        return if (workList.isNotEmpty()) {
            workList.map(mapper::toWork)
        } else {
            delay(5000)
            throw IOException("No work is saved!")
        }
    }

    override suspend fun deleteWork(work: Work): Boolean {
        if (dbDao.deleteWork(mapper.toWorkModel(work)) != 0) {
            return true
        } else {
            throw IOException("Something went wrong!")
        }
    }

    override suspend fun addWork(work: Work): Boolean {
        if (dbDao.deleteWork(mapper.toWorkModel(work)) != -1) {
            return true
        } else {
            throw IOException("Something went wrong!")
        }
    }

    override suspend fun updateWork(work: Work): Boolean {
        if (dbDao.deleteWork(mapper.toWorkModel(work)) != 0) {
            return true
        } else {
            throw IOException("Something went wrong!")
        }
    }

}