package com.example.plango.ui.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.data.repository.AuthRepository
import com.example.plango.ui.components.button.GoogleSignInButton
import com.example.plango.ui.components.button.PlanGoButton
import com.example.plango.ui.components.textfield.PlanGoTextField
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                viewModel.loginWithGoogle(idToken) { success ->
                    if (success) {
                        navController.navigate("home")
                    } else {
                        Toast.makeText(
                            context,
                            "Erro ao fazer login com Google",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        } catch (e: ApiException) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 80.dp)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically() + fadeIn()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(R.drawable.logo_text_mono),
                    contentDescription = "PlanGo App Logo",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(R.string.signup_screen_header),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.signup_screen_subtitle),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(16.dp))
                PlanGoTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = stringResource(R.string.placeholder_name),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_person),
                            contentDescription = "User icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                PlanGoTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = stringResource(R.string.placeholder_email),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_email),
                            contentDescription = "Email icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(8.dp))
                PlanGoTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = stringResource(R.string.placeholder_senha),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_lock),
                            contentDescription = "Password icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    isPassword = true
                )
                Spacer(modifier = Modifier.padding(16.dp))
                PlanGoButton(
                    text = stringResource(R.string.create_account_button_text),
                    onClick = {
                        viewModel.register(email, password, name) { success ->
                            if (success) {
                                navController.navigate(Routes.LOGIN) // Navega para login após sucesso
                            } else {
                                Toast.makeText(context, "Erro no cadastro", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                GoogleSignInButton(
                    onClick = {
                        val signInIntent = viewModel.getGoogleSignInClient(context).signInIntent
                        googleSignInLauncher.launch(signInIntent)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@Preview
@Composable
private fun SignUpScreenPreview() {
    PlanGoTheme() {
        SignUpScreen(
            viewModel = AuthViewModel(repository = AuthRepository()),
            navController = NavController(LocalContext.current)
        )
    }
}