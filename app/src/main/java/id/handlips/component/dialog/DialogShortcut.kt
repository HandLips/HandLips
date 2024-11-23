package id.handlips.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.handlips.R
import id.handlips.component.button.DoubleButton
import id.handlips.component.textfield.DescriptionTextField
import id.handlips.component.textfield.GeneralTextField
import id.handlips.ui.theme.poppins


@Composable
fun DialogShortcut(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var sound by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title
                Text(
                    text = stringResource(R.string.feedback),
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                // Error Message
                Text(
                    text = stringResource(R.string.how_do_you_feel_about_using_this_app),
                    fontFamily = poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp),
                )
                GeneralTextField(
                    title = "Title",
                    onValueChange = { newValue ->
                        title = newValue
                    },
                    value = title,
                    label = "Enter title"
                )
                DescriptionTextField(
                    label = "Saran",
                    onValueChange = { newValue ->
                        sound = newValue
                    },
                    value = sound
                )
                // Buttons
                DoubleButton(
                    onClickCancel = { onDismissRequest() },
                    onClickConfirm = {
                        onConfirm(sound)
                    },
                )
            }
        }
    }
}