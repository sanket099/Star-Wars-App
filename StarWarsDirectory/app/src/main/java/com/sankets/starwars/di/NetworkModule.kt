package com.sankets.starwars.di

import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.network.StarWarsAPIManager
import com.sankets.starwars.domain.utils.Constants
import com.sankets.starwars.domain.utils.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(networkInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    @BaseUrl
    fun providesBaseURL(): String = Constants.BASE_URL

    @Provides
    @Singleton
    @RetrofitStarWars
    fun provideRetrofit(@BaseUrl baseUrl: String, okhttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl).client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    @StarWarsApi
    fun provideStarWarsApi(@RetrofitStarWars retrofit: Retrofit): com.sankets.starwars.network.StarWarsApi =
        retrofit.create(com.sankets.starwars.network.StarWarsApi::class.java)

    @Provides
    @Singleton
    fun provideStarWarsApiManager(
        @StarWarsApi api: com.sankets.starwars.network.StarWarsApi,
        coroutineDispatcher: CoroutineDispatcherProvider,
        ): StarWarsAPIManager =
        StarWarsAPIManager(api, coroutineDispatcher)

}