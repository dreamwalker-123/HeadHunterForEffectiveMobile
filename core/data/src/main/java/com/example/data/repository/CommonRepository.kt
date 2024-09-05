package com.example.data.repository

import android.util.Log
import com.example.data.mappers.reverseVacancyMapper
import com.example.data.mappers.vacancyMapper
import com.example.data.repository.database.OfflineFavoriteRepository
import com.example.data.repository.network.NetworkRepository
import com.example.database.entities.Vacancy
import com.example.network.model.NetworkData
import com.example.network.model.VacancyFromNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// network репозиторий, который добавляет в бд избранные вакансии
class CommonRepository @Inject constructor(
    private val favoriteRepository: OfflineFavoriteRepository,
    private val networkRepository: NetworkRepository,
) {
    private var networkData = NetworkData(listOf(), listOf())
    suspend fun insertOnceAfterNetworkCall() {
        networkRepository.getAllData().let {
            for (i in it.vacancies) {
                if (i.isFavorite == true) {
                    favoriteRepository.insertFavorite(vacancyMapper(i))
                }
            }
            networkData = it
        }
        Log.e("CommonRepository", "insertOnceAfterNetworkCall")
    }

    suspend fun getAllData(): Flow<NetworkData> {
        val networkFlow = flow { emit(networkData) }

        return networkFlow.combine(favoriteRepository.getAllFavorite()) {
            networkData: NetworkData, listWithFavorite: List<Vacancy> ->

            val networkVacancies = networkData.vacancies
            val favoriteMap = mutableMapOf<String, VacancyFromNetwork>()
            listWithFavorite.forEach { favoriteMap[it.id] = reverseVacancyMapper(it) }

            val returnList = mutableListOf<VacancyFromNetwork>()

            for (j in networkVacancies) {
                if (favoriteMap.containsKey(j.id)) {
                    returnList.add(j.copy(isFavorite = true))
                } else returnList.add(j.copy(isFavorite = false))
            }

            Log.e("CommonRepository", "combineFlows2")
            NetworkData(offers = networkData.offers, vacancies = returnList.toList())
        }
    }
}