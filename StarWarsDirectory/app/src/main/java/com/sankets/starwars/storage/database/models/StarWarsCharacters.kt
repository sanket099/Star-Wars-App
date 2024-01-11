package com.sankets.starwars.storage.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sankets.starwars.domain.utils.Constants

@Entity(tableName = Constants.CHARACTER_TABLE)
data class StarWarsCharacters(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "height")
    val height: String,
    @ColumnInfo(name = "hair_color")
    val hairColor: String,
    @ColumnInfo(name = "skin_color")
    val skinColor: String,
    @ColumnInfo(name = "eye_color")
    val eyeColor: String,
    @ColumnInfo(name = "birth_year")
    val birthYear: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "homeworld")
    val homeworld: String,
    @ColumnInfo(name = "films")
    val films: List<String>,
    @ColumnInfo(name = "species")
    val species: List<String>,
    @ColumnInfo(name = "vehicles")
    val vehicles: List<String>,
    @ColumnInfo(name = "starships")
    val starships: List<String>,
    @ColumnInfo(name = "created")
    val created: String,
    @ColumnInfo(name = "edited")
    val edited: String
)
