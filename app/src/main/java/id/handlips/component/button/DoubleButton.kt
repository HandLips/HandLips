package id.handlips.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White

@Composable
fun DoubleButton(
    onClickCancel: () -> Unit,
    onClickConfirm: () -> Unit,
    buttonConfirmColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Blue,
        contentColor = White
    ),
    buttonCancelColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = White,
        contentColor = Blue
    ),
    textCancel: String = stringResource(R.string.cancel),
    textConfirm: String = stringResource(R.string.confirm)
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = onClickCancel,
            modifier = Modifier
                .padding(top = 20.dp)
                .height(50.dp),
            colors = buttonCancelColors,
            shape = Shapes().small
        ) {
            Text(
                text = textCancel,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.padding(start = 20.dp))
        Button(
            onClick = onClickConfirm,
            modifier = Modifier
                .padding(top = 20.dp)
                .height(50.dp),
            colors = buttonConfirmColors,
            shape = Shapes().small
        ) {
            Text(
                text = textConfirm,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}