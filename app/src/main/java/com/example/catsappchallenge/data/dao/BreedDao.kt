package com.example.catsappchallenge.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.catsappchallenge.data.model.Breed
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Upsert
    suspend fun upsertBreed(breed: Breed)

    @Upsert
    suspend fun upsertBreedList(breeds: List<Breed>)

    @Query("SELECT id, name, image FROM breed WHERE (:filterFavourite IS NULL OR favourite = :filterFavourite)")
    fun getAllBreeds(filterFavourite: Boolean?): Flow<List<Breed>>

    @Query("SELECT * FROM breed WHERE id = :breedId")
    fun getBreedById(breedId: String): Breed
}