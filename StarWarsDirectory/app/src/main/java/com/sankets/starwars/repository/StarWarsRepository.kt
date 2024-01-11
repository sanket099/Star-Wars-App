package com.sankets.starwars.repository

import com.sankets.starwars.storage.database.models.StarWarsCharacters
import com.sankets.starwars.storage.database.models.StarWarsFilms
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun getStarWarsCharacters(): Flow<List<StarWarsCharacters>>
    suspend fun getStarWarsFilms(listOfFilmIds : List<Long>): Flow<List<StarWarsFilms>>
    fun hitCharactersAPIWithPage(page : Int)
    fun hitFilmsAPIWithPage(filmId : Long)
}