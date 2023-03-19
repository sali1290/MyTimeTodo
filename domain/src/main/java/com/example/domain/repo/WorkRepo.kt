package com.example.domain.repo

import com.example.domain.model.Work

interface WorkRepo {

    suspend fun getAllWorks(): List<Work>

    suspend fun deleteWork(work: Work)

    suspend fun addWork(work: Work)

    suspend fun updateWork(work: Work)

}