package com.salaheddin.weatherapp.home.utils

object ValidationUtils {
    fun validateSearchKeywords(keywords: String): String {
        val expression = "[a-zA-Z0-9, ]*"
        return when {
            keywords.matches(Regex(expression)) && keywords.split(",").size in 3..7 -> ""
            keywords.split(",").size > 7 -> "Please enter maximum 7 cities"
            keywords.split(",").size < 3 -> "Please enter minimum 3 cities"
            else -> "Please enter city names without special characters"
        }
    }
}