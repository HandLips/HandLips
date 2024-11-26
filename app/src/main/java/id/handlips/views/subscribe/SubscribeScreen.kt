package id.handlips.views.subscribe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.ui.theme.Green
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscribeScreen(modifier: Modifier = Modifier, onCLickBack: () -> Unit) {
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
            Row() {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Green
                )
                Spacer(Modifier.padding(start = 5.dp))
                Text(text = stringResource(R.string.komunikasi_sepuasnya))
            }
            Spacer(Modifier.padding(top = 10.dp))
            Row() {
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
            MenuSubscribe()
        }
    }
}

@Composable
fun MenuSubscribe(modifier: Modifier = Modifier) {
    Spacer(Modifier.padding(10.dp))
    CardSubscribe(
        backgroundColour = Color(red = 255, green = 223, blue = 0),
        colourBackgroundPrice = Color(red = 255, green = 241, blue = 145),
        textCategory = "Gold",
        textDescription = "Berlangganan selama 1 tahun",
        textPrice = "Rp. 360.000",
        colorText = Color(red = 255, green = 123, blue = 0)
    )
    Spacer(Modifier.padding(10.dp))
    CardSubscribe(
        colorText = Color(red = 99, green = 99, blue = 99),
        colourBackgroundPrice = Color(red = 232, green = 232, blue = 232),
        textCategory = "Silver",
        textDescription = "Berlangganan selama 1 bulan",
        textPrice = "Rp. 40.000",
        backgroundColour = Color(red = 192, green = 192, blue = 192)
    )
    Spacer(Modifier.padding(10.dp))
    CardSubscribe(
        backgroundColour = Color(red = 151, green = 106, blue = 83),
        colourBackgroundPrice = Color(red = 187, green = 141, blue = 118),
        textCategory = "Bronze",
        textDescription = "Berlangganan selama 1 minggu",
        textPrice = "Rp. 15.000",
        colorText = Color(red = 82, green = 28, blue = 0)
    )
}

@Composable
fun CardSubscribe(
    backgroundColour: Color = Color.Yellow,
    colourBackgroundPrice: Color = Color.White,
    colorText: Color = Color.Red,
    textCategory: String,
    textDescription: String,
    textPrice: String
) {
    // Card sebagai container utama
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColour)
                .padding(16.dp),

            ) {
            // Text untuk kategori
            Text(
                text = textCategory,
                color = colorText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            // Text untuk deskripsi
            Text(
                text = textDescription,
                color = colorText, // Warna teks deskripsi (oranye)
                fontSize = 14.sp
            )
            // Card untuk harga
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 8.dp), // Jarak atas
                shape = RoundedCornerShape(8.dp), // Membuat sudut melengkung pada harga

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
