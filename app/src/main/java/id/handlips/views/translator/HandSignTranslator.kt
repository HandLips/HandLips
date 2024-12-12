@file:Suppress("DEPRECATION")

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
import androidx.camera.core.CameraSelector
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.component.board.ResultBoard
import id.handlips.ui.theme.HandlipsTheme
import id.handlips.ui.theme.White
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

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
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                    ) {
                        TranslatorScreen()
                    }
                }
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun TranslatorScreen(speechToTextViewModel: SpeechToTextViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var translatorMode: TranslatorMode by remember { mutableStateOf(TranslatorMode.HAND_SIGN) }
    var speechState: SpeechState by remember { mutableStateOf(SpeechState.STOPPED) }
    var resultText by remember { mutableStateOf("") }
    var inferenceTimeText by remember { mutableStateOf("") }
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }

    val speechText by speechToTextViewModel.resultText

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

    val pcmFile = File(context.cacheDir, "recording_cache.pcm")
    val wavFile = File(context.cacheDir, "recording_cache.wav")

    val recorder = remember { AudioRecorder() }

    val player by lazy {
        AndroidAudioPlayer(context)
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
                            lensFacing = lensFacing,
                        )
                    }
                }

                TranslatorMode.SPEECH ->
                    Text(
                        speechText,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
//                    Column {
//                        Button(onClick = { wavFile?.let { player.playFile(it) } }) { Text("Play") }
//                        Button(onClick = { player.stop() }) { Text("Stop") }
//                    }
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
//                    speechText = ""
                },
                onSpeechButtonClick = {
                    Log.e("TranslatorScreen", "onSpeechButtonClick: $speechState")
                    resultText = ""

                    when (speechState) {
                        SpeechState.RECORDING -> {
                            speechState = SpeechState.STOPPED
//                            speechTextResult = "Stopped"

                            recorder.stopRecording()
                            convertPcmToWav(pcmFile = pcmFile, wavFile = wavFile)
                            speechToTextViewModel.speechToText(title = "test", audioFile = wavFile)
                            Log.d("Recording", pcmFile.toString())
                            Log.d("Recording", wavFile.toString())
                            Log.d("Context Cache", context.cacheDir.toString())
                        }

                        SpeechState.STOPPED -> {
                            speechState = SpeechState.RECORDING
//                            speechTextResult = "Recording"
//                            speechText = ""

                            recorder.startRecording(context = context, outputFile = pcmFile)

                            Log.d("Recording", pcmFile.toString())
                            Log.d("Context Cache", context.cacheDir.toString())
                        }
                    }
                },
                onClickCameraSwitch = {
                    Log.d("TranslatorScreen", "onClickCameraSwitch: $lensFacing")
                    lensFacing =
                        if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                            CameraSelector.LENS_FACING_FRONT
                        } else {
                            CameraSelector.LENS_FACING_BACK
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

fun convertPcmToWav(
    pcmFile: File,
    wavFile: File,
) {
    val pcmData = pcmFile.readBytes()
    val wavHeader = createWavHeader(pcmData.size)

    wavFile.outputStream().use { outputStream ->
        outputStream.write(wavHeader)
        outputStream.write(pcmData)
    }
}

fun createWavHeader(pcmDataLength: Int): ByteArray {
    val totalDataLength = pcmDataLength + 36
    val sampleRate = 16000
    val channels = 1
    val byteRate = sampleRate * channels * 2

    return ByteBuffer
        .allocate(44)
        .order(ByteOrder.LITTLE_ENDIAN)
        .apply {
            // RIFF header
            put("RIFF".toByteArray())
            putInt(totalDataLength)
            put("WAVE".toByteArray())

            // fmt sub-chunk
            put("fmt ".toByteArray())
            putInt(16) // Sub-chunk size (16 for PCM)
            putShort(1.toShort()) // Audio format (1 for PCM)
            putShort(channels.toShort())
            putInt(sampleRate)
            putInt(byteRate)
            putShort((channels * 2).toShort()) // Block align
            putShort(16.toShort()) // Bits per sample

            // data sub-chunk
            put("data".toByteArray())
            putInt(pcmDataLength)
        }.array()
}
