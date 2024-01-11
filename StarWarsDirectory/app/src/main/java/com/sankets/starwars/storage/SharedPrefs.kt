package com.sankets.starwars.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefs @Inject constructor(application: Application) {

    companion object {
        private const val SHARED_PREF_NAME = "SharedPref"

    }
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun putString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun putLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0L)
    }

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clearAllSharedPrefs() {
        editor.clear().apply()
    }
}