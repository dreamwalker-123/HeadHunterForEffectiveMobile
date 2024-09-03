package com.example.data.repository

import android.util.Log
import com.example.data.mappers.vacancyMapper
import com.example.data.repository.database.OfflineFavoriteRepository
import com.example.data.repository.network.NetworkRepository
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.NetworkData
import javax.inject.Inject

// network репозиторий, который добавляет в бд избранные вакансии
class CommonRepository @Inject constructor(
    private val offlineFavoriteRepository: OfflineFavoriteRepository,
    private val networkRepository: NetworkRepository,
) {
    suspend fun getAllData(): Result<NetworkData> {
        networkRepository.getAllData().let {
            for (i in it.vacancies) {
                if (i.isFavorite == true) {
                    Log.e("CommonRepository", "getAllData, offlineFavoriteRepository.insertFavorite")
                    offlineFavoriteRepository.insertFavorite(vacancyMapper(i))
                }
            }
        }
        return kotlin.runCatching { networkRepository.getAllData() }
    }
}