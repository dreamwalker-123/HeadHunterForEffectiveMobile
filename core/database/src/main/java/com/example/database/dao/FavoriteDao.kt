package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    // прочитать как правильно делать INSERT и UPDATE, создать методы
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(vacancy: Vacancy)

    @Query(
        value = """
        SELECT * FROM FavoriteTable
        ORDER BY id ASC
        """
    )
    fun getAllFavorite(): Flow<List<Vacancy>>

    @Delete
    suspend fun deleteFavorite(vacancy: Vacancy)
}