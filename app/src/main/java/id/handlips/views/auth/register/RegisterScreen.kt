package id.handlips.views.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
import id.handlips.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    // State management
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
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
                    title = {}
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // Header
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
                Spacer(Modifier.height(20.dp))

                // Form Fields
                GeneralTextField(
                    title = stringResource(R.string.name),
                    label = stringResource(R.string.enter_name),
                    value = name,
                    onValueChange = { name = it }
                )
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
                Spacer(Modifier.height(20.dp))

                // Register Button
                LongButton(
                    text = stringResource(R.string.btn_register),
                    onClick = {
                        if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                            viewModel.signUp(email, password)
                        } else {
                            errorMessage = "Please fill all fields"
                            showDialogError = true
                        }
                    }
                )

                // Divider and Google Sign In
                Spacer(Modifier.height(30.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(10.dp))
                GoogleButton(
                    text = stringResource(R.string.register_with_google),
                    onClick = { /* Implement Google Sign In */ }
                )

                // Login Link
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
                    TextButton(onClick = { navController.navigate("login") }) {
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
        }

        // Loading Overlay
        if (uiState is UiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
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

    // Dialogs
    if (showDialogSuccess) {
        DialogSuccess(
            onDismissRequest = {
                navController.popBackStack()
                navController.navigate("login") {
                    popUpTo("register") { inclusive = true }
                }
            },
            textSuccess = "Login successful!"
        )
    }

    if (showDialogError) {
        DialogError(
            onDismissRequest = { showDialogError = false },
            textError = errorMessage
        )
    }
}