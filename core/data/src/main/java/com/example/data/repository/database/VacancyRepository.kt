package com.example.data.repository.database

import com.example.database.entities.Vacancy
import com.example.network.model.NetworkData
import kotlinx.coroutines.flow.Flow


interface VacancyRepository {
    /**
     * Вставляет текущую вакансию для экрана Vacancy
     */
    suspend fun insertVacancy(vacancy: Vacancy)

    /**
     * Удаляет текущую вакансию экрана Vacancy
     */
    suspend fun deleteVacancy()

    /**
     * Получить сохраненную вакансию для экрана Vacancy
     */
    suspend fun getVacancy(id: String): Flow<Vacancy>
}