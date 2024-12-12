package id.handlips.component.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.component.button.CircleButton
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.Red
import id.handlips.ui.theme.White
import id.handlips.views.translator.SpeechState
import id.handlips.views.translator.TranslatorMode

@Composable
fun ResultBoard(
    modifier: Modifier = Modifier,
    result: String,
    translatorMode: TranslatorMode = TranslatorMode.HAND_SIGN,
    speechState: SpeechState = SpeechState.STOPPED,
    onClickTranslatorMode: () -> Unit,
    onSpeechButtonClick: () -> Unit,
) {
    val speechContainerColor: Color =
        when (speechState) {
            SpeechState.RECORDING -> White
            SpeechState.STOPPED -> Red
        }

    val speechContentColor: Color =
        when (speechState) {
            SpeechState.RECORDING -> Color.Black
            SpeechState.STOPPED -> White
        }

    val speechStateIcon: Painter =
        when (speechState) {
            SpeechState.RECORDING -> painterResource(id = R.drawable.round_stop)
            SpeechState.STOPPED -> painterResource(id = R.drawable.round_fiber_manual_record)
        }
    val translatorModeIcon: Painter =
        when (translatorMode) {
            TranslatorMode.HAND_SIGN -> painterResource(id = R.drawable.ic_mic)
            TranslatorMode.SPEECH -> painterResource(id = R.drawable.ic_camera)
        }

    Card(
        modifier = modifier,
        shape =
            RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
            ),
        border = BorderStroke(width = 1.dp, color = Blue),
    ) {
        Box(
            modifier =
                Modifier
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        White,
                                        White,
                                        White,
                                        Blue,
                                    ),
                            ),
                    ).padding(16.dp), // Padding inside the card
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (translatorMode) {
                    TranslatorMode.SPEECH -> {
                        CircleButton(
                            icon = speechStateIcon,
                            containerColor = speechContainerColor,
                            contentColor = speechContentColor,
                            onClick = onSpeechButtonClick,
                            contentDescription = "",
                            size = 120,
                        )
                    }

                    TranslatorMode.HAND_SIGN -> Text(result, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CircleButton(
                        icon = painterResource(id = R.drawable.ic_history),
                        onClick = { null },
                        contentDescription = "Riwayat Percakapan",
                    )
                    CircleButton(
                        icon = translatorModeIcon,
                        onClick = onClickTranslatorMode,
                        contentDescription = "Translator Mode",
                    )
                }
            }
        }
    }
}
