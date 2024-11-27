package id.handlips.views.auth.login

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.component.button.GoogleButton
import id.handlips.component.dialog.DialogError
import id.handlips.component.dialog.DialogSuccess
import id.handlips.component.loading.LoadingAnimation
import id.handlips.component.textfield.EmailTextField
import id.handlips.component.textfield.PasswordTextField
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.poppins
import id.handlips.utils.UiState

@Composable
fun LoginScreen(
    navController: NavHostController,
    onClickLogin: () -> Unit,
    onClickGoogle: () -> Unit,
    onClickRegister: () -> Unit,
    onClickForgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    // State management
    val uiState by viewModel.uiState.collectAsState()
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.hai),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(R.string.description_login),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }

        // Input Fields
        EmailTextField(
            title = stringResource(R.string.email),
            label = stringResource(R.string.enter_email),
            value = email,
            onValueChange = { email = it }
        )

        PasswordTextField(
            title = stringResource(R.string.password),
            label = stringResource(R.string.enter_password),
            value = password,
            onValueChange = { password = it }
        )

        // Forgot Password
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { onClickForgotPassword() }) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }

        // Login Button
        LongButton(
            text = stringResource(R.string.btn_login),
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.signIn(email, password)
                } else {
                    errorMessage = context.getString(R.string.please_fill_all_fields)
                    showDialogError = true
                }
            }
        )

        // Divider
        HorizontalDivider(
            modifier = Modifier.padding(top = 30.dp),
            thickness = 1.dp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.padding(top = 10.dp))

        // Google Sign In
        GoogleButton(
            text = stringResource(R.string.btn_google_login),
            onClick = { /* Implement Google Sign In */ }
        )

        // Register Link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.don_t_have_an_account),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Gray
            )
            TextButton(onClick = { onClickRegister() }) {
                Text(
                    text = stringResource(R.string.register),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Blue
                )
            }
        }
    }

    // Handle UI States
    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                showDialogSuccess = true
            }

            is UiState.Error -> {
                errorMessage = (uiState as UiState.Error).message
                showDialogError = true
            }

            else -> {}
        }
    }

    // Dialogs and Loading
    if (showDialogSuccess) {
        DialogSuccess(
            onDismissRequest = {
                onClickLogin()
            },
            textSuccess = stringResource(R.string.login_successful)
        )
    }

    if (showDialogError) {
        DialogError(
            onDismissRequest = {
                showDialogError = false
            },
            textError = errorMessage
        )
    }

    if (uiState is UiState.Loading) {
        LoadingAnimation()
    }
}