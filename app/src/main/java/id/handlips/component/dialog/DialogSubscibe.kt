package id.handlips.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.Green
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins

@Composable
fun DialogSubscribe(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    colorBackgroundPrice: Color = Color.White,
    textPrice: String,
    textColor: Color
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = modifier
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = cardBackgroundColor
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = title,
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                // Description
                Text(
                    text = description,
                    fontFamily = poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                // Custom content if provided
                Column {
                    Row {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Green,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.padding(start = 5.dp))
                        Text(text = stringResource(R.string.komunikasi_sepuasnya), fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                    }
                    Row {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Green,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.padding(start = 5.dp))
                        Text(text = stringResource(R.string.shortcut_komunikasi_lebih_banyak), fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                    }
                }

                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(top = 8.dp), // Jarak atas
                    shape = RoundedCornerShape(8.dp), // Membuat sudut melengkung pada harga

                ) {
                    Text(
                        text = textPrice,
                        modifier = Modifier
                            .background(colorBackgroundPrice)
                            .padding(horizontal = 16.dp, vertical = 8.dp), // Padding dalam harga
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                // Buttons
                DoubleButton(
                    onClickCancel = onDismissRequest,
                    onClickConfirm = onConfirm,
                    textConfirm = stringResource(R.string.bayar),
                    buttonCancelColors = ButtonDefaults.buttonColors(
                        containerColor = colorBackgroundPrice,
                        contentColor = Color.Black
                    ),
                )

            }
        }
    }
}