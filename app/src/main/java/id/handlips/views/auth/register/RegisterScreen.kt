package id.handlips.views.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import id.handlips.R
import id.handlips.component.button.GoogleButton
import id.handlips.component.button.LongButton
import id.handlips.component.textfield.EmailTextField
import id.handlips.component.textfield.GeneralTextField
import id.handlips.component.textfield.PasswordTextField
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
                title = {}, // Tidak ada title
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = "Silahkan Buat Akun \uD83D\uDC4C",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Buat akun baru untuk akses penuh ke penerjemahan bahasa isyarat yang mudah dan cepat.",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(20.dp))
                GeneralTextField(
                    title = "Nama",
                    label = "Masukkan nama",
                    value = name,
                    onValueChange = { name = it }
                )
                EmailTextField(
                    title = "Email",
                    label = "Masukkan email",
                    value = email,
                    onValueChange = { email = it }
                )
                Spacer(Modifier.height(10.dp))
                PasswordTextField(
                    title = "Kata Sandi",
                    label = "Masukkan kata sandi",
                    value = password,
                    onValueChange = { password = it }
                )
                Spacer(Modifier.height(20.dp))
                LongButton(
                    text = "DAFTAR",
                    onClick = {
                        // Tambahkan logika untuk proses pendaftaran
                    }
                )
                Spacer(Modifier.height(30.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(10.dp))
                GoogleButton(
                    text = "Daftar dengan Google",
                    onClick = {
                        // Tambahkan logika untuk daftar dengan Google
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sudah punya akun?",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    TextButton(onClick = { navController.navigate("login") }) {
                        Text(
                            text = "Masuk",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Blue
                        )
                    }
                }
            }
        }
    )
}
