package com.sankets.starwars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sankets.starwars.domain.models.StarWarsCharactersUI
import com.sankets.starwars.domain.models.StarWarsFilmsUI
import com.sankets.starwars.domain.utils.Constants
import com.sankets.starwars.domain.utils.extractNumbers
import com.sankets.starwars.domain.utils.toListOfStarWarsCharactersUI
import com.sankets.starwars.domain.utils.toListOfStarWarsFilmsUI
import com.sankets.starwars.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class StarWarsViewModel @Inject constructor(private val repository: StarWarsRepository) :
    ViewModel() {

    private var charactersJob: Job? = null
    private var filmsJob: Job? = null

    private val _characterFlow = MutableStateFlow<List<StarWarsCharactersUI>>(emptyList())
    val characterFlow: StateFlow<List<StarWarsCharactersUI>> get() = _characterFlow

    private val _filmFlow = MutableStateFlow<List<StarWarsFilmsUI>>(emptyList())
    val filmFlow: StateFlow<List<StarWarsFilmsUI>> get() = _filmFlow

    private val _characterHairColors = MutableStateFlow<List<String>>(emptyList())
    val characterHairColors: StateFlow<List<String>> get() = _characterHairColors

    private val _characterEyeColors = MutableStateFlow<List<String>>(emptyList())
    val characterEyeColors: StateFlow<List<String>> get() = _characterEyeColors

    private val _characterSkinColors = MutableStateFlow<List<String>>(emptyList())
    val characterSkinColors: StateFlow<List<String>> get() = _characterSkinColors

    private val _selectedCharacter = MutableStateFlow<String>("")
    val selectedCharacter: StateFlow<String> get() = _selectedCharacter

    private val _charactersLoading = MutableStateFlow<Boolean>(true)
    val charactersLoading: StateFlow<Boolean> get() = _charactersLoading

    private val _filmsLoading = MutableStateFlow<Boolean>(true)
    val filmsLoading: StateFlow<Boolean> get() = _filmsLoading

    private var page = AtomicInteger(1)

    init {
        charactersJob = viewModelScope.launch {
            getCharacters()
        }
    }

    private suspend fun getCharacters() {
        _charactersLoading.value = true
        viewModelScope.launch {
            repository.getStarWarsCharacters().collectLatest { listOfCharacters ->
                _characterFlow.value = listOfCharacters.toListOfStarWarsCharactersUI()
                _characterHairColors.value =
                    listOfCharacters.toListOfStarWarsCharactersUI().map { it.hairColor }
                _characterSkinColors.value =
                    listOfCharacters.toListOfStarWarsCharactersUI().map { it.skinColor }
                _characterEyeColors.value =
                    listOfCharacters.toListOfStarWarsCharactersUI().map { it.eyeColor }

                _charactersLoading.value = false
            }
        }

    }

    fun sortCharacters(sortingOrder: String) {
        when (sortingOrder) {
            Constants.SortBy.NAME.name -> {
                _characterFlow.value = _characterFlow.value.sortedBy { it.name }
            }

            Constants.SortBy.CREATED_AT.name -> {
                _characterFlow.value = _characterFlow.value.sortedBy { it.createdAt }
            }

            Constants.SortBy.UPDATED_AT.name -> {
                _characterFlow.value = _characterFlow.value.sortedBy { it.updatedAt }
            }

        }

    }

    private suspend fun getFilms(listOfFilmIds: List<Long>, name: String) {
        _filmsLoading.value = true
        filmsJob = viewModelScope.launch {
            repository.getStarWarsFilms(listOfFilmIds).collectLatest { listOfFilms ->
                _filmFlow.value = listOfFilms.toListOfStarWarsFilmsUI()
                _selectedCharacter.value = name

                if(listOfFilmIds.size == listOfFilms.size){
                    _filmsLoading.value = false
                }
            }
        }

    }

    fun hitCharacterApiWithPage() {
        _charactersLoading.value = true
        repository.hitCharactersAPIWithPage(page.incrementAndGet())

    }

    fun hitFilmApiWithFilmId(listOfFilmUrls: List<String>, name: String) {
        val listOfFilms = extractNumbers(listOfFilmUrls)
        listOfFilms.forEach { filmId ->
            repository.hitFilmsAPIWithPage(filmId)
        }
        _filmsLoading.value = true

        viewModelScope.launch {
            getFilms(listOfFilms, name)
        }

    }

    override fun onCleared() {
        super.onCleared()
        charactersJob?.cancel()
        stopFilmJob()
        page.set(0)
    }

    fun filterHairColor(hairColor: String) {
        _characterFlow.value = _characterFlow.value.filter { it.hairColor == hairColor }
    }

    fun filterEyeColor(eyeColor: String) {
        _characterFlow.value = _characterFlow.value.filter { it.eyeColor == eyeColor }
    }

    fun filterSkinColor(skinColor: String) {
        _characterFlow.value = _characterFlow.value.filter { it.skinColor == skinColor }
    }

    fun stopFilmJob() {
        filmsJob?.cancel()
    }
}