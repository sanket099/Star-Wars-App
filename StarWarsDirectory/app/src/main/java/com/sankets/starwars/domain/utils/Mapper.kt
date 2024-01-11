package com.sankets.starwars.domain.utils

import com.sankets.starwars.domain.models.StarWarsCharactersUI
import com.sankets.starwars.domain.models.StarWarsFilmsUI
import com.sankets.starwars.network.models.StarWarsCharactersAPIResponseData
import com.sankets.starwars.network.models.StarWarsFilmsAPIResponse
import com.sankets.starwars.storage.database.models.StarWarsCharacters
import com.sankets.starwars.storage.database.models.StarWarsFilms

fun List<StarWarsFilmsAPIResponse>.toListOfFilmEntities(): List<StarWarsFilms> {
    return this.map { response ->
        response.toFilmEntity()
    }
}

fun List<StarWarsCharactersAPIResponseData>.toListOfCharacterEntities(): List<StarWarsCharacters> {
    return this.map { response ->
        response.toCharacterEntity()
    }
}

fun StarWarsFilmsAPIResponse.toFilmEntity(): StarWarsFilms {
    return StarWarsFilms(
        id = extractNumber(this.url),
        url = this.url,
        title = this.title,
        episodeId = this.episodeId,
        openingCrawl = this.openingCrawl,
        director = this.director,
        producer = this.producer,
        releaseDate = this.releaseDate,
        characters = this.characters,
        planets = this.planets,
        starships = this.starships,
        vehicles = this.vehicles,
        species = this.species,
        created = this.created,
        edited = this.edited
    )
}

fun StarWarsCharactersAPIResponseData.toCharacterEntity(): StarWarsCharacters {
    return StarWarsCharacters(
        id = extractNumber(url),
        url = url,
        name = name,
        height = height,
        hairColor = hairColor,
        skinColor = skinColor,
        eyeColor = eyeColor,
        birthYear = birthYear,
        gender = gender,
        homeworld = homeworld,
        films = films,
        species = species,
        vehicles = vehicles,
        starships = starships,
        created = created,
        edited = edited
    )
}

fun StarWarsFilms.toStarWarsFilmsUI() : StarWarsFilmsUI {
    return StarWarsFilmsUI(
        id = this.id,
        title = this.title,
        episodeId = this.episodeId,
        openingCrawl = this.openingCrawl,
        director = this.director,
        producer = this.producer,
        releaseDate = this.releaseDate,
    )
}

fun StarWarsCharacters.toStarWarsCharactersUI() : StarWarsCharactersUI {
    return StarWarsCharactersUI(
        id = this.id,
        name = this.name,
        height = this.height,
        hairColor = this.hairColor,
        skinColor = this.skinColor,
        eyeColor = this.eyeColor,
        birthYear = this.birthYear,
        gender = this.gender,
        films = this.films,
        createdAt = this.created,
        updatedAt = this.edited
    )
}

fun List<StarWarsCharacters>.toListOfStarWarsCharactersUI(): List<StarWarsCharactersUI> {
    return this.map { response ->
        response.toStarWarsCharactersUI()
    }
}


fun List<StarWarsFilms>.toListOfStarWarsFilmsUI(): List<StarWarsFilmsUI> {
    return this.map { response ->
        response.toStarWarsFilmsUI()
    }
}
