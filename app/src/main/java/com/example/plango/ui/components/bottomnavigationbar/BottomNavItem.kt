package com.example.plango.ui.components.bottomnavigationbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.plango.R
import com.example.plango.ui.screen.route.Routes

sealed class BottomNavItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    data object Home : BottomNavItem(
        route = Routes.HOME,
        title = R.string.title_home_screen,
        icon = R.drawable.ic_home
    )

    data object Explore : BottomNavItem(
        route = Routes.EXPLORE,
        title = R.string.title_explore_screen,
        icon = R.drawable.ic_explore
    )

    data object Profile : BottomNavItem(
        route = Routes.PROFILE,
        title = R.string.title_profile_screen,
        icon = R.drawable.ic_person
    )
}