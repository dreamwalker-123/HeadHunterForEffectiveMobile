package com.example.vacancy

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}
// bat@gmail.com

const val VACANCY = "vacancy"