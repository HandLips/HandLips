package id.handlips.views.translator

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import id.handlips.component.board.ResultBoard
import id.handlips.ui.theme.HandlipsTheme
import id.handlips.ui.theme.White

enum class TranslatorMode {
    HAND_SIGN,
    SPEECH,
}

enum class SpeechState {
    RECORDING,
    STOPPED,
}

class HandSignTranslator : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HandlipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        TranslatorScreen()
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun TranslatorScreen() {
    val context = LocalContext.current
    var translatorMode: TranslatorMode by remember { mutableStateOf(TranslatorMode.SPEECH) }
    var speechState: SpeechState by remember { mutableStateOf(SpeechState.STOPPED) }
    var resultText by remember { mutableStateOf("") }
    var inferenceTimeText by remember { mutableStateOf("") }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    var hasMicrophonePermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            hasCameraPermission = isGranted
        }

    val microphonePermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            hasMicrophonePermission = isGranted
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
        if (!hasMicrophonePermission) {
            microphonePermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    if (hasCameraPermission) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (translatorMode) {
                TranslatorMode.HAND_SIGN -> {
                    Box(modifier = Modifier.align(Alignment.TopCenter)) {
                        CameraPreview(
                            onClassificationResults = { results, inferenceTime ->
                                resultText = results
                                inferenceTimeText = inferenceTime
                            },
                        )
                    }
                }

                TranslatorMode.SPEECH ->
                    Text(
                        resultText,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
            }

            ResultBoard(
                modifier = Modifier.align(Alignment.BottomCenter),
                result = "result: $resultText inference: $inferenceTimeText",
                translatorMode = translatorMode,
                speechState = speechState,
                onClickTranslatorMode = {
                    Log.e("TranslatorScreen", "onClickTranslatorMode: $translatorMode")
                    translatorMode =
                        when (translatorMode) {
                            TranslatorMode.HAND_SIGN -> TranslatorMode.SPEECH
                            TranslatorMode.SPEECH -> TranslatorMode.HAND_SIGN
                        }
                },
                onSpeechButtonClick = {
                    Log.e("TranslatorScreen", "onSpeechButtonClick: $speechState")
                    speechState =
                        when (speechState) {
                            SpeechState.RECORDING -> SpeechState.STOPPED
                            SpeechState.STOPPED -> SpeechState.RECORDING
                        }
                },
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Camera permission is required.")
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Preview(showBackground = true)
@Composable
private fun HandSignTranslatorPreview() {
    HandlipsTheme {
        val translatorMode: TranslatorMode = TranslatorMode.HAND_SIGN
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton({ null }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                "",
                            )
                        }
                    },
                    colors =
                        TopAppBarDefaults.topAppBarColors(
                            containerColor = White,
                            titleContentColor = Color.Black,
                        ),
                    title = {
                        Text("Penerjemah")
                    },
                )
            },
        ) { innerPadding ->

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
            ) {
                when (translatorMode) {
                    TranslatorMode.HAND_SIGN -> Text("Camera Preview") // CameraPreview()
                    TranslatorMode.SPEECH -> Text("Speech to Text Result")
                }

//                ResultBoard(
//                    translatorMode = translatorMode,
//                    result = "Hai, Saya tuna rungu. Namaku Budi. Bolehkah saya meminta bantuan?",
//                )
            }
        }
    }
}
