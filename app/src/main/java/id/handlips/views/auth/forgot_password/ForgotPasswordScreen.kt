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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import id.handlips.R
import id.handlips.component.dialog.DialogError
import id.handlips.component.dialog.DialogSuccess
import id.handlips.component.loading.LoadingAnimation
import id.handlips.component.textfield.EmailTextField
import id.handlips.ui.theme.poppins
import id.handlips.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    onCLickBack: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    var errorMessage by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(uiState) {
        when(uiState) {
            is UiState.Loading -> loading = true
            is UiState.Success -> {
                showDialogSuccess = true
                loading = false
            }
            is UiState.Error -> {
                errorMessage = (uiState as UiState.Error).message
                showDialogError = true
                loading = false
            }
            else -> {}
        }
    }

    if (showDialogSuccess) {
        DialogSuccess(
            onDismissRequest = {
                showDialogSuccess = false
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

    if (loading) {
        LoadingAnimation()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = stringResource(R.string.back_icon)
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
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_forgot_password),
                        contentDescription = stringResource(R.string.forgot_password_icon),
                        modifier = Modifier.size(180.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 30.dp))
                Text(
                    text = stringResource(R.string.forgot_password),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = stringResource(R.string.enter_your_email_and_we_ll_send_you_a_link_to_reset_your_password),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.padding(top = 10.dp))
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
                    onClick = {
                        if(email.isNotBlank()){
                            viewModel.forgotPassword(email)
                        } else {
                            errorMessage = context.getString(R.string.email_required)
                            showDialogError = true

                        }
                    }
                )
            }
        }
    )
}
