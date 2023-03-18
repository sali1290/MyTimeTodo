package com.example.domain.usecase

import com.example.domain.repo.WorkRepo
import javax.inject.Inject

class GetAllWorks @Inject constructor(private val workRepo: WorkRepo) {
    suspend fun invoke() = workRepo.getAllWorks()
}