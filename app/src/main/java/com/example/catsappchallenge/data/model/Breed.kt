package com.example.catsappchallenge.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Breed(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val name: String,
    val origin: String,
    val temperament: List<String>,
    val description: String,
    val image: String?,

    val lowLifeSpan: Float,
    val highLifeSpan: Float,

    val favourite: Boolean,
)
