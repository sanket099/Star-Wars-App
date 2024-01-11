package com.sankets.starwars.storage.database

import androidx.room.*
import com.sankets.starwars.storage.database.models.StarWarsCharacters
import com.sankets.starwars.storage.database.models.StarWarsFilms
import com.sankets.starwars.domain.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface StarWarsDao {

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: StarWarsCharacters): Long

    @Query("SELECT * FROM ${Constants.CHARACTER_TABLE}")
    fun getCharacters(): Flow<List<StarWarsCharacters>>

    // INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFilm(film: StarWarsFilms): Long

    @Query("SELECT * FROM ${Constants.FILM_TABLE} WHERE id IN (:ids)")
    fun getFilms(ids: List<Long>):  Flow<List<StarWarsFilms>>
}