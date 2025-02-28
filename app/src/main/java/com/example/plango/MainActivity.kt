package com.example.plango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plango.data.repository.AuthRepository
import com.example.plango.data.repository.TripRepository
import com.example.plango.ui.screen.ForgotPasswordScreen
import com.example.plango.ui.screen.HomeScreen
import com.example.plango.ui.screen.LoginScreen
import com.example.plango.ui.screen.SettingsScreen
import com.example.plango.ui.screen.SignUpScreen
import com.example.plango.ui.screen.SplashScreen
import com.example.plango.ui.screen.TripDetailsScreen
import com.example.plango.ui.screen.WelcomeScreen
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.viewmodel.AuthViewModel
import com.example.plango.viewmodel.AuthViewModelFactory
import com.example.plango.viewmodel.ThemeViewModel
import com.example.plango.viewmodel.TripViewModel
import com.example.plango.viewmodel.TripViewModelFactory

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = AuthRepository()
        val authViewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        val tripRepository = TripRepository()
        val tripViewModel =
            ViewModelProvider(this, TripViewModelFactory(tripRepository))[TripViewModel::class.java]
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            PlanGoTheme(darkTheme = themeViewModel.isDarkMode.value) {
                val navHostCont: NavHostController = rememberNavController()
                NavHost(
                    navController = navHostCont,
                    startDestination = Routes.SPLASH
                ) {
                    composable(Routes.SPLASH) {
                        SplashScreen(viewModel = authViewModel, navController = navHostCont)
                    }
                    composable(Routes.WELCOME) {
                        WelcomeScreen(navController = navHostCont)
                    }
                    composable(Routes.LOGIN) {
                        LoginScreen(viewModel = authViewModel, navController = navHostCont)
                    }
                    composable(Routes.SIGN_UP) {
                        SignUpScreen(viewModel = authViewModel, navController = navHostCont)
                    }
                    composable(Routes.HOME) {
                        HomeScreen(
                            navController = navHostCont,
                            viewModel = authViewModel,
                            tripViewModel = tripViewModel
                        )
                    }
                    composable(Routes.SETTINGS) {
                        SettingsScreen(
                            themeViewModel = themeViewModel,
                            navController = navHostCont
                        )
                    }
                    composable(Routes.FORGOT_PASSWORD) {
                        ForgotPasswordScreen(
                            navController = navHostCont,
                            viewModel = authViewModel
                        )
                    }
                    composable(Routes.TRIP_DETAILS + "/{tripId}") { backStackEntry ->
                        val tripId = backStackEntry.arguments?.getString("tripId")
                        TripDetailsScreen(
                            tripId = tripId,
                            navController = navHostCont,
                            tripViewModel = tripViewModel
                        )
                    }
                }
            }
        }
    }
}