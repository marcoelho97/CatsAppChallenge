package com.example.catsappchallenge.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.thecatapi.com/"
    private const val BASE_IMAGE_URL = "https://cdn2.thecatapi.com/images/"
    private const val BASE_IMAGE_EXTENSION = ".jpg"
    fun BASE_IMAGE_URL(breedImage: String, alternativeExtension: String?): String {
        return BASE_IMAGE_URL + breedImage + (alternativeExtension ?: BASE_IMAGE_EXTENSION)
    }

    val api: CatApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApiService::class.java)
    }
}
