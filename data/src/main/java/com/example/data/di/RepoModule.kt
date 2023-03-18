package com.example.data.di

import com.example.data.repoImpl.WorkRepoImpl
import com.example.domain.repo.WorkRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindWorkRepo(
        workRepoImpl: WorkRepoImpl
    ): WorkRepo

}