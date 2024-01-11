package com.sankets.starwars.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.network.StarWarsAPIManager
import com.sankets.starwars.storage.StarWarsStorageManager
import com.sankets.starwars.domain.utils.ApiResult
import com.sankets.starwars.domain.utils.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class CharactersWorker@AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val apiManager: StarWarsAPIManager,
    private val storageManager: StarWarsStorageManager,
    private val coroutineDispatcher: CoroutineDispatcherProvider
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(coroutineDispatcher.ioDispatcher) {
        val page = inputData.getInt(Constants.PAGE, 1)
        val workResult = when(val result = apiManager.getCharactersFromAPI(page)) {
            is ApiResult.Success -> {
                // Save data
                Timber.tag("CHARACTER").d("worker page : ${page} list ${result.data.size}")
                storageManager.saveStarWarsCharactersToDB(result.data)
                Result.success()
            }
            is ApiResult.Error -> {
                Result.retry()
            }
        }
        return@withContext workResult

    }
}