package com.example.plango.ui.components.welcome

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun WelcomeTips(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    @DrawableRes iconRes: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = iconRes),
            contentDescription = "Ícone de dica",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier.padding(top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = Typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitle,
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeTipsPreview() {
    PlanGoTheme {
        WelcomeTips(
            title = "Organize sua mala e tarefas",
            subtitle = "Crie checklists para garantir que você não vai esquecer nada.",
            iconRes = R.drawable.ic_phone
        )
    }
}

