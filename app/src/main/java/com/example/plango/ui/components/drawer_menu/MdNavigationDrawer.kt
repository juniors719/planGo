package com.example.plango.ui.components.drawer_menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.plango.R
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@Composable
fun MdNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(color = Color.White)
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 40.dp),
                    painter = painterResource(id = R.drawable.logo_inline_color),
                    contentDescription = "Logo Inline Home"
                )
            }
            PlanGoModalNavigationDrawerItem(
                drawerState = drawerState,
                scope = scope,
                label = "Home",
                iconRes = R.drawable.ic_home
            )
            PlanGoModalNavigationDrawerItem(
                drawerState = drawerState,
                scope = scope,
                label = "Configurações",
                iconRes = R.drawable.ic_settings
            )
            PlanGoModalNavigationDrawerItem(
                drawerState = drawerState,
                scope = scope,
                label = "Sobre",
                iconRes = R.drawable.ic_about
            )
        }
    }
}