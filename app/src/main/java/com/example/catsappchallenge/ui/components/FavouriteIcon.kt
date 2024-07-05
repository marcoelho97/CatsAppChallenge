package com.example.catsappchallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.viewmodel.BreedViewModel
import kotlinx.coroutines.launch

@Composable
fun BoxScope.FavouriteIcon(breedViewModel: BreedViewModel, breed: BreedListDTO) {
    var isFavourite by remember { mutableStateOf(breed.favourite) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                breedViewModel.viewModelScope.launch {
                    if (breedViewModel.updateFavourite(breed.id, !breed.favourite)) {
                        isFavourite = !isFavourite
                    }
                }
            },
    ) {
        // Better solution without adding the heavy library material-icons-extended
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isFavourite) Color.White else Color.Black,
            modifier = Modifier
                .size(36.dp)
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (isFavourite) Color.Black else Color.White,
            modifier = Modifier
                .size(30.dp)
        )
    }
}
