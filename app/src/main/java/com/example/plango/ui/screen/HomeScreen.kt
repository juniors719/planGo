package com.example.plango.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plango.data.repository.AuthRepository
import com.example.plango.data.repository.TripRepository
import com.example.plango.ui.components.bottomnavigationbar.BottomNavItem
import com.example.plango.ui.components.bottomnavigationbar.PlanGoBottonNavBar
import com.example.plango.ui.components.dropdownmenu.PlanGoDropdownMenu
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.ui.theme.Typography
import com.example.plango.utils.PlanGoSideEffect
import com.example.plango.viewmodel.AuthViewModel
import com.example.plango.viewmodel.TripViewModel

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController,
    tripViewModel: TripViewModel
) {

    val isMenuOpen = remember { mutableStateOf(false) }
    val mainNavHostController: NavHostController = rememberNavController()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "PlanGo",
                        style = Typography.headlineLarge
                    )
                },
                actions = {
                    PlanGoDropdownMenu(
                        menuState = isMenuOpen,
                        navController = navController
                    )
                }
            )
        },
        bottomBar = {
            PlanGoBottonNavBar(
                modifier = Modifier.fillMaxWidth(),
                navController = mainNavHostController
            )
        }
    ) { paddingValues ->
        PlanGoSideEffect()
        NavHost(
            navController = mainNavHostController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavItem.Home.route) {
                MainScreen(
                    navController = navController,
                    tripViewModel = tripViewModel,
                    tripNavHostController = mainNavHostController
                )
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
            composable(Routes.ADD_TRIP) {
                AddTripScreen(
                    navController = mainNavHostController,
                    tripViewModel = tripViewModel
                )
            }
        }
    }
}


@Preview
@ExperimentalMaterial3Api
@Composable
private fun HomeScreenPreview() {
    PlanGoTheme {
        HomeScreen(
            navController = NavController(LocalContext.current),
            viewModel = AuthViewModel(
                repository = AuthRepository()
            ),
            tripViewModel = TripViewModel(
                repository = TripRepository()
            )
        )
    }
}