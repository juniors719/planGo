package com.example.plango.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plango.viewmodel.ThemeViewModel
import com.example.plango.ui.theme.Typography

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    navController: NavController
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Configurações",
                        style = Typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home")
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = themeViewModel.isDarkMode.value,
                    onCheckedChange = {
                        themeViewModel.toggleTheme()
                    },
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Tema escuro",
                    style = Typography.titleMedium
                )
            }
        }
    }
}