package id.handlips.component.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.ui.theme.poppins

@Composable
fun GeneralTextField(
    title: String,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
){
    Column(modifier = Modifier.padding(top = 15.dp)) {
        Text(text = title, fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 16.sp)

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            label = { Text(text = label, fontFamily = poppins, fontWeight = FontWeight.Normal, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
    }

}