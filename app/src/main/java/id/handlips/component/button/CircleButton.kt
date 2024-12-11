package id.handlips.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White

@Composable
fun CircleButton(
    icon: Painter,
    containerColor: Color = Blue,
    contentColor: Color = White,
    onClick: () -> Unit,
    contentDescription: String,
    size: Int = 94,
) {
    val iconSize = size - (size / 1.5)
    LargeFloatingActionButton(
        onClick = {
            onClick
        },
        shape = CircleShape,
        containerColor = containerColor,
        contentColor = contentColor,
        modifier =
            Modifier
                .shadow(
                    elevation = 24.dp,
                    shape = CircleShape,
                    spotColor = White,
                ).size(size.dp),
    ) {
        Icon(
            icon,
            contentDescription,
            modifier = Modifier.size(iconSize.dp),
        )
    }
}
