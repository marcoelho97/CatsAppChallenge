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

    // In Favourites List
    private val filterFavourite: MutableState<Boolean?> = mutableStateOf(null)

    // Loading status
    val isLoading: MutableState<Boolean> = mutableStateOf(true)

    var averageLifeSpan: Double = 0.0

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
        if (breedList.value.isEmpty()) {
            if (!breedRepository.isConnected()) {
                breedRepository.registerNetworkCallback(this)
            }
        }
        isLoading.value = false
    }

    suspend fun getAllBreeds() {
        breedList.value = breedRepository.getAllBreedsFromDb(
            searchFilter = prepareSearchFilter(SearchManager.searchFilter.value),
            filterFavourites = filterFavourite.value
        )
        averageLifeSpan = if (breedList.value.size > 0) {
            breedList.value.map { breed ->
                breed.highLifeSpan
            }.average()
        } else {
            0.0
        }
    }

    suspend fun updateFilterFavourites(filterFavourite: Boolean?) {
        this.filterFavourite.value = filterFavourite
        getAllBreeds()
    }

    suspend fun getBreedById(breedId: String): Breed? {
        return breedRepository.getBreedById(breedId)
    }

    suspend fun updateFavourite(breedId: String, favourite: Boolean): Boolean {
        val wasUpdated = breedRepository.updateFavouriteByBreed(
            breedId = breedId,
            favourite = favourite
        )
        getAllBreeds()
        return wasUpdated
    }

}
