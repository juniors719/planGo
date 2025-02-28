package com.example.plango.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plango.R
import com.example.plango.ui.components.button.PlanGoButton
import com.example.plango.ui.components.button.PlanGoButtonSecondary
import com.example.plango.ui.components.textfield.PlanGoTextField
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.Typography
import com.example.plango.viewmodel.AuthViewModel


@Composable
fun ProfileScreen(
    viewModel: AuthViewModel,
    navController: NavController
) {
    val profileNavHostController: NavHostController = rememberNavController()
    NavHost(
        navController = profileNavHostController,
        startDestination = Routes.PROFILE
    ) {
        composable(Routes.PROFILE) {
            ProfileInfo(
                viewModel = viewModel,
                navController = navController,
                profileNavHostController = profileNavHostController
            )
        }
        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(
                viewModel = viewModel,
                profileNavHostController = profileNavHostController
            )
        }
    }
}

@Composable
fun ProfileInfo(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController,
    profileNavHostController: NavHostController
) {
    val userState by viewModel.userState

    LaunchedEffect(Unit) {
        viewModel.loadUserData()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto de perfil
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .size(150.dp),
                painter = painterResource(id = R.drawable.pic_profile),
                contentDescription = "Foto de perfil"
            )
            // Nome de usuário
            userState.name?.let {
                Text(
                    text = it,
                    style = Typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            // Email
            userState.email?.let {
                Text(
                    text = it,
                    style = Typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botão de Editar
            PlanGoButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = "Editar",
                onClick = {
                    profileNavHostController.navigate(Routes.EDIT_PROFILE)
                },
                isLoading = viewModel.isLoading
            )
            // Botão de Logout
            PlanGoButtonSecondary(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = "Logout",
                onClick = {
                    viewModel.logout()
                    navController.navigate("login")
                }
            )
        }
    }
}

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    profileNavHostController: NavHostController
) {
    val userState by viewModel.userState
    var editedName by remember { mutableStateOf(userState.name) }

    LaunchedEffect(userState.name) {
        editedName = userState.name // Atualiza quando o nome muda
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.edit_profile_screen_title),
            style = Typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.padding(16.dp))
        editedName?.let {
            PlanGoTextField(
                value = it,
                onValueChange = { editedName = it },
                placeholder = stringResource(R.string.placeholder_name),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_person),
                        contentDescription = "User icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        PlanGoButton(
            text = stringResource(R.string.edit_profile_button),
            onClick = {
                viewModel.editName(editedName ?: "") { success ->
                    if (success) {
                        profileNavHostController.navigate(Routes.PROFILE)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isLoading = viewModel.isLoading
        )
    }
}