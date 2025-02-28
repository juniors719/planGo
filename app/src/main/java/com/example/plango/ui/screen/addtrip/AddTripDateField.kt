package com.example.plango.ui.screen.addtrip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plango.R
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.ui.theme.Typography


@Composable
fun AddTripDatefield(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) },
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(text = "DD/MM/AAAA", style = Typography.bodyMedium)
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddTripDatefieldPreview() {
    val selectedDate = remember { mutableStateOf("") }

    PlanGoTheme {
        AddTripDatefield(
            value = selectedDate.value,
            onValueChange = { selectedDate.value = it },
            label = "Data de saída"
        )
    }
}