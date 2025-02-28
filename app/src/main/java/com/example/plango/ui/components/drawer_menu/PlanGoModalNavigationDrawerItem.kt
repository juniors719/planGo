package com.example.plango.ui.components.drawer_menu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun PlanGoModalNavigationDrawerItem(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    scope: CoroutineScope,
    label: String,
    @DrawableRes iconRes: Int
) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        icon = {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = iconRes),
                contentDescription = "Ícone de $label"
            )
        },
        selected = false,
        onClick = {
            scope.launch { drawerState.close() }
            // Handle navigation
        }
    )
}