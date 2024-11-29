package id.handlips.views.auth.register

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.qualifiers.ApplicationContext
import id.handlips.R
import id.handlips.component.button.GoogleButton
import id.handlips.component.button.LongButton
import id.handlips.component.dialog.DialogError
import id.handlips.component.dialog.DialogSuccess
import id.handlips.component.loading.LoadingAnimation
import id.handlips.component.textfield.EmailTextField
import id.handlips.component.textfield.GeneralTextField
import id.handlips.component.textfield.PasswordTextField
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.poppins
import id.handlips.utils.Resource
import id.handlips.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onClickBack: () -> Unit,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit,
    onClickGoogle: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    // State management
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }

    // Observing ViewModel states
    val uiState by viewModel.uiState.collectAsState()

    // Handle Firebase signup state
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

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
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
                    title = {}
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                RegisterHeader()

                Spacer(Modifier.height(20.dp))

                RegisterForm(
                    name = name,
                    email = email,
                    password = password,
                    onNameChange = { name = it },
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it }
                )

                Spacer(Modifier.height(20.dp))

                LongButton(
                    text = stringResource(R.string.btn_register),
                    onClick = {
                        if (validateInputs(name, email, password)) {
                            viewModel.signUp(email, password, name)
                        } else {
                            errorMessage = context.getString(R.string.please_fill_all_fields)
                            showDialogError = true
                        }
                    }
                )

                RegisterFooter(
                    onGoogleClick = onClickGoogle,
                    onLoginClick = onClickLogin
                )
            }
        }

        // Loading Overlay
        if (uiState is UiState.Loading) {
            LoadingOverlay()
        }
    }

    // Dialogs
    HandleDialogs(
        showSuccessDialog = showDialogSuccess,
        showErrorDialog = showDialogError,
        errorMessage = errorMessage,
        onSuccessDismiss = onClickRegister,
        onErrorDismiss = { showDialogError = false }
    )
}


@Composable
private fun RegisterHeader() {
    Text(
        text = stringResource(R.string.create_an_account),
        fontFamily = poppins,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    Text(
        text = stringResource(R.string.description_register),
        fontFamily = poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.Gray
    )
}

@Composable
private fun RegisterForm(
    name: String,
    email: String,
    password: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    GeneralTextField(
        title = stringResource(R.string.name),
        label = stringResource(R.string.enter_name),
        value = name,
        onValueChange = onNameChange
    )
    EmailTextField(
        title = stringResource(R.string.email),
        label = stringResource(R.string.enter_email),
        value = email,
        onValueChange = onEmailChange
    )
    PasswordTextField(
        title = stringResource(R.string.password),
        label = stringResource(R.string.enter_password),
        value = password,
        onValueChange = onPasswordChange
    )
}

@Composable
private fun RegisterFooter(
    onGoogleClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    Spacer(Modifier.height(30.dp))
    HorizontalDivider(
        thickness = 1.dp,
        color = Color.Gray
    )
    Spacer(Modifier.height(10.dp))
    GoogleButton(
        text = stringResource(R.string.register_with_google),
        onClick = onGoogleClick
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.do_you_have_an_account),
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.Gray
        )
        TextButton(onClick = onLoginClick) {
            Text(
                text = stringResource(R.string.login),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Blue
            )
        }
    }
}

@Composable
private fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        LoadingAnimation()
    }
}

@Composable
private fun HandleDialogs(
    showSuccessDialog: Boolean,
    showErrorDialog: Boolean,
    errorMessage: String,
    onSuccessDismiss: () -> Unit,
    onErrorDismiss: () -> Unit
) {
    if (showSuccessDialog) {
        DialogSuccess(
            onDismissRequest = onSuccessDismiss,
            textSuccess = stringResource(R.string.register_successful)
        )
    }

    if (showErrorDialog) {
        DialogError(
            onDismissRequest = onErrorDismiss,
            textError = errorMessage
        )
    }
}

private fun validateInputs(name: String, email: String, password: String): Boolean {
    return name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
}