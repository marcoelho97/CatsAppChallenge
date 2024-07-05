package com.example.catsappchallenge.utils

import androidx.navigation.NavController

object NavigationSpamProtector {
    var lastClickTime: Long = 0L
    const val intervalBetweenClick = 1200L
    fun onNavigationIconClick(navController: NavController) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > intervalBetweenClick) {
            lastClickTime = currentTime
            navController.popBackStack()
        }
    }
}
