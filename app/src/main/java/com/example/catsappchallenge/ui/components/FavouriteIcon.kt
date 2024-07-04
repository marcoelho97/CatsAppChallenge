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
import androidx.compose.runtime.remember
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
                // TODO: ViewModel to change favourite status
                breedViewModel.viewModelScope.launch {
                    breedViewModel.updateFavourite(breed.id, !breed.favourite)
                }
            },
    ) {
        // Better solution without adding the heavy library material-icons-extended
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (breed.favourite) Color.White else Color.Black,
            modifier = Modifier
                .size(42.dp)
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = if (breed.favourite) Color.Black else Color.White,
            modifier = Modifier
                .size(30.dp)
        )
    }
}