package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteTable")
data class Favorite(
    @PrimaryKey
    val id: String,
    val address: Address? = null,
    val appliedNumber: Int? = null,
    val company: String? = null,
    val description: String? = null,
    val experience: Experience? = null,

    val isFavorite: Boolean? = null,
    val lookingNumber: Int? = null,
    val publishedDate: String? = null,
    val questions: List<String>? = null,
    val responsibilities: String? = null,
    val salary: Salary? = null,
    val schedules: List<String>? = null,
    val title: String? = null,
)