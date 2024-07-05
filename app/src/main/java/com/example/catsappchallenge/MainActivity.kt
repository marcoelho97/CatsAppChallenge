package com.example.catsappchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catsappchallenge.data.database.CatsAppDatabase
import com.example.catsappchallenge.repository.BreedRepository
import com.example.catsappchallenge.ui.screens.CatBreedsScreen
import com.example.catsappchallenge.ui.screens.Screen
import com.example.catsappchallenge.ui.theme.CatsAppChallengeTheme
import com.example.catsappchallenge.viewmodel.BreedViewModel
import com.example.catsappchallenge.viewmodel.BreedViewModelFactory

class MainActivity : ComponentActivity() {
    @Composable
    fun AppNavigation(breedViewModel: BreedViewModel) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.BreedsListScreen.route) {
            navigationGraph(navController = navController, breedViewModel = breedViewModel)
        }
    }

    private fun NavGraphBuilder.navigationGraph(
        navController: NavController,
        breedViewModel: BreedViewModel
    ) {
        composable(route = Screen.BreedsListScreen.route) {
            CatBreedsScreen(
                navController = navController,
                breedViewModel = breedViewModel,
                modifier = Modifier
            )
        }

        // TODO: Favourite breeds' list screen

        // TODO: Breed info screen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val breedDao = CatsAppDatabase.getDatabase(applicationContext).breedDao
        val breedRepository = BreedRepository(applicationContext, breedDao)

        enableEdgeToEdge()
        setContent {
            val breedViewModel: BreedViewModel =
                viewModel(factory = BreedViewModelFactory(breedRepository))
            val isLoading = breedViewModel.isLoading.value
            CatsAppChallengeTheme {
                // TODO: Confirm if this works offline and with problems on the api
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {
                        AppNavigation(breedViewModel = breedViewModel)
                    }
                }
            }
        }
    }
}
