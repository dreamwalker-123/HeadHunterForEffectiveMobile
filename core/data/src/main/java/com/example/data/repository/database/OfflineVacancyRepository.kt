package com.example.data.repository.database

import com.example.database.dao.ContentsDao
import com.example.database.entities.Vacancy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// методы выбора, добавления, удаления вакансий в VacancyScreen
class OfflineVacancyRepository @Inject constructor(
    private val contentsDao: ContentsDao,
): VacancyRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        contentsDao.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy() {
        contentsDao.deleteVacancy()
    }
    override suspend fun getVacancy(id: String): Flow<Vacancy> {
        return contentsDao.getVacancy(id)
    }
}