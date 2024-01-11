package com.sankets.starwars.repository

import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.domain.models.StarWarsFilmsUI
import com.sankets.starwars.storage.StarWarsStorageManager
import com.sankets.starwars.storage.database.models.StarWarsFilms
import com.sankets.starwars.workers.StarWarsWorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository to get data and pass to view model
 */
class StarWarsRepositoryImpl @Inject constructor(
    private val starWarsStorageManager: StarWarsStorageManager,
    private val starWarsWorkManager: StarWarsWorkManager,
    private val dispatcher: CoroutineDispatcherProvider
) : StarWarsRepository {

    override suspend fun getStarWarsCharacters() =
        starWarsStorageManager.getStarWarsCharactersFromDB()

    override suspend fun getStarWarsFilms(listOfFilmIds: List<Long>) =
        withContext(dispatcher.ioDispatcher) {
            return@withContext starWarsStorageManager.getStarWarsFilmFromDB(listOfFilmIds)
        }

    override fun hitCharactersAPIWithPage(page: Int) {
        starWarsWorkManager.startWorkerToGetCharacters(page)
    }

    override fun hitFilmsAPIWithPage(filmId: Long) {
        starWarsWorkManager.startWorkerToGetFilms(filmId)
    }
}