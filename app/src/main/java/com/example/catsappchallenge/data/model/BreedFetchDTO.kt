package com.example.catsappchallenge.data.model


data class BreedFetchDTO(
    val id: String,
    val name: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val reference_image_id: String?,
    val life_span: String
)
