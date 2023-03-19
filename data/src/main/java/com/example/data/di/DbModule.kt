package com.example.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.datasource.dao.WorkDao
import com.example.data.datasource.db.WorkDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WorkDb::class.java, "work_db").build()


    @Provides
    @Singleton
    fun provideWorkDaoO(db: WorkDb): WorkDao {
        return db.workDao()
    }

}