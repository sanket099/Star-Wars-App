package com.sankets.starwars.network

import com.sankets.starwars.network.models.StarWarsCharactersAPIResponse
import com.sankets.starwars.network.models.StarWarsFilmsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people/")
    suspend fun getCharacters(
        @Query("page") page: Int,
    ) : Response<StarWarsCharactersAPIResponse>

    @GET("films/{filmId}")
    suspend fun getFilms(
      @Path("filmId") filmId: Long
    ) : Response<StarWarsFilmsAPIResponse>
}