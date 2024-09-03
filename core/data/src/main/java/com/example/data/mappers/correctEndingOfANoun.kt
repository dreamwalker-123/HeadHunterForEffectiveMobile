package com.example.data.mappers

fun correctEndingOfANoun(num: Int): String {
    val lastDigit = num.toString().last().digitToInt()
    return when {
        num == 11 -> "вакансий"
        lastDigit == 0 -> "вакансий"
        num in 5..20 -> "вакансий"
        lastDigit == 1 -> "вакансия"
        lastDigit in 5..9 -> "вакансий"
        else -> "вакансии"
    }
}