package com.sankets.starwars.di

import android.app.Application
import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.di.coroutine.CoroutineDispatcherProviderImpl
import com.sankets.starwars.repository.StarWarsRepository
import com.sankets.starwars.repository.StarWarsRepositoryImpl
import com.sankets.starwars.storage.StarWarsStorageManager
import com.sankets.starwars.viewmodel.StarWarsViewModel
import com.sankets.starwars.workers.StarWarsWorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcherProvider =
        CoroutineDispatcherProviderImpl()

    @Singleton
    @Provides
    fun provideStarWarsWorkManager(application: Application): StarWarsWorkManager =
        StarWarsWorkManager(application)

    @Provides
    @Singleton
    fun providesRepository(storageManager: StarWarsStorageManager, workManager : StarWarsWorkManager, dispatcherProvider: CoroutineDispatcherProvider): StarWarsRepository =
        StarWarsRepositoryImpl(storageManager, workManager, dispatcher = dispatcherProvider)


}