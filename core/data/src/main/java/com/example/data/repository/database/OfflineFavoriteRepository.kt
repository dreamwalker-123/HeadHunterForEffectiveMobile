package com.example.data.repository.database

import android.util.Log
import com.example.data.mappers.vacancyMapper
import com.example.database.dao.ContentsDao
import com.example.database.dao.FavoriteDao
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.VacancyFromNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.util.Locale.filter
import javax.inject.Inject

// методы выбора, добавления, удаления избранных вакансий в FavoriteScreen
class OfflineFavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
): FavoriteRepository {
    override fun getAllFavorite(): Flow<List<Vacancy>> {
        Log.e("OfflineFavoriteRepository", "getAllFavorite")
        return favoriteDao.getAllFavorite()
    }

    override suspend fun insertFavorite(vacancy: Vacancy) {
        Log.e("OfflineFavoriteRepository", "insertFavorite")
        favoriteDao.insertFavorite(vacancy.copy(isFavorite = true))
    }

    override suspend fun deleteFavorite(vacancy: Vacancy) {
        Log.e("OfflineFavoriteRepository", "deleteFavorite")
        favoriteDao.deleteFavorite(vacancy.id)
    }
}