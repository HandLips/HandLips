package id.handlips.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.ui.theme.poppins

@Composable
fun EmailTextField(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    var isError by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(top = 5.dp)) {
        Text(text = title, fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 16.sp)

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
                isError = !isValidEmail(newValue)
            },
            isError = isError,
            label = { Text(text = label, fontFamily = poppins, fontWeight = FontWeight.Normal, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        if (isError) {
            Text(
                text = stringResource(R.string.error_invalid_email),
                color = MaterialTheme.colorScheme.error,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
