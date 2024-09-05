package com.example.network.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class NetworkData(
    val offers: List<OfferFromNetwork>,
    val vacancies: List<VacancyFromNetwork>
)

@Serializable
data class OfferFromNetwork(
    val button: Button? = null,
    val id: String? = null,
    val link: String? = null,
    val title: String? = null,
)

@Serializable
data class VacancyFromNetwork(
    val address: AddressFromNetwork? = null,
    val appliedNumber: Int? = null,
    val company: String? = null,
    val description: String? = null,
    val experience: ExperienceFromNetwork? = null,
    val id: String? = null,
    val isFavorite: Boolean? = null,
    val lookingNumber: Int? = null,
    val publishedDate: String? = null,
    val questions: List<String>? = null,
    val responsibilities: String? = null,
    val salary: SalaryFromNetwork? = null,
    val schedules: List<String>? = null,
    val title: String? = null,
)

@Serializable
data class Button(
    val text: String? = null,
)

@Serializable
data class AddressFromNetwork(
    val house: String? = null,
    val street: String? = null,
    val town: String? = null,
)

@Serializable
data class ExperienceFromNetwork(
    val previewText: String? = null,
    val text: String? = null,
)

@Serializable
data class SalaryFromNetwork(
    val full: String? = null,
    val short: String? = null,
)