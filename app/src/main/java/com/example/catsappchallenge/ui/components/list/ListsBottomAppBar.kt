package com.example.catsappchallenge.ui.components.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.catsappchallenge.ui.screens.Screen

@Composable
fun ListsBottomAppBar(navController: NavController) {
    BottomAppBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        IconButton(
            onClick = {
                navController.navigate(Screen.BreedsListScreen.route) {
                    popUpTo(0)
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Breeds List",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        VerticalDivider(
            modifier = Modifier.padding(vertical = 15.dp),
            color = Color.White
        )
        IconButton(
            onClick = {
                // TODO: Favourite list
            },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Favourites List",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}