package com.example.vacancy

import com.example.network.model.VacancyFromNetwork

interface VacancyUiState {
    data class Success(val vacancy: VacancyFromNetwork) : VacancyUiState
    data object Empty : VacancyUiState
    data object Error : VacancyUiState
    data object Loading : VacancyUiState
}