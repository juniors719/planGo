package com.example.plango.ui.components.bottomnavigationbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.PlanGoTheme
import kotlin.collections.listOf

data class NavigationItem(
    val icon: @Composable () -> Unit,
    val contentDescription: String,
    val label: String
)

@Composable
fun PlanGoBottonNavBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val navigationItems = listOf(
        NavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            contentDescription = "Home",
            label = "Home"
        ),
        NavigationItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_explore), contentDescription = "Explore") },
            contentDescription = "Explore",
            label = "Explorar"
        ),
        NavigationItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_account_circle), contentDescription = "Profile") },
            contentDescription = "Profile",
            label = "Perfil"
        )
    )
    val selectedItem = remember { mutableStateOf(0) }
    NavigationBar(modifier = modifier) {
        navigationItems.forEachIndexed {
            index, item ->
            NavigationBarItem(
                icon = item.icon,
                label = {
                    Text(item.label)
                },
                selected = selectedItem.value == index,
                onClick = {
                    selectedItem.value = index
                    when (index) {
                        0 -> navController.navigate(Routes.HOME)
                        1 -> navController.navigate(Routes.EXPLORE)
                        2 -> navController.navigate(Routes.PROFILE)
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Preview
@Composable
private fun PlanGoBottonNavBarPreview() {
    PlanGoTheme {
        PlanGoBottonNavBar(
            modifier = Modifier.fillMaxWidth(),
            navController = NavController(LocalContext.current)
        )
    }
}