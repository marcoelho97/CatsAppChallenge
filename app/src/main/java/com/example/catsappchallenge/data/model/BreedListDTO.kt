package com.example.catsappchallenge.data.model

data class BreedListDTO(
    val id: String,
    val name: String,
    val image: String?,
    val favourite: Boolean
)

fun breedListDTO(breed: Breed): BreedListDTO {
    return BreedListDTO(
        breed.id,
        breed.name,
        breed.image,
        breed.favourite
    )
}
