package com.example.catsappchallenge.ui.screens

sealed class Screen(val route: String) {
    data object BreedsListScreen : Screen("breeds-list-screen")
    data object FavouriteBreedsScreen : Screen("favourite-breeds-screen")
    data object DetailedBreedScreen : Screen("detailed-breeds-screen")
}