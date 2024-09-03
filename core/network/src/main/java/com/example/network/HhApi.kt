package com.example.network

import com.example.network.model.NetworkData
import retrofit2.http.GET

interface HhApi {
    // uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download
    @GET(value = "uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download/мок json.json")
    suspend fun getAllData(): NetworkData
}