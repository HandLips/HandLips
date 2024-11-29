package id.handlips.views.menu_home.subscribe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.component.dialog.DialogSubscribe
import id.handlips.ui.theme.Green
import id.handlips.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscribeScreen(modifier: Modifier = Modifier, onCLickBack: () -> Unit) {
    var dialogShow by remember { mutableStateOf(false) }
    var nameDialog by remember { mutableStateOf("") }
    val context = LocalContext.current
    if (dialogShow){
        when(nameDialog){
            stringResource(R.string.gold) -> DialogSubscribe(
                onDismissRequest = { dialogShow = false },
                onConfirm = { dialogShow = false },
                title = stringResource(R.string.gold),
                description = stringResource(R.string.berlangganan_selama_1_tahun),
                textColor = Gold.colorText,
                textPrice = stringResource(R.string.rp_360_000),
                colorBackgroundPrice = Gold.backgroundPrice,
                cardBackgroundColor = Gold.backgroundColour
            )
            stringResource(R.string.silver) -> DialogSubscribe(
                onDismissRequest = { dialogShow = false },
                onConfirm = { dialogShow = false },
                title = stringResource(R.string.silver),
                description = "Berlangganan selama 1 bulan",
                textColor = Silver.colorText,
                textPrice = "Rp. 40.000",
                colorBackgroundPrice = Silver.backgroundPrice,
                cardBackgroundColor = Silver.backgroundColour,
            )
            stringResource(R.string.bronze) -> DialogSubscribe(
                onDismissRequest = { dialogShow = false },
                onConfirm = { dialogShow = false },
                title = stringResource(R.string.bronze),
                description = "Berlangganan selama 1 minggu",
                textColor = Bronze.colorText,
                textPrice = "Rp. 15.000",
                colorBackgroundPrice = Bronze.backgroundPrice,
                cardBackgroundColor = Bronze.backgroundColour

            )
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {

            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onCLickBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back_icon)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.langganan),
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },

                )

        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.mulai_berlangganan_sekarang),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = stringResource(R.string.harga_yang_murah_untuk_manfaat_yang_lebih_banyak),
                fontFamily = poppins,
                color = Color.Gray
            )
            Spacer(Modifier.padding(top = 15.dp))
            Row {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Green
                )
                Spacer(Modifier.padding(start = 5.dp))
                Text(text = stringResource(R.string.komunikasi_sepuasnya))
            }
            Spacer(Modifier.padding(top = 10.dp))
            Row {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Green
                )
                Spacer(Modifier.padding(start = 5.dp))
                Text(text = stringResource(R.string.shortcut_komunikasi_lebih_banyak))
            }
            Spacer(Modifier.padding(bottom = 15.dp))
            // Button Subscribe
            MenuSubscribe(onClickGold = {
                dialogShow = true
                nameDialog = context.getString(R.string.gold)
            }, onClickSilver = {
                dialogShow = true
                nameDialog = context.getString(R.string.silver)
            }, onClickBronze = {
                dialogShow = true
                nameDialog = context.getString(R.string.bronze)
            })
        }
    }
}
object Gold {
    val backgroundColour = Color(red = 255, green = 223, blue = 0)
    val backgroundPrice = Color(red = 255, green = 241, blue = 145)
    val colorText = Color(red = 255, green = 123, blue = 0)
}

object Silver {
    val backgroundColour = Color(red = 192, green = 192, blue = 192)
    val backgroundPrice = Color(red = 232, green = 232, blue = 232)
    val colorText = Color(red = 99, green = 99, blue = 99)
}

object Bronze {
    val backgroundColour = Color(red = 151, green = 106, blue = 83)
    val backgroundPrice = Color(red = 187, green = 141, blue = 118)
    val colorText =  Color(red = 82, green = 28, blue = 0)
}

@Composable
fun MenuSubscribe(onClickGold: () -> Unit, onClickSilver: () -> Unit, onClickBronze: () -> Unit) {

    Spacer(Modifier.padding(10.dp))
    CardSubscribe(
        backgroundColour = Gold.backgroundColour,
        colourBackgroundPrice = Gold.backgroundPrice,
        textCategory = stringResource(R.string.gold),
        textDescription = stringResource(R.string.berlangganan_selama_1_tahun),
        textPrice = stringResource(R.string.rp_360_000),
        colorText = Gold.colorText,
        onCLick = onClickGold
    )
    Spacer(Modifier.padding(10.dp))
    CardSubscribe(
        colorText = Silver.colorText,
        colourBackgroundPrice = Silver.backgroundPrice,
        textCategory = stringResource(R.string.silver),
        textDescription = stringResource(R.string.berlangganan_selama_1_bulan),
        textPrice = stringResource(R.string.rp_40_000),
        backgroundColour = Silver.backgroundColour,
        onCLick = onClickSilver
    )
    Spacer(Modifier.padding(10.dp))
    CardSubscribe(
        backgroundColour = Bronze.backgroundColour,
        colourBackgroundPrice = Bronze.backgroundPrice,
        textCategory = stringResource(R.string.bronze),
        textDescription = stringResource(R.string.berlangganan_selama_1_minggu),
        textPrice = stringResource(R.string.rp_15_000),
        colorText = Bronze.colorText,
        onCLick = onClickBronze
    )
}

@Composable
fun CardSubscribe(
    backgroundColour: Color = Color.Yellow,
    colourBackgroundPrice: Color = Color.White,
    colorText: Color = Color.Red,
    textCategory: String,
    textDescription: String,
    textPrice: String,
    onCLick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        onClick = onCLick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColour)
                .padding(16.dp),

            ) {
            Text(
                text = textCategory,
                color = colorText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = textDescription,
                color = colorText,
                fontSize = 14.sp
            )
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    text = textPrice,
                    modifier = Modifier
                        .background(colourBackgroundPrice)
                        .padding(horizontal = 16.dp, vertical = 8.dp), // Padding dalam harga
                    color = colorText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
