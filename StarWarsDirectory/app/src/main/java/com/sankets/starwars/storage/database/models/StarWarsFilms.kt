package com.sankets.starwars.storage.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sankets.starwars.domain.utils.Constants

@Entity(tableName = Constants.FILM_TABLE)

data class StarWarsFilms(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "episode_id")
    val episodeId: Int,
    @ColumnInfo(name = "opening_crawl")
    val openingCrawl: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "producer")
    val producer: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "characters")
    val characters: List<String>,
    @ColumnInfo(name = "planets")
    val planets: List<String>,
    @ColumnInfo(name = "starships")
    val starships: List<String>,
    @ColumnInfo(name = "vehicles")
    val vehicles: List<String>,
    @ColumnInfo(name = "species")
    val species: List<String>,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "edited")
    val edited: String
)