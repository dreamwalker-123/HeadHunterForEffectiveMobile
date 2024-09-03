package com.example.data.repository.database

import android.util.Log
import com.example.database.dao.ContentsDao
import com.example.database.entities.Vacancy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineVacancyRepository @Inject constructor(
    private val contentsDao: ContentsDao,
): VacancyRepository {

    override suspend fun insertVacancy(vacancy: Vacancy) {
        contentsDao.insertVacancy(vacancy)
    }

    override suspend fun deleteVacancy() {
        Log.i("deleting?", "метод вызван")
        contentsDao.deleteVacancy()
    }
    override suspend fun getVacancy(id: String): Flow<Vacancy> {
        return contentsDao.getVacancy(id)
    }
}