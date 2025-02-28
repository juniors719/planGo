package com.example.plango.ui.components.checkcard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.ui.theme.PlanGoTheme

@Composable
fun TripChecklistItem(
    modifier: Modifier = Modifier,
    itemName: String,
    isChecked: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                onCheckedChange(it)
            }
        )
        Text(
            text = itemName,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun TripChecklistItemPreview() {
    val checkedState = remember { mutableStateOf(false) }
    PlanGoTheme {
        TripChecklistItem(
            itemName = "Tênis de Caminhada",
            isChecked = checkedState,
            onCheckedChange = {})
    }
}