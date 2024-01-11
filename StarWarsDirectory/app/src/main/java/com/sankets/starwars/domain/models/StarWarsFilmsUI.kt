package com.sankets.starwars.domain.models


data class StarWarsFilmsUI(
    val id : Long,
    val title: String,
    val episodeId: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: String,
)