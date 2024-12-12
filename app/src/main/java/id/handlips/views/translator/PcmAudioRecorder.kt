package id.handlips.views.translator

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream

class PcmAudioRecorder {
    private var recorder: AudioRecord? = null

    fun startRecording(
        context: Context,
        file: File,
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val audioRecord =
            AudioRecord(
                AudioManager.STREAM_MUSIC,
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                AudioRecord.getMinBufferSize(
                    44100,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                ),
            )

        if (audioRecord.state == AudioRecord.STATE_INITIALIZED) {
            audioRecord.startRecording()

            // Create a thread to write audio data to a file
            Thread {
                val outputStream = FileOutputStream(file)
                val buffer = ByteArray(1024)
                while (audioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    val read = audioRecord.read(buffer, 0, buffer.size)
                    if (read > 0) {
                        outputStream.write(buffer, 0, read)
                    }
                }
                outputStream.close()
                audioRecord.release()
            }.start()
        }
    }

    fun stopRecording() {
        recorder?.stop()
        recorder?.release()
        recorder = null
    }
}
