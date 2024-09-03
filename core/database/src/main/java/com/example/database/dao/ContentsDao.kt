package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entities.Vacancy
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(users: Vacancy)

    @Query(
        value = """
        SELECT * FROM VacancyForScreen
        WHERE id == (:id)
        ORDER BY id ASC
        """
    )
    fun getVacancy(id: String): Flow<Vacancy>

    @Query("DELETE FROM VacancyForScreen")
    suspend fun deleteVacancy()
}