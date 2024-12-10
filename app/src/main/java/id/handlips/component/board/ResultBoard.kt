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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.component.button.CircleButton
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.Red
import id.handlips.ui.theme.White
import id.handlips.views.translator.TranslatorMode

@Composable
fun ResultBoard(
    result: String,
    translatorMode: TranslatorMode = TranslatorMode.HAND_SIGN,
) {
    Card(
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
                            icon = Icons.Filled.Star,
                            containerColor = Red,
                            contentColor = White,
                            onClick = {},
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
                        icon = Icons.Filled.Refresh,
                        onClick = { null },
                        contentDescription = "Riwayat Percakapan",
                    )
                    CircleButton(
                        icon = Icons.Filled.Call,
                        onClick = { null },
                        contentDescription = "Terjemah Suara ke Teks",
                    )
                }
            }
        }
    }
}
