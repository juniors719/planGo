package com.example.plango.ui.screen.addtrip

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plango.ui.components.textfield.PlanGoTextField

@Composable
fun AddTripTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        PlanGoTextField(
            value = value,
            onValueChange = { if (!readOnly) onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onClick,
            leadingIcon = leadingIcon,
            singleLine = true,
            placeholder = placeholder,
            readOnly = readOnly
        )
    }
}

@Preview
@Composable
private fun AddTripDestinationPreview() {
    val context = LocalContext.current
    AddTripTextField(
        value = "",
        onValueChange = {},
        label = "Destino",
        placeholder = "Digite o destino",
        onClick = {
            Toast.makeText(context, "Cliquei", Toast.LENGTH_SHORT).show()
        },
        readOnly = true
    )
}