package id.handlips.views.guide

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
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
        Column(modifier = Modifier.padding(paddingValues)) {
            ExpendedCardComponent(title = "Menerjemahkan Bahasa Isyarat ke Text",
                compose = {
                    Column(modifier = Modifier.fillMaxWidth().background(White).padding(16.dp)){
                        Text(
                            text = "Content goes here",
                        )
                    }
                })
            ExpendedCardComponent(title = "Menerjemahkan Audio ke Text",
                compose = {
                    Column(modifier = Modifier.fillMaxWidth().background(White).padding(16.dp)){
                        Text(
                            text = "Content goes here",
                        )
                    }
                })
            ExpendedCardComponent(title = "Menggunakan shortcut",
                compose = {
                    Column(modifier = Modifier.fillMaxWidth().background(White).padding(16.dp)){
                        Text(
                            text = "Content goes here",
                        )
                    }
                })
            ExpendedCardComponent(title = "Ingin melakukan pembayaran",
                compose = {
                    Column(modifier = Modifier.fillMaxWidth().background(White).padding(16.dp)){
                        Text(
                            text = "Content goes here",
                        )
                    }
                })
        }
    }
}

@Preview
@ExperimentalMaterialApi
@Composable
fun ExpendedCardComponentPreview() {
   GuideScreen(onClickBack = {})
}
