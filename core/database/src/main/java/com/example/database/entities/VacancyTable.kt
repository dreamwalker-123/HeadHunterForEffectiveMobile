package com.example.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson

@Entity(tableName = "VacancyForScreen")
data class Vacancy(
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

data class Experience(
    val previewText: String? = null,
    val text: String? = null,
)

data class Salary(
    val full: String? = null,
    val short: String? = null,
)

data class Address(
    val house: String? = null,
    val street: String? = null,
    val town: String? = null,
)


class Converters {
    // Address converter
    @TypeConverter
    fun fromAddressInDB(value: String?): Address? {
        return Gson().fromJson(value, Address::class.java)
    }
    @TypeConverter
    fun addressToDatabase(address: Address?): String? {
        return Gson().toJson(address)
    }

    // Salary converter
    @TypeConverter
    fun fromSalaryInDB(value: String?): Salary? {
        return Gson().fromJson(value, Salary::class.java)
    }
    @TypeConverter
    fun salaryToDatabase(salary: Salary?): String? {
        return Gson().toJson(salary)
    }

    // Experience converter
    @TypeConverter
    fun fromExperienceInDB(value: String?): Experience? {
        return Gson().fromJson(value, Experience::class.java)
    }
    @TypeConverter
    fun experienceToDatabase(experience: Experience?): String? {
        return Gson().toJson(experience)
    }

    // questions and schedules List<String> converter
    @TypeConverter
    fun fromQuestionsInDB(value: String?): List<String>? {
        return value?.trim { it == '[' || it == ']' }?.split(", ")
    }
    @TypeConverter
    fun questionsToDatabase(list: List<String>?): String? {
        return list?.toString()
    }
}