package com.sankets.starwars.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RetrofitStarWars

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StarWarsApi
