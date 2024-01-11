package com.sankets.starwars.di

import android.app.Application
import androidx.room.Room
import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.storage.SharedPrefs
import com.sankets.starwars.storage.StarWarsStorageManager
import com.sankets.starwars.storage.database.StarWarsDao
import com.sankets.starwars.storage.database.StarWarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    private const val DATABASE_NAME = "starWars.db"

    @Provides
    @Singleton
    fun provideStarWarsDatabase(
        application: Application,
    ): StarWarsDatabase {
        return Room.databaseBuilder(application, StarWarsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideStarWarsDao(starWarsDatabase: StarWarsDatabase): StarWarsDao =
        starWarsDatabase.getStarWarsDao()


    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPrefs = SharedPrefs(application)

    @Provides
    @Singleton
    fun provideStarWarsStorageManager(
        dao: StarWarsDao,
        dispatcherProvider: CoroutineDispatcherProvider,

        ): StarWarsStorageManager = StarWarsStorageManager(
        dao, dispatcherProvider
    )
}