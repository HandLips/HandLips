package id.handlips.views.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.handlips.R
import id.handlips.component.card.ExpendedCardComponent
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back_icon)
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.guide),
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)) {
            ExpendedCardComponent(title = stringResource(R.string.menerjemahkan_bahasa_isyarat_ke_text),
                compose = {
                    GuideContent(
                        description = stringResource(R.string.description_gesture),
                        steps = {
                            Spacer(Modifier.padding(10.dp))
                            TextBold(number = stringResource(R.string._1))
                            TextNormal(text = stringResource(R.string.pastikan_kamu_sudah_masuk))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._2))
                            TextNormal(text = stringResource(R.string.pilih_icon_chat_pada_bottom_navigasi))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._3))
                            TextNormal(text = stringResource(R.string.tambah_judul_dan_tekan_tombol_buat_baru))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._4))
                            TextNormal(text = stringResource(R.string.sekarang_kamu_bisa_memulai_komunikasi_dengan_menerjemahkan_gesture_tubuh_ke_kalimat))
                            Spacer(Modifier.padding(10.dp))
                        }
                    )
                }
            )
            ExpendedCardComponent(title = stringResource(R.string.menerjemahkan_audio_ke_text),
                compose = {
                    GuideContent(
                        description = stringResource(R.string.fitur_penerjemah_audio_ke_teks_dirancang_untuk_mempermudah_komunikasi_antara_teman_tuli_dan_bisu_dengan_teman_yang_tidak_memiliki_hambatan_pendengaran_atau_berbicara_sehingga_mereka_dapat_saling_memahami_dengan_lebih_baik),
                        steps = {
                            Spacer(Modifier.padding(10.dp))
                            TextBold(number = stringResource(R.string._1))
                            TextNormal(text = stringResource(R.string.pastikan_kamu_sudah_masuk))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._2))
                            TextNormal(text = stringResource(R.string.pilih_icon_chat_pada_bottom_navigasi))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._3))
                            TextNormal(text = stringResource(R.string.tambah_judul_dan_tekan_tombol_buat_baru))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._4))
                            TextNormal(text = stringResource(R.string.sekarang_kamu_bisa_memulai_komunikasi_dengan_klik_icon_audio_dan_rekam_suara_ke_lawan_bicara_untuk_mendapatkan_text_suara))
                            Spacer(Modifier.padding(10.dp))
                        }
                    )
                })
            ExpendedCardComponent(title = stringResource(R.string.menggunakan_shortcut),
                compose = {
                    GuideContent(
                        description = stringResource(R.string.description_shortcut),
                        steps = {
                            Spacer(Modifier.padding(10.dp))
                            TextBold(number = stringResource(R.string._1))
                            TextNormal(text = stringResource(R.string.pastikan_kamu_sudah_masuk))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._2))
                            TextNormal(text = stringResource(R.string.pilih_icon_petir_pada_bottom_navigasi))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._3))
                            TextNormal(text = stringResource(R.string.jika_belum_ada_shortcut_maka_tambahkan_terlebih_dahulu_shortcut_dengan_tekan_tombol_tambah_pada_pojok_kanan_bawah))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._4))
                            TextNormal(text = stringResource(R.string.masukan_judul_pesan_suara_dan_jika_sudah_selesai_maka_tekan_tombol_tambah))
                            Spacer(Modifier.padding(5.dp))
                            TextBold(number = stringResource(R.string._5))
                            TextNormal(text = stringResource(R.string.setelah_ditambah_maka_akan_muncul_pada_halaman_shortcut_dan_bisa_digunakan_dengan_tekan_tombol_pada_shortcut_tersebut_ketika_ingin_digunakan))
                            Spacer(Modifier.padding(10.dp))
                        }
                    )
                })
        }
    }
}

@Composable
fun GuideContent(
    description: String,
    steps: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(16.dp)
    ) {
        TextNormal(text = description)
        steps()
    }
}

@Composable
fun TextBold(modifier: Modifier = Modifier, number: String) {
    Text(
        text = "Langkah $number :",
        modifier = modifier,
        fontSize = 12.sp,
        fontFamily = poppins,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextNormal(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 12.sp,
        fontFamily = poppins,
        fontWeight = FontWeight.Normal
    )
}

@Preview
@ExperimentalMaterialApi
@Composable
fun ExpendedCardComponentPreview() {
    GuideScreen(onClickBack = {})
}
