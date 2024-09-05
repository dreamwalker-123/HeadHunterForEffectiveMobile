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
    @Insert(entity = Favorite::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(vacancy: Vacancy)

//    @Query(
//        value = """
//            INSERT INTO FavoriteTable
//            VALUES (:vacancy)
//        """
//    )
//    suspend fun insertFavorite(vacancy: Vacancy)

    @Query(
        value = """
        SELECT * FROM FavoriteTable
        ORDER BY id ASC
        """
    )
    fun getAllFavorite(): Flow<List<Vacancy>>

//    @Delete
//    suspend fun deleteFavorite(vacancy: Vacancy)

    @Query(value = """
        DELETE FROM FavoriteTable
        WHERE id = :id;
    """)
    suspend fun deleteFavorite(id: String)
}