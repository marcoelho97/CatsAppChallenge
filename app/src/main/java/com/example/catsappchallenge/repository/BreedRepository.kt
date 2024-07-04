package com.example.catsappchallenge.repository

import android.content.Context
import com.example.catsappchallenge.data.dao.BreedDao
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.network.RetrofitInstance
import com.example.catsappchallenge.utils.SearchManager.prepareSearchFilter
import com.example.catsappchallenge.utils.splitLifeSpan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreedRepository(private val context: Context, private val breedDao: BreedDao) {
    private val contextForRepository = context.applicationContext
    private var breedList: List<BreedListDTO> = emptyList()

    suspend fun fetchAndInsertAllBreeds() = withContext(Dispatchers.IO) {
        val breedList = getAllBreedsFromDb(searchFilter = null)
        if (breedList.isNotEmpty()) {
            return@withContext
        }

        val allBreeds = RetrofitInstance.api.getAllBreeds()
        val breedsToInsert = allBreeds.map { breed ->

            val temperaments: List<String> = breed.temperament.split(",").map { it.trim() }
            val lifeSpan: Pair<Float, Float> = splitLifeSpan(breed.life_span)

            Breed(
                id = breed.id,
                name = breed.name,
                origin = breed.origin,
                temperament = temperaments,
                description = breed.description,
                image = breed.reference_image_id,
                lowLifeSpan = lifeSpan.first,
                highLifeSpan = lifeSpan.second,
                favourite = false
            )
        }
        breedDao.upsertBreedList(breedsToInsert)
        return@withContext
    }

    suspend fun getAllBreedsFromDb(searchFilter: String?): List<BreedListDTO> =
        withContext(Dispatchers.IO) {
            breedList = breedDao.getAllBreeds(
                searchFilter = prepareSearchFilter(searchFilter),
                filterFavourite = null
            )
            return@withContext breedList
        }

    fun getBreedById(breedId: String): Breed {
        return breedDao.getBreedById(breedId)
    }

    suspend fun updateFavouriteByBreed(
        breedId: String,
        favourite: Boolean
    ) =
        withContext(Dispatchers.IO) {
            // TODO: Test with a non existent ID
            breedDao.updateFavouriteByBreedId(
                breedId = breedId,
                favourite = favourite
            )
            return@withContext
        }
}