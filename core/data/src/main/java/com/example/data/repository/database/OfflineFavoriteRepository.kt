package com.example.data.repository.database

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

class OfflineFavoriteRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
): FavoriteRepository {
    override fun getAllFavorite(): Flow<List<Vacancy>> {
        return favoriteDao.getAllFavorite().filter { it.any { qwe -> qwe.isFavorite == true } }
    }

    override suspend fun insertFavorite(vacancy: Vacancy) {
        favoriteDao.insertFavorite(vacancy)
    }

    override suspend fun deleteFavorite(vacancy: Vacancy) {
        favoriteDao.deleteFavorite(vacancy)
    }
}