package com.example.data.repository.database

import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.VacancyFromNetwork
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    /**
     * Получить все избранные вакансии из БД
     */
    fun getAllFavorite(): Flow<List<Vacancy>>

    suspend fun insertFavorite(vacancy: Vacancy)

    suspend fun deleteFavorite(vacancy: Vacancy)
}