package com.example.plango.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.data.repository.AuthRepository
import com.example.plango.viewmodel.AuthViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthViewModel
) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        if (viewModel.isUserLogged()){
            navController.navigate("home")
        } else {
            navController.navigate("welcome")
        }
    }

    val systemUiController = rememberSystemUiController()
    val backgroundColor = Color(0xFF45C09C)

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = backgroundColor,
            darkIcons = false
        )
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(id = R.drawable.bg_splashscreen), // Replace with your image resource
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Icon(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.logo_text_mono),
            contentDescription = "Imagem Logo",
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        navController = NavController(LocalContext.current),
        viewModel = AuthViewModel(repository = AuthRepository())
    )
}