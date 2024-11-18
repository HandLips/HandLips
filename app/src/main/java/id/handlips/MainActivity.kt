package id.handlips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.handlips.ui.theme.HandlipsTheme
import id.handlips.views.forgot_password.ForgotPasswordScreen
import id.handlips.views.register.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandlipsTheme {
                ForgotPasswordScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HandlipsTheme {
        ForgotPasswordScreen()
    }
}

