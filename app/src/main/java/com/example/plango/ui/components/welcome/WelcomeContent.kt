package com.example.plango.ui.components.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.R

@Composable
fun WelcomeContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        WelcomeTips(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.welcomeContentTitle1),
            subtitle = stringResource(R.string.welcomeContentSubtitle1),
            iconRes = R.drawable.ic_checklist
        )
        WelcomeTips(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.welcomeContentTitle2),
            subtitle = stringResource(R.string.welcomeContentSubtitle2),
            iconRes = R.drawable.ic_calendar
        )
        WelcomeTips(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.welcomeContentTitle3),
            subtitle = stringResource(R.string.welcomeContentSubtitle3),
            iconRes = R.drawable.ic_camera
        )
    }
}

@Preview
@Composable
private fun WelcomeContentPreview() {
    WelcomeContent()
}