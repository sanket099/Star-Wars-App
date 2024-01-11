package com.sankets.starwars.domain.models

data class StarWarsCharactersUI(
    val id : Long,
    val name: String,
    val height: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val films : List<String>,
    val createdAt : String,
    val updatedAt : String
)