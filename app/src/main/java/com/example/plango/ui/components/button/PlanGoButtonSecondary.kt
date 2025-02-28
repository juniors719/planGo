package com.example.plango.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.R
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.ui.theme.Typography

@Composable
fun PlanGoButtonSecondary(
    modifier: Modifier = Modifier,
    text: String? = null,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    upperCase: Boolean = true
) {
    Button(
        modifier = modifier
            .heightIn(min = 56.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = if (text == null && iconRes != null) PaddingValues(0.dp) else ButtonDefaults.ContentPadding,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            iconRes?.let {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = "Ícone do botão"
                )
            }
            text?.let { Text(
                text = if (upperCase) it.uppercase() else it,
                style = Typography.labelLarge
            ) }
        }
    }

}

@Preview
@Composable
private fun PlanGoButtonSecondaryPreview() {
    PlanGoTheme {
        PlanGoButtonSecondary(
            modifier = Modifier.fillMaxWidth(),
            text = "Teste",
            iconRes = R.drawable.ic_scan,
            onClick = {}
        )
    }

}

@Preview
@Composable
private fun PlanGoButtonSecondaryNoIconPreview() {
    PlanGoButtonSecondary(
        modifier = Modifier.fillMaxWidth(),
        text = "Teste",
        onClick = {}
    )
}

@Preview
@Composable
private fun PlanGoButtonSecondaryNoTextPreview() {
    PlanGoButtonSecondary(
        iconRes = R.drawable.ic_arrow_left,
        onClick = {}
    )
}

