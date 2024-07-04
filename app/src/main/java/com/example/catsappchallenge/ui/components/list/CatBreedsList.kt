package com.example.catsappchallenge.ui.components.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.ui.components.FavouriteIcon
import com.example.catsappchallenge.ui.components.ImageBox
import com.example.catsappchallenge.viewmodel.BreedViewModel

@Composable
fun CatsGrid(breedList: List<BreedListDTO>, breedViewModel: BreedViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(breedList, key = { it.id }) { breed ->
            CatCard(breed = breed, breedViewModel)
        }
    }
}

@Composable
fun CatCard(breed: BreedListDTO, breedViewModel: BreedViewModel) {


    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            ImageBox(breed = breed)
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
