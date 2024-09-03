package com.example.data.repository.network

import com.example.network.model.NetworkData
import com.example.network.retrofit.RetrofitClient
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val retrofitClient: RetrofitClient,
): AllDataRepository {

    override suspend fun getAllData(): NetworkData  {
        return retrofitClient.getAllData()
    }
}