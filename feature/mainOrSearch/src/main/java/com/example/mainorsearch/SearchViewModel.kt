package com.example.mainorsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mappers.vacancyMapper
import com.example.data.repository.CommonRepository
import com.example.data.repository.database.OfflineFavoriteRepository
import com.example.data.repository.database.OfflineVacancyRepository
import com.example.data.repository.network.NetworkRepository
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.network.model.VacancyFromNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkDataSource: CommonRepository,
    private val databaseRepository: OfflineVacancyRepository,
    private val offlineFavoriteRepository: OfflineFavoriteRepository
): ViewModel() {
//    private val _emailAddress = MutableStateFlow("")
//    val emailAddress = _emailAddress.asStateFlow()

    private val _dataUiState: MutableStateFlow<DataUiState> = MutableStateFlow(DataUiState.Loading)
    val dataUiState = _dataUiState.asStateFlow()

    init {
        getAllNetworkData()
    }

    private fun getAllNetworkData() {
        viewModelScope.launch {
            networkDataSource.getAllData()
                .onSuccess {
                    _dataUiState.value = DataUiState.Success(it)
                }
                .onFailure {
                    _dataUiState.value = DataUiState.Error
                }
        }
    }

    fun insertVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            databaseRepository.insertVacancy(vacancy)
        }
    }

    fun insertFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            offlineFavoriteRepository.insertFavorite(vacancy)
        }
    }

    fun deleteFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            offlineFavoriteRepository.deleteFavorite(vacancy)
        }
    }
} //bat@gmail.com