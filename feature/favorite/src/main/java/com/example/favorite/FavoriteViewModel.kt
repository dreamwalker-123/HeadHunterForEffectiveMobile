package com.example.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mappers.reverseVacancyMapper
import com.example.data.mappers.vacancyMapper
import com.example.data.repository.database.OfflineFavoriteRepository
import com.example.data.repository.database.OfflineVacancyRepository
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.VacancyFromNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val offlineFavoriteRepository: OfflineFavoriteRepository,
    private val databaseRepository: OfflineVacancyRepository
): ViewModel() {
    private val _allFavoriteUiState: MutableStateFlow<FavoriteUiState> = MutableStateFlow(FavoriteUiState.Loading)
    val allFavoriteUiState = _allFavoriteUiState.asStateFlow()

    init {
        getAllFavorite()
    }

    private fun getAllFavorite() {
        viewModelScope.launch {
            offlineFavoriteRepository.getAllFavorite().collect {
                try {
                    _allFavoriteUiState.value = FavoriteUiState.Success(it)
                    Log.e("FavoriteViewModel","correct")
                } catch(e: Exception) {
                    Log.e("FavoriteViewModel","exception")
                    _allFavoriteUiState.value = FavoriteUiState.Error
                }
            }
        }
    }

    fun insertFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            Log.e("FavoriteViewModel","insertFavorite")
            offlineFavoriteRepository.insertFavorite(vacancy)
        }
    }

    fun deleteFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            Log.e("FavoriteViewModel","deleteFavorite")
            offlineFavoriteRepository.deleteFavorite(vacancy)
        }
    }

    fun insertVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            Log.e("FavoriteViewModel","insertVacancy")
            databaseRepository.insertVacancy(vacancy)
        }
    }
}