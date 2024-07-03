package com.example.catsappchallenge.network

import com.example.catsappchallenge.data.model.BreedFetchDTO
import retrofit2.http.GET

interface CatApiService {
    @GET("v1/breeds")
    suspend fun getAllBreeds(): List<BreedFetchDTO>
}