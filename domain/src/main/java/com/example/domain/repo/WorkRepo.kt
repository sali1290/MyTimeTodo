package com.example.domain.repo

import com.example.domain.model.Work

interface WorkRepo {

    suspend fun getAllWorks(): List<Work>

    suspend fun deleteWork(work: Work): Boolean

    suspend fun addWork(work: Work): Boolean

    suspend fun updateWork(work: Work): Boolean

    suspend fun getWorksByColor(): List<Work>

    suspend fun getWorksByTitle(): List<Work>

    suspend fun getWorksByTime(): List<Work>
}