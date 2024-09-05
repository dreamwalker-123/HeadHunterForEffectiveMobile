package com.example.vacancy

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.database.OfflineFavoriteRepository
import com.example.data.repository.database.OfflineVacancyRepository
import com.example.database.entities.Vacancy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class VacancyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val databaseRepository: OfflineVacancyRepository,
    private val favoriteRepository: OfflineFavoriteRepository
): ViewModel() {
    // получение записанного аргумента из Bundle при помощи savedStateHandle
    private val idVacancy: String = checkNotNull(savedStateHandle[VACANCY])

    private val _currentVacancy: MutableStateFlow<Vacancy> =
        MutableStateFlow(Vacancy(id = ""))
    val currentVacancy = _currentVacancy.asStateFlow()

    init {
        getVacancy()
    }

    private fun getVacancy() {
        viewModelScope.launch {
            databaseRepository.getVacancy(idVacancy).collect { vacancy ->
                _currentVacancy.value = vacancy
            }
        }
    }

    fun deleteVacancy() {
        viewModelScope.launch {
            databaseRepository.deleteVacancy()
        }
    }

    fun insertFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            Log.e("SearchViewModel", "метод insertFavorite")
            favoriteRepository.insertFavorite(vacancy)
        }
    }

    fun deleteFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            Log.e("SearchViewModel", "метод deleteFavorite")
            favoriteRepository.deleteFavorite(vacancy)
        }
    }
}
// bat@gmail.com

const val VACANCY = "vacancy"