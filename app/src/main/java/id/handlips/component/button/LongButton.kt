import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White

@Composable
fun LongButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Blue,
        contentColor = White
    )
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth().padding(top = 20.dp).height(50.dp),
        enabled = enabled,
        colors = buttonColors,
        shape = Shapes().small
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
