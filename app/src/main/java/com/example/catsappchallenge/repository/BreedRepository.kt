package com.example.catsappchallenge.repository

import android.content.Context
import com.example.catsappchallenge.data.dao.BreedDao
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.BreedListDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreedRepository(private val context: Context, private val breedDao: BreedDao) {
    private val contextForRepository = context.applicationContext
    private var breedList: List<BreedListDTO> = emptyList()

    suspend fun fetchAndInsertAllBreeds() = withContext(Dispatchers.IO) {
        val allBreeds = getAllBreeds()
        if(allBreeds.isNotEmpty()) {
            return@withContext
        }
        // TODO: API call here
    }

    suspend fun getAllBreeds(): List<BreedListDTO> = withContext(Dispatchers.IO) {
        if(breedList.isNotEmpty()) {
            return@withContext breedList
        }
        breedList = breedDao.getAllBreeds(null)
        return@withContext breedList
    }

    fun getBreedById(breedId: String): Breed {
        return breedDao.getBreedById(breedId)
    }
}