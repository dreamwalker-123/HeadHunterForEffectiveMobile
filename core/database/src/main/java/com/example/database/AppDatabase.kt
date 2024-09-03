package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.dao.ContentsDao
import com.example.database.dao.FavoriteDao
import com.example.database.entities.Converters
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy

@Database(entities = [Vacancy::class, Favorite::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun contentsDao(): ContentsDao
    abstract fun favoriteDao(): FavoriteDao
}