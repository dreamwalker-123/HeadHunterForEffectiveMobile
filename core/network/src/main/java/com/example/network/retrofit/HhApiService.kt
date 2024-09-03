package com.example.network.retrofit

import com.example.network.HhApi
import com.example.network.model.NetworkData
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitClient @Inject constructor(
    networkJson: Json,
): HhApi {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // bat@gmail.com
    // https://drive.usercontent.google.com/u/0/uc?id=1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r&export=download
    private val baseUrl = "https://drive.usercontent.google.com/u/0/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(client)
        .build()
        .create(HhApi::class.java)

    override suspend fun getAllData(): NetworkData {
        return retrofit.getAllData()
    }
}