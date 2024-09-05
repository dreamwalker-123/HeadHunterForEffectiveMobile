package com.example.data.repository.network

import android.util.Log
import com.example.network.model.NetworkData
import com.example.network.retrofit.RetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val retrofitClient: RetrofitClient,
): AllDataRepository {

    override suspend fun getAllData(): NetworkData {
        Log.e("NetworkRepository", "getAllData")
        return retrofitClient.getAllData()
    }
}