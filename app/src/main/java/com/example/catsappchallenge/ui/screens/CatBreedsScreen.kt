package com.example.catsappchallenge.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.catsappchallenge.data.model.BreedListDTO
import com.example.catsappchallenge.network.RetrofitInstance
import com.example.catsappchallenge.viewmodel.BreedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatBreedsScreen(
    navController: NavController,
    breedViewModel: BreedViewModel,
    modifier: Modifier
) {
    val breedList = breedViewModel.breedList.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.ExtraBold,
                        text = "Cats App"
                    )
                }
            )
        },
        bottomBar = {
            // TODO: Bottom navigation bar
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            SearchBar()
            CatsGrid(breedList = breedList)
        }
    }
}

@Composable
fun SearchBar() {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = textState,
            onValueChange = {
                textState = it
                // Query
            },
            placeholder = {
                Text(text = "Search...")
            },
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CatsGrid(breedList: List<BreedListDTO>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(breedList) { breed ->
            CatCard(breed = breed)
        }
    }
}

@Composable
fun CatCard(breed: BreedListDTO) {
    var retryAttempted by remember { mutableStateOf(false) }
    var imageUrl by remember {
        mutableStateOf(
            RetrofitInstance.BASE_IMAGE_URL(
                breedImage = breed.image ?: "",
                alternativeExtension = null
            )
        )
    }
    var isFavourite by remember { mutableStateOf(false) }
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

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(128.dp)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        // TODO: ViewModel to change favourite status
                        isFavourite = !isFavourite
                    },
                contentAlignment = Alignment.Center
            ) {
                // Better solution without adding the heavy library material-icons-extended
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(42.dp)
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
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = breed.name,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}
