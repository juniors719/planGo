package com.example.plango.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.ui.components.textfield.PlanGoTextField
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChecklistItemDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAddItem: (String) -> Unit
) {
    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                var itemText by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Adicionar Item", style = Typography.headlineMedium)

                    Spacer(modifier = Modifier.height(8.dp))

//                    OutlinedTextField(
//                        value = itemText,
//                        onValueChange = { itemText = it },
//                        label = { Text("Nome do item") },
//                        modifier = Modifier.fillMaxWidth()
//                    )

                    PlanGoTextField(
                        value = itemText,
                        onValueChange = { itemText = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { onDismiss() }) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            if (itemText.isNotBlank()) {
                                onAddItem(itemText)
                                onDismiss()
                            }
                        }) {
                            Text("Adicionar")
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun AddCheckListItemDialogPreview() {
    PlanGoTheme {
        AddChecklistItemDialog(
            showDialog = true,
            onDismiss = {},
            onAddItem = {}
        )
    }
}