package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteTable")
data class Favorite(
    @PrimaryKey
    val id: String,
    val isFavorite: Boolean = false,
)