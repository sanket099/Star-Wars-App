package com.sankets.starwars.domain.utils

object Constants {
    const val CHARACTER_WORKER: String = "CHARACTER_WORKER"
    const val FILM_WORKER: String = "FILM_WORKER"
    const val CHARACTER_TABLE = "CHARACTER_TABLE"
    const val BASE_URL = "https://swapi.dev/api/"
    const val FILM_TABLE = "FILM_TABLE"
    const val PAGE = "Page"
    const val FILM_ID = "FILM_ID"

    enum class SortBy(){
        NAME, CREATED_AT, UPDATED_AT
    }

    enum class FilterType(){
        HAIR_COLOR, SKIN_COLOR, EYE_COLOR
    }
}