package com.example.catsappchallenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.catsappchallenge.network.RetrofitInstance

@Composable
fun ImageBox(
    breedImage: String?,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    var retryAttempted by remember { mutableStateOf(false) }
    var imageUrl by remember {
        mutableStateOf(
            RetrofitInstance.BASE_IMAGE_URL(
                breedImage = breedImage ?: "",
                alternativeExtension = null
            )
        )
    }

    // Size.Original to fix problem between Coil and Scrollable Columns
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .size(Size.ORIGINAL)
            .data(imageUrl)
            .crossfade(true)
            .listener(
                onError = { _, _ ->
                    // Very rare cases' alternative extension is .png
                    if (!retryAttempted && breedImage != null) {
                        imageUrl = RetrofitInstance.BASE_IMAGE_URL(
                            breedImage = breedImage,
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
        contentScale = contentScale,
        modifier = modifier
    )

}
