package com.example.plango.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.plango.R
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.ui.theme.Typography

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier.heightIn(min = 56.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google_icon_logo_svgrepo_com), // Substitua pelo seu ícone
                contentDescription = "Logo do Google",
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(R.string.login_with_google_button_text).uppercase(),
                style = Typography.labelLarge,
                color = Color.Black
            )
        }
    }
}

@Preview
@Composable
private fun GoogleSignInPreview() {
    PlanGoTheme {
        GoogleSignInButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}