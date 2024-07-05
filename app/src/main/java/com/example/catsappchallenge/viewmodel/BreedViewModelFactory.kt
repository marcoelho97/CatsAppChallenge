package com.example.catsappchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catsappchallenge.repository.BreedRepository

class BreedViewModelFactory(private val repository: BreedRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BreedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown model class")
    }
}
