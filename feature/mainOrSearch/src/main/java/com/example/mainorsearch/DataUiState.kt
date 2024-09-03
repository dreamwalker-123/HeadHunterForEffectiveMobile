package com.example.mainorsearch

import com.example.network.model.NetworkData

interface DataUiState {
    data class Success(val data: NetworkData) : DataUiState
    data object Empty : DataUiState
    data object Error : DataUiState
    data object Loading : DataUiState
}