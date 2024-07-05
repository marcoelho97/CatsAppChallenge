package com.example.catsappchallenge.data.model

data class BreedListDTO(
    val id: String,
    val name: String,
    val image: String?,
    val favourite: Boolean,
    val highLifeSpan: Float
)

fun breedListDTO(breed: Breed): BreedListDTO {
    return BreedListDTO(
        id = breed.id,
        name = breed.name,
        image = breed.image,
        favourite = breed.favourite,
        highLifeSpan = breed.highLifeSpan
    )
}
