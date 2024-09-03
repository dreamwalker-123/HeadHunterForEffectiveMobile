package com.example.favorite

import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.VacancyFromNetwork


interface FavoriteUiState {
    data class Success(val allFavorite: List<Vacancy>) : FavoriteUiState
    data object Empty : FavoriteUiState
    data object Error : FavoriteUiState
    data object Loading : FavoriteUiState
}