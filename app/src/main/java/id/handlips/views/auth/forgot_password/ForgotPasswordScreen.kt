package id.handlips.views.auth.forgot_password

import id.handlips.component.button.LongButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import id.handlips.R
import id.handlips.component.textfield.EmailTextField
import id.handlips.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
) {
    var email by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back Icon"
                        )
                    }
                },
                title = {},
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(paddingValues).verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_forgot_password),
                        contentDescription = "Forgot Password Icon",
                        modifier = Modifier.size(180.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 30.dp))
                Text(
                    text = "Lupa kata sandi?",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Masukkan email Anda, dan kami akan mengirimkan tautan untuk reset kata sandi.",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                EmailTextField(
                    title = stringResource(R.string.email),
                    label = stringResource(R.string.label_email),
                    value = email,
                    onValueChange = {
                        email = it
                    }
                )
                Spacer(Modifier.padding(top = 10.dp))
                LongButton(
                    text = stringResource(R.string.send),
                    onClick = { /* Tambahkan logika pengiriman email */ }
                )
            }
        }
    )
}
