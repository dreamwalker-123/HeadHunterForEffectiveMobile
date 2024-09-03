package com.example.data.repository.network

import com.example.network.model.NetworkData

interface AllDataRepository {
    /**
     * Возвращает все темы из базы данных
     */
    suspend fun getAllData(): NetworkData
}