package id.handlips.component.bubble_chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.handlips.ui.theme.Blue

@Composable
fun ChatBubbleComponent(
    message: String,
    time: String,
    isFromMe: Boolean,
    modifier: Modifier = Modifier
) {
    val bubbleColor = if (isFromMe) {
        Blue
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }

    val textColor = if (isFromMe) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

    val arrangement = if (isFromMe) {
        Arrangement.End
    } else {
        Arrangement.Start
    }

    val bubbleShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (isFromMe) 16.dp else 0.dp,
        bottomEnd = if (isFromMe) 0.dp else 16.dp
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = arrangement
    ) {
        Column(
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Surface(
                shape = bubbleShape,
                color = bubbleColor,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text(
                        text = message,
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = time,
                        color = textColor.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}