package com.example.plango.ui.components.dropdownmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.R

@Composable
fun PlanGoDropDownMenuItem(
    modifier: Modifier = Modifier,
    label: String? = null,
    iconRes: Int? = null,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (iconRes != null) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = "{$label} icon menu"
                    )
                }
                if (label != null) {
                    Text(text = label)
                }
            }
        },
        onClick = onClick
    )
}

@Preview
@Composable
private fun PlanGoDropDownMenuItemPreview() {
    PlanGoDropDownMenuItem(
        label = "Home",
        iconRes = R.drawable.ic_home,
        onClick = {}
    )
}