package com.example.data.repository

import com.example.data.mappers.vacancyMapper
import com.example.data.repository.database.OfflineFavoriteRepository
import com.example.data.repository.network.NetworkRepository
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.NetworkData
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val offlineFavoriteRepository: OfflineFavoriteRepository,
    private val networkRepository: NetworkRepository,
) {
    suspend fun getAllData(): Result<NetworkData> {
        networkRepository.getAllData().let {
            for (i in it.vacancies) {
                if (i.isFavorite == true) {
                    offlineFavoriteRepository.insertFavorite(vacancyMapper(i))
                }
            }
        }
        return kotlin.runCatching { networkRepository.getAllData() }
    }
}