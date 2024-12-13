package id.handlips.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import id.handlips.ui.theme.poppins

@Composable
fun DescriptionTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            label = {
                Text(
                    text = label,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default,
            maxLines = 3, // Maksimal 3 baris
            minLines = 3  // Minimal 3 baris
        )
}
