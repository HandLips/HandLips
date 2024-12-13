package id.handlips.views.customer_service

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.R
import id.handlips.component.button.LongButton
import id.handlips.component.card.ExpendedCardComponent
import id.handlips.component.dialog.DialogSuccess
import id.handlips.component.loading.LoadingAnimation
import id.handlips.component.textfield.DescriptionTextField
import id.handlips.ui.theme.White
import id.handlips.ui.theme.poppins
import id.handlips.utils.UiState
import id.handlips.views.feedback.DialogFeedbackEmot
import id.handlips.views.feedback.DialogFeedbackTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerServiceScreen(modifier: Modifier = Modifier, onClickBack: () -> Unit, viewModel: CustomerServiceViewModel = hiltViewModel()) {
    var feedback by remember { mutableStateOf("") }
    var report by remember { mutableStateOf("") }
    var succesFor by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    var errorMessage by remember { mutableStateOf("") }
    var showDialogSuccess by remember { mutableStateOf(false) }
    var showDialogError by remember { mutableStateOf(false) }
    var showDialogFeedbackEmot by remember { mutableStateOf(false) }
    val uiStateFeedback by viewModel.uiState.collectAsState()
    val uiStateReport by viewModel.uiStateReport.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiStateFeedback, uiStateReport) {
        when (uiStateFeedback) {
            is UiState.Success -> {
                showDialogSuccess = true
                succesFor = context.getString(R.string.feedback)
            }
            is UiState.Error -> {
                errorMessage = (uiStateFeedback as UiState.Error).message
                showDialogError = true
            }
            else -> {}
        }

        when (uiStateReport) {
            is UiState.Success -> {
                showDialogSuccess = true
                succesFor = context.getString(R.string.report)
            }
            is UiState.Error -> {
                errorMessage = (uiStateFeedback as UiState.Error).message
                showDialogError = true
            }
            else -> {}
        }

    }
    // Loading Overlay
    if (uiStateFeedback is UiState.Loading) {
        LoadingOverlay()
    }
    if (uiStateReport is UiState.Loading) {
        LoadingOverlay()
    }
    if (showDialogSuccess){
        DialogSuccess(
            onDismissRequest = {
                showDialogSuccess = false
                feedback = ""
                report = ""
            },
            textSuccess = stringResource(R.string.succes_create, succesFor)
        )
    }
    if (showDialogFeedbackEmot) {
        DialogFeedbackEmot(
            onDismissRequest = {
                showDialogFeedbackEmot = false
            },
            onConfirm = { emotion ->
                viewModel.sendFeedback(emotion, feedback) // Kirim angka dan feedback ke ViewModel
                showDialogFeedbackEmot = false
            }
        )
    }
    Scaffold(
        modifier = modifier,
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
                title = {
                    Text(
                        text = stringResource(R.string.customer_service),
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)) {
            ExpendedCardComponent(title = stringResource(R.string.feedback),
                compose = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(White)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.masukan_feedback),
                                fontFamily = poppins,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = stringResource(R.string.how_do_you_feel_about_using_this_app),
                                fontFamily = poppins,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 8.dp),
                            )
                            DescriptionTextField(
                                label = stringResource(R.string.saran),
                                onValueChange = { newValue ->
                                    feedback = newValue
                                },
                                value = feedback
                            )
                            LongButton(
                                text = stringResource(R.string.send),
                                onClick = {
                                    showDialogFeedbackEmot = true
                                },
                            )
                        }
                    }
                })
            ExpendedCardComponent(title = stringResource(R.string.report),
                compose = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(White)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.report),
                                fontFamily = poppins,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = stringResource(R.string.are_there_any_issues_we_can_help_you_with),
                                fontFamily = poppins,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 8.dp),
                            )
                            DescriptionTextField(
                                label = stringResource(R.string.masukan_laporan),
                                onValueChange = { newValue ->
                                    report = newValue
                                },
                                value = report
                            )
                            LongButton(
                                text = stringResource(R.string.send),
                                onClick = {
                                    viewModel.sendReport(reason = report)
                                },
                            )
                        }
                    }
                })
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

@Preview
@ExperimentalMaterialApi
@Composable
fun ExpendedCardComponentPreview() {
    CustomerServiceScreen(onClickBack = {})
}