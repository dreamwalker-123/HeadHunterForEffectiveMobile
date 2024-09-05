package com.example.data.repository.network

import com.example.network.model.NetworkData
import kotlinx.coroutines.flow.Flow

interface AllDataRepository {
    /**
     * Возвращает все данные из сети
     */
    suspend fun getAllData(): NetworkData
}