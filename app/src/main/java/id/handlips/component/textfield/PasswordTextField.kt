package id.handlips.component.textfield

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.ui.theme.poppins

@Composable
fun PasswordTextField(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var isPasswordVisible by remember { mutableStateOf(false) } // Mengatur visibility password
    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(top = 5.dp)) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )

        OutlinedTextField(value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                isError = newValue.length < 8
            },
            isError = isError,
            label = {
                Text(
                    text = label,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon =
                    if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                val description = if (isPasswordVisible) "Hide password" else "Show password"
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(id = icon), contentDescription = description
                    )
                }
            })

        if (isError) {
            Text(
                text = stringResource(R.string.error_password_at_least_8_characters),
                color = MaterialTheme.colorScheme.error,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
