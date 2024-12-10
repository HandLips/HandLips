package id.handlips.views.translator

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import id.handlips.component.button.CircleButton
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.HandlipsTheme
import id.handlips.ui.theme.Red
import id.handlips.ui.theme.White

enum class TranslatorMode {
    HAND_SIGN,
    SPEECH,
}

class HandSignTranslator : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HandlipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        CameraScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun ResultBoard(
    result: String,
    translatorMode: TranslatorMode = TranslatorMode.HAND_SIGN,
) {
    Card(
        shape =
            RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp,
            ),
        border = BorderStroke(width = 1.dp, color = Blue),
    ) {
        Box(
            modifier =
                Modifier
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        White,
                                        White,
                                        White,
                                        Blue,
                                    ),
                            ),
                    ).padding(16.dp), // Padding inside the card
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (translatorMode) {
                    TranslatorMode.SPEECH -> {
                        CircleButton(
                            icon = Icons.Filled.Star,
                            containerColor = Red,
                            contentColor = White,
                            onClick = {},
                            contentDescription = "",
                            size = 120,
                        )
                    }

                    TranslatorMode.HAND_SIGN -> Text(result, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    CircleButton(
                        icon = Icons.Filled.Refresh,
                        onClick = { null },
                        contentDescription = "Riwayat Percakapan",
                    )
                    CircleButton(
                        icon = Icons.Filled.Call,
                        onClick = { null },
                        contentDescription = "Terjemah Suara ke Teks",
                    )
                }
            }
        }
    }
}



@Suppress("ktlint:standard:function-naming")
@Composable
fun CameraScreen() {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            hasCameraPermission = isGranted
        }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        CameraPreview()
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text("Camera permission is required.")
        }
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun CameraPreview(modifier: Modifier = Modifier.fillMaxSize()) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            PreviewView(context)
                .apply {
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
        },
    )
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

                ResultBoard(
                    translatorMode = translatorMode,
                    result = "Hai, Saya tuna rungu. Namaku Budi. Bolehkah saya meminta bantuan?",
                )
            }
        }
    }
}
