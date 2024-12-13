package id.handlips.views.auth.register


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
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
import id.handlips.utils.Constraint.clientId
import id.handlips.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onClickBack: () -> Unit,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit,
    onClickGooggle: () -> Unit,
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
    val googleSignInState = viewModel.googleState.value
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

    // Google Sign-In launcher
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val result = account.getResult(ApiException::class.java)
                val credentials = GoogleAuthProvider.getCredential(result.idToken, null)
                viewModel.googleSignIn(credentials)
            } catch (it: ApiException) {
                print(it)
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

                // Form
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
                            viewModel.signUp(email, password, name)
                        } else {
                            errorMessage = context.getString(R.string.please_fill_all_fields)
                            showDialogError = true
                        }
                    }
                )

                Spacer(Modifier.height(30.dp))

                // Divider
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)

                Spacer(Modifier.height(10.dp))

                // Google Sign-In Button
                GoogleButton(
                    text = stringResource(R.string.register_with_google),
                    onClick = {
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .requestIdToken(clientId)
                            .build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    }
                )

                Spacer(Modifier.height(20.dp))

                // Login Redirect
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
                    TextButton(onClick = onClickLogin) {
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
        if (uiState is UiState.Loading || googleSignInState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        }

        // Dialogs
        if (showDialogSuccess ) {
            DialogSuccess(
                onDismissRequest = onClickRegister,
                textSuccess = stringResource(R.string.register_successful)
            )
        }
        if (googleSignInState.success != null){
            DialogSuccess(
                onDismissRequest = onClickGooggle,
                textSuccess = stringResource(R.string.register_successful)
            )
        }
        if (showDialogError) {
            DialogError(
                onDismissRequest = { showDialogError = false },
                textError = errorMessage
            )
        }
    }
}
