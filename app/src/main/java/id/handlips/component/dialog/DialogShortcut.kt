package id.handlips.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins


@Composable
fun DialogShortcut(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    title: String,
    sound: String,
    onTitleChange: (String) -> Unit,
    onSoundChange: (String) -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier
                    .background(White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Shortcut",
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(R.string.add_shortcut),
                    fontFamily = poppins,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp),
                )
                Spacer(Modifier.padding(10.dp))
                GeneralTextField(
                    title = stringResource(R.string.title),
                    onValueChange = onTitleChange,
                    value = title,
                    label = "Enter title"
                )
                Column {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.text_suara),
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    DescriptionTextField(
                        label = stringResource(R.string.masukan_text_suara),
                        onValueChange = onSoundChange,
                        value = sound
                    )
                }
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