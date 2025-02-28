package com.example.plango.ui.components.textfield

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plango.ui.theme.PlanGoTheme
import com.example.plango.ui.theme.Typography

@Composable
fun PlanGoTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = true,
    leadingIcon: (@Composable (() -> Unit))? = null,
    trailingIcon: (@Composable (() -> Unit))? = null,
    errorText: String? = null,
    readOnly: Boolean = false,
    onClick: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {  })
        }) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                label = label?.let {
                    { Text(text = it, style = Typography.bodyMedium) }
                },
                placeholder = placeholder?.let {
                    { Text(text = it, style = Typography.bodyMedium) }
                },
                maxLines = maxLines,
                singleLine = singleLine,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                shape = RoundedCornerShape(8.dp),
                isError = errorText != null,
                readOnly = readOnly,
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                )
            )
            errorText?.let {
                Text(
                    text = it,
                    style = Typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp, start = 8.dp)
                )
            }
        }
    }
}



@Preview
@Composable
private fun PlanGoTextFieldPreview() {
    PlanGoTheme {
        PlanGoTextField(
            value = "",
            onValueChange = {},
            label = "Destino",
            placeholder = "Digite o destino",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = com.example.plango.R.drawable.ic_map_pin),
                    contentDescription = null
                )
            },
            errorText = "Campo obrigatório"
        )
    }
}
