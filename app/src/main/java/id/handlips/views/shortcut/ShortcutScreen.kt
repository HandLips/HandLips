package id.handlips.views.shortcut

import android.media.MediaPlayer
import android.net.Uri
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.R
import id.handlips.component.dialog.DialogError
import id.handlips.component.dialog.DialogShortcut
import id.handlips.component.loading.LoadingAnimation
import id.handlips.data.model.DataItem
import id.handlips.ui.theme.Blue
import id.handlips.ui.theme.White
import id.handlips.utils.Resource

@Composable
fun ShortcutScreen(modifier: Modifier = Modifier, viewModel: ShortcutViewModel = hiltViewModel()) {
    var loading by remember { mutableStateOf(false) }
    var dialogError by remember { mutableStateOf(false) }
    var textError by remember { mutableStateOf("") }
    var listSound by remember { mutableStateOf<List<DataItem>>(emptyList()) }
    var dialogAddSound by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getSound().observeForever { resource ->
            when (resource) {
                is Resource.Loading -> {
                    loading = true
                }
                is Resource.Success -> {
                    loading = false
                    listSound = resource.data.data
                }
                is Resource.Error -> {
                    loading = false
                    dialogError = true
                    textError = resource.message
                }
            }
        }
    }
    if (dialogError) {
        DialogError(
            onDismissRequest = { dialogError = false },
            textError = textError
        )
    }
    if (dialogAddSound) {
        DialogShortcut(
            onDismissRequest = {
                dialogAddSound = false
            },
            onConfirm = {
                /* Todo Add Sound */
            }
        )
    }
    Scaffold (modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    dialogAddSound = true
                },
                containerColor = Blue,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        },
        ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Text(
                text = "Komunikasi Cepat",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 25.dp)
            )
            SoundGrid(
                sounds = listSound,
                onError = {
                    dialogError = true
                    textError = it
                }
            )
        }
    }
}

@Composable
private fun SoundCard(
    modifier: Modifier = Modifier,
    sound: DataItem,
    onError: (String) -> Unit = {}
) {
    val TAG = "SoundCard"
    val mediaPlayer = remember { MediaPlayer() }
    var isPlaying by remember { mutableStateOf(false) }
    var isPreparing by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Disposing MediaPlayer for sound: ${sound.text}")
            mediaPlayer.release()
        }
    }

    fun playAudio() {
        if (isPreparing) {
            Log.d(TAG, "Still preparing previous audio, please wait")
            return
        }

        try {
            if (isPlaying) {
                Log.d(TAG, "Stopping audio: ${sound.text}")
                mediaPlayer.stop()
                mediaPlayer.reset()
                isPlaying = false
            } else {
                Log.d(TAG, "Starting playback for: ${sound.text}")
                Log.d(TAG, "Audio URL: ${sound.audioUrl}")

                isPreparing = true
                mediaPlayer.reset()

                val preparationTimeout = object : CountDownTimer(10000, 10000) {
                    override fun onTick(millisUntilFinished: Long) {}
                    override fun onFinish() {
                        if (isPreparing) {
                            Log.e(TAG, "Media preparation timeout for ${sound.text}")
                            isPreparing = false
                            mediaPlayer.reset()
                            onError("Timeout preparing audio")
                        }
                    }
                }

                preparationTimeout.start()

                try {
                    // Validate URL format
                    val uri = Uri.parse(sound.audioUrl)
                    if (uri.scheme.isNullOrEmpty() || uri.host.isNullOrEmpty()) {
                        throw IllegalArgumentException("Invalid URL format")
                    }

                    mediaPlayer.setDataSource(sound.audioUrl)
                    mediaPlayer.prepareAsync()

                    mediaPlayer.setOnPreparedListener {
                        Log.d(TAG, "MediaPlayer prepared successfully for: ${sound.text}")
                        preparationTimeout.cancel()
                        isPreparing = false
                        it.start()
                        isPlaying = true
                    }

                    mediaPlayer.setOnCompletionListener {
                        Log.d(TAG, "Playback completed for: ${sound.text}")
                        isPlaying = false
                        mediaPlayer.reset()
                    }

                    mediaPlayer.setOnErrorListener { _, what, extra ->
                        preparationTimeout.cancel()
                        isPreparing = false
                        val errorMessage = when(what) {
                            MediaPlayer.MEDIA_ERROR_UNKNOWN -> "Unknown error occurred"
                            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> "Server died"
                            else -> "Error code: $what"
                        }
                        val extraMessage = when(extra) {
                            MediaPlayer.MEDIA_ERROR_IO -> "IO error"
                            MediaPlayer.MEDIA_ERROR_MALFORMED -> "Malformed media"
                            MediaPlayer.MEDIA_ERROR_UNSUPPORTED -> "Unsupported format"
                            MediaPlayer.MEDIA_ERROR_TIMED_OUT -> "Timed out"
                            else -> "Extra code: $extra"
                        }

                        Log.e(TAG, "MediaPlayer error for ${sound.text} - $errorMessage ($extraMessage)")
                        isPlaying = false
                        mediaPlayer.reset()
                        onError("Error playing audio: $errorMessage - $extraMessage")
                        true
                    }
                } catch (e: IllegalArgumentException) {
                    preparationTimeout.cancel()
                    isPreparing = false
                    Log.e(TAG, "Invalid URL for ${sound.text}: ${sound.audioUrl}", e)
                    onError("Invalid audio URL")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception playing audio for ${sound.text}", e)
            isPreparing = false
            onError("Error playing audio: ${e.message}")
            isPlaying = false
            mediaPlayer.reset()
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .clickable(enabled = !isPreparing) {
                Log.d(TAG, "Card clicked for sound: ${sound.text}")
                playAudio()
            },
        colors = CardDefaults.cardColors(containerColor = Blue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = sound.text,
                color = White,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            AudioControls(isPlaying = isPlaying)

            // Optional: Show loading indicator while preparing
            if (isPreparing) {
                LoadingAnimation()
            }
        }
    }
}

@Composable
private fun AudioControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(
                R.drawable.ic_wave
            ),
            contentDescription = "Wave",
            tint = White,
            modifier = Modifier.size(100.dp)
        )
        Icon(
            painter = painterResource(
                id = if (isPlaying) {
                    R.drawable.ic_pause // Make sure to add this icon
                } else {
                    R.drawable.ic_music
                }
            ),
            contentDescription = if (isPlaying) "Stop" else "Play",
            tint = White,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
private fun SoundGrid(
    modifier: Modifier = Modifier,
    sounds: List<DataItem>,
    onError: (String) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(sounds) { _, sound ->
            SoundCard(
                sound = sound,
                onError = onError
            )
        }
    }
}