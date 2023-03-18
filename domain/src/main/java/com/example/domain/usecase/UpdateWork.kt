package com.example.domain.usecase

import com.example.domain.model.Work
import com.example.domain.repo.WorkRepo
import javax.inject.Inject

class UpdateWork @Inject constructor(private val workRepo: WorkRepo) {
    suspend fun invoke(work: Work) = workRepo.updateWork(work)
}