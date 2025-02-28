package com.example.plango.viewmodel

import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
//    val isDarkMode: MutableState<Boolean> = mutableStateOf(false)
    val isDarkMode: MutableState<Boolean> = mutableStateOf(
        when (Resources.getSystem().configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    )

    fun toggleTheme() {
        isDarkMode.value = !isDarkMode.value
    }
}