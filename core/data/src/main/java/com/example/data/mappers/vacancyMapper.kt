package com.example.data.mappers

import com.example.database.entities.Address
import com.example.database.entities.Experience
import com.example.database.entities.Salary
import com.example.database.entities.Vacancy
import com.example.network.model.AddressFromNetwork
import com.example.network.model.ExperienceFromNetwork
import com.example.network.model.SalaryFromNetwork
import com.example.network.model.VacancyFromNetwork

fun vacancyMapper(vacancyFromNetwork: VacancyFromNetwork): Vacancy {
    return Vacancy(
        id = vacancyFromNetwork.id!!,
        address = addressMapper(vacancyFromNetwork.address),
        appliedNumber = vacancyFromNetwork.appliedNumber,
        company = vacancyFromNetwork.company,
        description = vacancyFromNetwork.description,
        experience = experienceMapper(vacancyFromNetwork.experience),
        isFavorite = vacancyFromNetwork.isFavorite,
        lookingNumber = vacancyFromNetwork.lookingNumber,
        publishedDate = vacancyFromNetwork.publishedDate,
        questions = vacancyFromNetwork.questions,
        responsibilities = vacancyFromNetwork.responsibilities,
        salary = salaryMapper(vacancyFromNetwork.salary),
        schedules = vacancyFromNetwork.schedules,
        title = vacancyFromNetwork.title,
    )
}

fun reverseVacancyMapper(vacancy: Vacancy): VacancyFromNetwork {
    return VacancyFromNetwork(
        id = vacancy.id,
        address = reverseAddressMapper(vacancy.address),
        appliedNumber = vacancy.appliedNumber,
        company = vacancy.company,
        description = vacancy.description,
        experience = reverseExperienceMapper(vacancy.experience),
        isFavorite = vacancy.isFavorite,
        lookingNumber = vacancy.lookingNumber,
        publishedDate = vacancy.publishedDate,
        questions = vacancy.questions,
        responsibilities = vacancy.responsibilities,
        salary = reverseSalaryMapper(vacancy.salary),
        schedules = vacancy.schedules,
        title = vacancy.title,
    )
}

fun salaryMapper(salaryFromNetwork: SalaryFromNetwork?): Salary {
    return Salary(full = salaryFromNetwork?.full, short = salaryFromNetwork?.short)
}
fun reverseSalaryMapper(salary:Salary?): SalaryFromNetwork {
    return SalaryFromNetwork(full = salary?.full, short = salary?.short)
}

fun experienceMapper(experience: ExperienceFromNetwork?): Experience {
    return Experience(previewText = experience?.previewText, text = experience?.text)
}
fun reverseExperienceMapper(experience:Experience?): ExperienceFromNetwork {
    return ExperienceFromNetwork(previewText = experience?.previewText, text = experience?.text)
}

fun addressMapper(address: AddressFromNetwork?): Address {
    return Address(house = address?.house, street = address?.street, town = address?.town)
}
fun reverseAddressMapper(address: Address?): AddressFromNetwork  {
    return AddressFromNetwork(house = address?.house, street = address?.street, town = address?.town)
}