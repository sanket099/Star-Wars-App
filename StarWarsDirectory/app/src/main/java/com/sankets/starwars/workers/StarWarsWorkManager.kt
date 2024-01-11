package com.sankets.starwars.workers

import android.app.Application
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sankets.starwars.domain.utils.Constants
import timber.log.Timber
import javax.inject.Inject

/**
 * Work manager to start one time workers
 */
class StarWarsWorkManager @Inject constructor(private val application: Application) {

    fun startWorkerToGetCharacters(page: Int) {
        val data = Data.Builder().putInt(Constants.PAGE, page)
        val oneTimeRequest = OneTimeWorkRequestBuilder<CharactersWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setInputData(data.build())
            .addTag(Constants.CHARACTER_WORKER)
            .build()
        WorkManager.getInstance(application).enqueueUniqueWork(
            Constants.CHARACTER_WORKER,
            ExistingWorkPolicy.APPEND, //Using Replace to trigger the worker whenever it is called
            oneTimeRequest
        )
    }

    fun startWorkerToGetFilms(filmId: Long) {
        val data = Data.Builder().putLong(Constants.FILM_ID, filmId)
        val oneTimeRequest = OneTimeWorkRequestBuilder<FilmsWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setInputData(data.build())
            .addTag(Constants.FILM_WORKER)
            .build()
        WorkManager.getInstance(application).enqueueUniqueWork(
            Constants.FILM_WORKER,
            ExistingWorkPolicy.APPEND, //Using Replace to trigger the worker whenever it is called
            oneTimeRequest
        )
    }
}