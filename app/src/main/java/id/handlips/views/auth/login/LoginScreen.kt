package id.handlips.views.auth.login

import id.handlips.component.button.LongButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import id.handlips.component.button.GoogleButton
import id.handlips.component.textfield.EmailTextField
import id.handlips.component.textfield.PasswordTextField
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.poppins

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp).verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Hai! 👋",
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = "Bantu komunikasi lebih mudah dengan menerjemahkan bahasa isyarat.",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.Gray
        )
        EmailTextField(
            title = "Email",
            label = "Masukan email",
            value = email,
            onValueChange = {
                email = it
            }
        )
        PasswordTextField(
            title = "Kata sandi",
            label = "Masukan kata sandi",
            value = password,
            onValueChange = { password = it }
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { navController.navigate("forgot_password") },

                ) {
                Text(text = "Lupa password?", fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 16.sp)
            }
        }
        LongButton(
            text = "MASUK",
            onClick = {}
        )
        HorizontalDivider(Modifier.padding(top = 30.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Spacer(Modifier.padding(top = 10.dp))
        GoogleButton(
            text = "Masuk dengan Google",
            onClick = {}
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
            Text(text = "Belum punya akun?", fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 16.sp, color = Color.Gray)
            TextButton(onClick = { navController.navigate("register") }) {
                Text(text = "Daftar", fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Blue)
            }
        }
    }
}