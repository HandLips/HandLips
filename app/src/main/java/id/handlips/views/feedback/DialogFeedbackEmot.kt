package id.handlips.views.feedback

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import id.handlips.ui.theme.poppins

@Composable
fun DialogFeedbackEmot(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var selectedEmotion by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.feedback),
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = stringResource(R.string.how_do_you_feel_about_using_this_app),
                    fontFamily = poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 8.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    listOf(
                        "😁" to "1",
                        "🙂" to "2",
                        "😐" to "3",
                        "☹️" to "4"
                    ).forEach { (emotion) ->
                        EmotionItem(
                            emotion = emotion,
                            isSelected = selectedEmotion == emotion,
                            onSelected = { selectedEmotion = emotion }
                        )
                    }
                }
                // Buttons
                DoubleButton(
                    onClickCancel = { onDismissRequest() },
                    onClickConfirm = {
                        if (selectedEmotion != null) {
                            onConfirm(selectedEmotion!!)
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun EmotionItem(
    emotion: String,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Emoticon
        Text(
            text = emotion,
            fontSize = 24.sp,
        )

        // Checkbox
        RadioButton(
            selected = isSelected,
            onClick = { onSelected() }
        )
    }
}
