package com.example.plango.ui.components.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.R
import com.example.plango.ui.theme.Typography

@Composable
fun WelcomeHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_text_color),
            contentDescription = "PlanGo App Logo"
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.boas_vindas),
            style = Typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(R.string.parceiro_viagens),
            style = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
private fun WelcomeHeaderPreview() {
    WelcomeHeader()
}