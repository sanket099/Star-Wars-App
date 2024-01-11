package com.sankets.starwars.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.domain.utils.ApiResult
import com.sankets.starwars.domain.utils.Constants
import com.sankets.starwars.network.StarWarsAPIManager
import com.sankets.starwars.storage.StarWarsStorageManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class FilmsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val apiManager: StarWarsAPIManager,
    private val storageManager: StarWarsStorageManager,
    private val coroutineDispatcher: CoroutineDispatcherProvider
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(coroutineDispatcher.ioDispatcher) {
        val filmId = inputData.getLong(Constants.FILM_ID, -1)

        val workResult = if(filmId.toInt() != -1) {
            when(val result = apiManager.getFilmsFromAPI(filmId)) {
                is ApiResult.Success -> {
                    // Save data
                    storageManager.saveStarWarsFilmsToDB(result.data)
                    Result.success()
                }
                is ApiResult.Error -> {
                    Timber.tag("Error").e("${result.exception}")
                    Result.retry()
                }
            }
        }
        else{
            Result.retry()
        }
        return@withContext workResult

    }
}