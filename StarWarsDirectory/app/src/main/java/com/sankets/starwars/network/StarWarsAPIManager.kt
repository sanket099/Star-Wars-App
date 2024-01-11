package com.sankets.starwars.network

import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.domain.utils.ApiResult
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * API Manager to call APIs
 */
class StarWarsAPIManager @Inject constructor(
    private val starWarsApi: StarWarsApi,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {

    suspend fun getCharactersFromAPI(page: Int) = withContext(dispatcherProvider.ioDispatcher) {
        val response = starWarsApi.getCharacters(page)
        if(response.isSuccessful && response.body() != null){
            return@withContext ApiResult.Success(response.body()!!.results)
        }
        else{
            return@withContext ApiResult.Error(Exception(response.errorBody().toString()))
        }
    }

    suspend fun getFilmsFromAPI(filmId: Long) = withContext(dispatcherProvider.ioDispatcher) {
        val response = starWarsApi.getFilms(filmId)
        if(response.isSuccessful && response.body() != null){
            return@withContext ApiResult.Success(response.body()!!)
        }
        else{
            return@withContext ApiResult.Error(Exception(response.errorBody().toString()))
        }
    }
}