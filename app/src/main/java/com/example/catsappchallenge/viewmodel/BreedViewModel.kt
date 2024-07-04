package com.example.catsappchallenge.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.repository.BreedRepository
import com.example.catsappchallenge.utils.SearchManager
import com.example.catsappchallenge.utils.SearchManager.prepareSearchFilter
import kotlinx.coroutines.launch

class BreedViewModel(private val breedRepository: BreedRepository) : ViewModel() {
    // Breed List
    val breedList: MutableState<List<BreedListDTO>> = mutableStateOf(emptyList())

    // Loading status
    val isLoading: MutableState<Boolean> = mutableStateOf(true)

    init {
        viewModelScope.launch {
            fetchAndInsertAll()
        }
        SearchManager.searchFilter.observeForever {
            viewModelScope.launch {
                getAllBreeds()
            }
        }
    }

    // Get all breeds
    private suspend fun fetchAndInsertAll() {
        val job = viewModelScope.launch {
            breedRepository.fetchAndInsertAllBreeds()
        }
        job.join()
        getAllBreeds()
        isLoading.value = false
    }

    private suspend fun getAllBreeds() {
        breedList.value = breedRepository.getAllBreedsFromDb(
            searchFilter = prepareSearchFilter(SearchManager.searchFilter.value)
        )
    }

    // TODO: getBreedById

    suspend fun updateFavourite(breed: Breed, favourite: Boolean) {
        breedRepository.updateFavouriteByBreed(
            breed = breed,
            favourite = favourite,
            searchFilter = prepareSearchFilter(SearchManager.searchFilter.value)
        )
        getAllBreeds()
    }

}