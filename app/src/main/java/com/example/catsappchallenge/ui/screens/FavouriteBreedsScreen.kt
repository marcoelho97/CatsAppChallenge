package com.example.catsappchallenge.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.catsappchallenge.ui.components.SearchBar
import com.example.catsappchallenge.ui.components.list.CatsGrid
import com.example.catsappchallenge.ui.components.list.ListsBottomAppBar
import com.example.catsappchallenge.viewmodel.BreedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteBreedsScreen(
    navController: NavController,
    breedViewModel: BreedViewModel,
    modifier: Modifier
) {
    val breedList = breedViewModel.breedList.value
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    fontWeight = FontWeight.ExtraBold, text = "Favourites"
                )
            })
        },
        bottomBar = {
            ListsBottomAppBar(
                navController = navController,
                breedViewModel = breedViewModel
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            SearchBar()
            Text(
                text = "Average lifespan: ${breedViewModel.averageLifeSpan}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 20.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            CatsGrid(
                navController = navController,
                breedList = breedList,
                breedViewModel = breedViewModel
            )
        }
    }
}
