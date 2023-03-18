package com.example.domain.repo

import com.example.domain.model.Work

interface WorkRepo {

    suspend fun getAllWorks(): List<Work>

    suspend fun deleteWork(id: Int)

    suspend fun addWork(work: Work)


}