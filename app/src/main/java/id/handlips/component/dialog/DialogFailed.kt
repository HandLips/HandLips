package id.handlips.component.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil3.compose.rememberAsyncImagePainter
import id.handlips.R
import id.handlips.component.button.LongButton
import id.handlips.ui.theme.Red
import id.handlips.ui.theme.poppins

@Composable
fun DialogError(
    onDismissRequest: () -> Unit,
    textError: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Title
                Text(
                    text = stringResource(R.string.oops),
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                // Error Message
                Text(
                    text = textError,
                    fontFamily = poppins,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp),
                )
                // Error Image
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.ic_error),

                    contentDescription = stringResource(R.string.error_image_description),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(top = 16.dp),
                )
                // Button Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    LongButton(
                        onClick = { onDismissRequest() },
                        text = stringResource(R.string.back),
                        buttonColors = buttonColors(
                            containerColor = Red,
                            contentColor = Color.White
                        )
                    )
                }
            }
        }
    }
}
