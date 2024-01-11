package com.sankets.starwars.di.coroutine

import com.sankets.starwars.di.coroutine.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatcherProviderImpl : CoroutineDispatcherProvider {
    override val mainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
    override val ioDispatcher: CoroutineDispatcher
        get() = Dispatchers.IO
    override val defaultDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfinedDispatcher: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}