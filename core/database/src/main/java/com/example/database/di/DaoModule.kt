package com.example.database.di

import com.example.database.AppDatabase
import com.example.database.dao.ContentsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesContentsDao(appDatabase: AppDatabase): ContentsDao {
        return appDatabase.contentsDao()
    }
}