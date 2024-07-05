package com.example.catsappchallenge.ui.components.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.ui.components.FavouriteIcon
import com.example.catsappchallenge.ui.components.ImageBox
import com.example.catsappchallenge.ui.screens.Screen
import com.example.catsappchallenge.viewmodel.BreedViewModel

@Composable
fun CatsGrid(
    navController: NavController,
    breedList: List<BreedListDTO>,
    breedViewModel: BreedViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(breedList, key = { it.id }) { breed ->
            CatCard(
                navController = navController,
                breed = breed,
                breedViewModel = breedViewModel
            )
        }
    }
}

@Composable
fun CatCard(navController: NavController, breed: BreedListDTO, breedViewModel: BreedViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                if (breed.id.isBlank()) {
                    // TODO: Better messages, preventing code repetition
                    Toast
                        .makeText(
                            context,
                            "No detailed information about this breed, for now.",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                } else {
                    navController.navigate(Screen.DetailedBreedScreen.createRoute(breed.id))
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            ImageBox(
                breedImage = breed.image,
                modifier = Modifier.size(128.dp)
            )
            FavouriteIcon(breedViewModel = breedViewModel, breed = breed)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = breed.name,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}
