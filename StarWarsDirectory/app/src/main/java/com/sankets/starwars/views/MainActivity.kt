package com.sankets.starwars.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sankets.starwars.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the character fragment initially
        if (savedInstanceState == null) {
            this.supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterFragment())
                .commit()
        }
    }

    // Function to load the film fragment
    fun loadFilmFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FilmsFragment())
            .addToBackStack(null)
            .commit()
    }

    // Function to load the filter fragment
    fun loadFilterFragment() {
        val sortFilterFragment = SortFilterFragment()
        sortFilterFragment.show(supportFragmentManager, sortFilterFragment.tag)
    }
}