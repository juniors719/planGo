package com.example.plango.ui.screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.plango.R
import com.example.plango.data.repository.AuthRepository
import com.example.plango.ui.components.button.PlanGoButton
import com.example.plango.ui.components.textfield.PlanGoTextField
import com.example.plango.ui.screen.route.Routes
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Routes.LOGIN)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "rollback icon"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Cabeçalho
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.forgot_password_illustration),
                        contentDescription = "forgot password illustration",
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = stringResource(R.string.forgot_password_screen_header),
                        fontSize = 32.sp,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                // Texto explicativo
                Text(
                    text = stringResource(R.string.forgot_password_instructions),
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
                // Campo de email
                PlanGoTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    },
                    placeholder = stringResource(R.string.placeholder_email),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_email),
                            contentDescription = "Email icon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    errorText = if (!isEmailValid) {
                        stringResource(R.string.invalid_email_warnig)
                    } else {
                        null
                    }
                )
                // Botão de recuperação de senha
                PlanGoButton(
                    text = stringResource(R.string.forgot_password_button),
                    onClick = {
                        if (isEmailValid && email.isNotEmpty()) {
                            viewModel.resetPassword(email) { success ->
                                if (success) {
                                    Toast.makeText(
                                        context,
                                        "Email de recuperação enviado!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    navController.navigate("login")
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Erro ao enviar email",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Insira um email válido", Toast.LENGTH_SHORT)
                                .show()
                        }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = viewModel.isLoading
                )
            }
        }
    }
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
    PlanGoTheme {
        ForgotPasswordScreen(
            viewModel = AuthViewModel(
                repository = AuthRepository()
            ),
            navController = NavController(LocalContext.current)
        )
    }
}