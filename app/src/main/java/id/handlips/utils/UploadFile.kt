package id.handlips.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val MAXIMAL_SIZE = 1000000 //1 MB
private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())


fun uriToFile(imageUri: Uri, context: Context): File {
    val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
    tempFile.createNewFile()

    try {
        // Cek ukuran file sebelum kompresi
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val fileSize = inputStream?.available() ?: 0
        Log.d("FileUpload", "Original File Size: $fileSize bytes")

        // Batasi ukuran file maksimal 5MB
        if (fileSize > 5 * 1024 * 1024) {
            throw IllegalArgumentException("Ukuran file terlalu besar. Maksimal 5MB")
        }

        // Kompresi gambar
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Resize gambar jika terlalu besar
        val resizedBitmap = if (bitmap.width > 1024 || bitmap.height > 1024) {
            val scale = minOf(1024f / bitmap.width, 1024f / bitmap.height)
            Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * scale).toInt(),
                (bitmap.height * scale).toInt(),
                true
            )
        } else {
            bitmap
        }

        // Simpan bitmap yang sudah diresize ke file
        val outputStream = FileOutputStream(tempFile)
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
        outputStream.close()

        // Cek ukuran file setelah kompresi
        val compressedFileSize = tempFile.length()
        Log.d("FileUpload", "Compressed File Size: $compressedFileSize bytes")

        Log.d("FileUpload", "File created at: ${tempFile.absolutePath}")
        return tempFile

    } catch (e: Exception) {
        Log.e("FileUpload", "Error converting URI to File", e)
        throw RuntimeException("Gagal mengkonversi gambar: ${e.message}")
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun File.reduceFileImage(): File {
    val bitmap = BitmapFactory.decodeFile(this.path).getRotatedBitmap(this)
    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(this))
    return this
}

@RequiresApi(Build.VERSION_CODES.Q)
fun Bitmap.getRotatedBitmap(file: File): Bitmap {
    val exif = ExifInterface(file)
    val orientation = exif.getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(this, 90f)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(this, 180f)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(this, 270f)
        else -> this
    }
}

fun rotateImage(source: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
}