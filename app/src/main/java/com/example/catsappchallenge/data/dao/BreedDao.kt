package com.example.catsappchallenge.data.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.BreedListDTO

@Dao
interface BreedDao {
    @Upsert
    suspend fun upsertBreed(breed: Breed)

    @Upsert
    suspend fun upsertBreedList(breeds: List<Breed>)

    @Query("UPDATE breed SET favourite = :favourite WHERE id = :breedId")
    suspend fun updateFavouriteByBreedId(breedId: String, favourite: Boolean)

    @Query("SELECT id, name, image, favourite FROM breed " +
            "WHERE (:filterFavourite IS NULL OR favourite = :filterFavourite) " +
            "AND name LIKE :searchFilter")
    fun getAllBreeds(searchFilter: String = "%", filterFavourite: Boolean?): List<BreedListDTO>

    @Query("SELECT * FROM breed WHERE id = :breedId")
    suspend fun getBreedById(breedId: String): Breed?
}