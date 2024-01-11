package com.sankets.starwars.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sankets.starwars.storage.database.models.StarWarsCharacters
import com.sankets.starwars.storage.database.models.StarWarsFilms

@Database(
    version = 1,
    entities = [StarWarsCharacters::class, StarWarsFilms::class],
    exportSchema = false
)
@TypeConverters(
    StarWarsTypeConverters::class
)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract fun getStarWarsDao(): StarWarsDao
}