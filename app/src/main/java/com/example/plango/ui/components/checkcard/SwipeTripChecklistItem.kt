package com.example.plango.ui.components.checkcard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SwipeTripChecklistItem(
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    isChecked: MutableState<Boolean>,
    onDelete: () -> Unit,
    itemName: String,
) {

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    isChecked.value = !isChecked.value
                    onCheckedChange(isChecked.value)
                    true
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    isChecked.value = false
                    onDelete()
                    true
                }

                else -> false
            }
        }
    )

    LaunchedEffect(isChecked.value) {
        dismissState.reset()
    }

    AnimatedVisibility( // 🔥 Garante que o item desapareça ao deletar
        visible = dismissState.targetValue != SwipeToDismissBoxValue.EndToStart,
        exit = shrinkVertically() + fadeOut()
    ) {
        SwipeToDismissBox(
            modifier = modifier,
            state = dismissState,
            backgroundContent = {
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        SwipeToDismissBoxValue.Settled -> Color.LightGray
                        SwipeToDismissBoxValue.StartToEnd -> Color(0xFF0F7532)
                        SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.StartToEnd -> Arrangement.Start
                        SwipeToDismissBoxValue.EndToStart -> Arrangement.End
                        else -> Arrangement.Center
                    }
                ) {
                    if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "Marcar como concluído",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    } else if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir item",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
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
    }
}

@Preview
@Composable
private fun SwipeTipChecklistItemPreview() {
    SwipeTripChecklistItem(
        modifier = Modifier.fillMaxWidth(),
        onCheckedChange = {},
        isChecked = remember { mutableStateOf(false) },
        onDelete = {},
        itemName = "Tênis de Caminhada"
    )
}