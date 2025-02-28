package com.example.plango.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun PlanGoSideEffect(modifier: Modifier = Modifier) {
    val systemUiController = rememberSystemUiController()
    val backgroundColor = MaterialTheme.colorScheme.background
    val isDarkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = !isDarkTheme
        )
    }
}