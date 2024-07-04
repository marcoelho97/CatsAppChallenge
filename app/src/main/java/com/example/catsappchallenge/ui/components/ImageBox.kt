package com.example.catsappchallenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.network.RetrofitInstance

@Composable
fun ImageBox(breed: BreedListDTO) {
    var retryAttempted by remember { mutableStateOf(false) }
    var imageUrl by remember {
        mutableStateOf(
            RetrofitInstance.BASE_IMAGE_URL(
                breedImage = breed.image ?: "",
                alternativeExtension = null
            )
        )
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .listener(
                onError = { _, _ ->
                    // Very rare cases' alternative extension is .png
                    if (!retryAttempted && breed.image != null) {
                        imageUrl = RetrofitInstance.BASE_IMAGE_URL(
                            breedImage = breed.image,
                            alternativeExtension = ".png"
                        )
                        retryAttempted = true
                    }
                }
            )
            .build(),
        contentScale = ContentScale.Crop
    )

    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(128.dp)
    )

}