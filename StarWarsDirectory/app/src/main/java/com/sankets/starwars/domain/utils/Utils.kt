package com.sankets.starwars.domain.utils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun extractNumbers(urls: List<String>): List<Long> {
    val numberList = mutableListOf<Long>()

    try{
        for (url in urls) {
            numberList.add(extractNumber(url))
        }
        return numberList
    }
    catch (ex : Exception){
        ex.printStackTrace()
    }
    return emptyList()
}

fun extractNumber(string : String) : Long {
    val regex = """\d+""".toRegex()
    val matchResult = regex.find(string)
    val number = matchResult?.value?.toLongOrNull()?:-1
    return number
}