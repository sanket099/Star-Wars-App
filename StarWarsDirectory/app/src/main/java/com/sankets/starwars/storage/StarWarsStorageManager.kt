package com.sankets.starwars.storage

import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.domain.utils.toFilmEntity
import com.sankets.starwars.domain.utils.toListOfCharacterEntities
import com.sankets.starwars.network.models.StarWarsCharactersAPIResponseData
import com.sankets.starwars.network.models.StarWarsFilmsAPIResponse
import com.sankets.starwars.storage.database.StarWarsDao
import com.sankets.starwars.storage.database.models.StarWarsFilms
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Class to get and insert data to DB
 */
class StarWarsStorageManager @Inject constructor(
    private val dao: StarWarsDao,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {

    suspend fun saveStarWarsCharactersToDB(characters: List<StarWarsCharactersAPIResponseData>) =
        withContext(dispatcherProvider.ioDispatcher) {
            val listOfCharacters = characters.toListOfCharacterEntities()
            listOfCharacters.forEach { character ->
                dao.insertCharacter(character)
            }
        }

    suspend fun getStarWarsCharactersFromDB() = withContext(dispatcherProvider.ioDispatcher) {
        return@withContext dao.getCharacters()
    }

    suspend fun saveStarWarsFilmsToDB(film: StarWarsFilmsAPIResponse) =
        withContext(dispatcherProvider.ioDispatcher) {
            dao.insertFilm(film.toFilmEntity())
        }

    suspend fun getStarWarsFilmFromDB(listOfFilmIds: List<Long>) =
        withContext(dispatcherProvider.ioDispatcher) {
            return@withContext dao.getFilms(listOfFilmIds)
        }

}