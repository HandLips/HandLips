package id.handlips.views.forgot_password

import LongButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import id.handlips.R
import id.handlips.component.textfield.EmailTextField
import id.handlips.component.textfield.PasswordTextField
import id.handlips.ui.theme.poppins

@Composable
fun ForgotPasswordScreen() {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
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
            onClick = {}
        )
    }
}