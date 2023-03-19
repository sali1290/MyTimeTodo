package com.example.data.repoImpl

import com.example.data.datasource.dao.WorkDao
import com.example.data.datasource.db.WorkDb
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

    override suspend fun deleteWork(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addWork(work: Work) {
        TODO("Not yet implemented")
    }

    override suspend fun updateWork(work: Work) {
        TODO("Not yet implemented")
    }

}