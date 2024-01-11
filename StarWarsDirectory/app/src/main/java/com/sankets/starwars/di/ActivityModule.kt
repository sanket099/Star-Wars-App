package com.sankets.starwars.di

import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import com.sankets.starwars.di.coroutine.CoroutineDispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {


}

