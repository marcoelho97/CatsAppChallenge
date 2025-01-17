package com.example.catsappchallenge.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.catsappchallenge.R
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.breedListDTO
import com.example.catsappchallenge.ui.components.FavouriteIcon
import com.example.catsappchallenge.ui.components.ImageBox
import com.example.catsappchallenge.utils.NavigationSpamProtector.onNavigationIconClick
import com.example.catsappchallenge.utils.toastMessage
import com.example.catsappchallenge.viewmodel.BreedViewModel

private fun invalidDetailScreen(
    context: Context,
    navController: NavController,
    message: Int
) {
    toastMessage(context, message)
    navController.popBackStack()
}

@Composable
fun DetailedBreedScreen(
    navController: NavController,
    breedViewModel: BreedViewModel,
    breedId: String?,
    modifier: Modifier
) {
    val context = LocalContext.current
    if (breedId == null) {
        LaunchedEffect(null) {
            invalidDetailScreen(
                context = context,
                navController = navController,
                message = R.string.detailed_breedId_blank
            )
        }
    } else {
        var breed by remember {
            mutableStateOf<Breed?>(null)
        }
        LaunchedEffect(breedId) {
            val fetchedBreed = breedViewModel.getBreedById(breedId)

            // No breed with that ID
            if (fetchedBreed == null) {
                invalidDetailScreen(
                    context = context,
                    navController = navController,
                    message = R.string.detailed_breed_not_found
                )
            } else {
                breed = fetchedBreed
            }
        }
        if (breed == null) {
            LoadingDetailedBreed(navController = navController)
        } else {
            DetailedBreedContent(navController, breedViewModel, breed!!, modifier)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDetailedBreed(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = ""
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationIconClick(navController) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Loading breed details...."
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedBreedContent(
    navController: NavController,
    breedViewModel: BreedViewModel,
    breed: Breed,
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontWeight = FontWeight.ExtraBold,
                        text = breed.name
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationIconClick(navController) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    Box {
                        FavouriteIcon(
                            breedViewModel = breedViewModel,
                            breed = breedListDTO(breed)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp, top = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                ImageBox(
                    breedImage = breed.image,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.FillWidth
                )
            }
            val bodyBold = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            val bodyNormal = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify
            )
            Text(
                text = "Origin: ",
                style = bodyBold
            )
            Text(
                text = breed.origin,
                style = bodyNormal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Temperament: ",
                style = bodyBold
            )
            Text(
                text = breed.temperament.joinToString(separator = ", "),
                style = bodyNormal
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Description: ",
                style = bodyBold
            )
            Text(
                text = breed.description,
                style = bodyNormal
            )
        }
    }
}
