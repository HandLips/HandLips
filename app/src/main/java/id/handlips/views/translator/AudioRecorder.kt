package id.handlips.views.translator

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class AudioRecorder {
    private var isRecording = false
    private var audioRecord: AudioRecord? = null

    // Audio configuration
    private val sampleRate = 16000 // 16 kHz sample rate
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioEncoding = AudioFormat.ENCODING_PCM_16BIT

    fun startRecording(
        context: Context,
        outputFile: File,
    ) {
        val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioEncoding)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        audioRecord =
            AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioEncoding,
                bufferSize,
            )

        val dataBuffer = ByteArray(bufferSize)
        isRecording = true

        audioRecord?.startRecording()

        // Write PCM data to the file in a coroutine
        CoroutineScope(Dispatchers.IO).launch {
            writePCMToFile(outputFile, dataBuffer)
        }
    }

    fun stopRecording() {
        isRecording = false
        audioRecord?.apply {
            stop()
            release()
        }
        audioRecord = null
    }

    private suspend fun writePCMToFile(
        outputFile: File,
        dataBuffer: ByteArray,
    ) {
        withContext(Dispatchers.IO) {
            FileOutputStream(outputFile).use { outputStream ->
                while (isRecording) {
                    val read = audioRecord?.read(dataBuffer, 0, dataBuffer.size) ?: 0
                    if (read > 0) {
                        outputStream.write(dataBuffer, 0, read)
                    }
                }
            }
        }
    }
}
