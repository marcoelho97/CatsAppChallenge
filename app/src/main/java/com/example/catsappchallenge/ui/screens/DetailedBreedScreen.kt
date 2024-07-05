package com.example.catsappchallenge.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.catsappchallenge.data.model.Breed
import com.example.catsappchallenge.data.model.breedListDTO
import com.example.catsappchallenge.ui.components.FavouriteIcon
import com.example.catsappchallenge.ui.components.ImageBox
import com.example.catsappchallenge.viewmodel.BreedViewModel

private fun invalidDetailScreen(
    context: Context,
    navController: NavController,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                message = "Invalid"
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
                    message = "Invalid2"
                )
            } else {
                breed = fetchedBreed
            }
        }
        if (breed == null) {
            // TODO: Make this more presentable and with a return button, in case it takes too long
            Text(text = "Loading breed details....")
        } else {
            DetailedBreedContent(navController, breedViewModel, breed!!, modifier)
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
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
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
