package com.example.data.repoImpl

import com.example.domain.model.Work
import com.example.domain.repo.WorkRepo

class WorkRepoImpl: WorkRepo {
    override suspend fun getAllWorks(): List<Work> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteWork(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addWork() {
        TODO("Not yet implemented")
    }
}