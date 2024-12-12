package id.handlips.views.translator

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import android.view.Surface
import androidx.camera.core.ImageProxy
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import kotlin.toString
import kotlin.use

class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "model_coba_metadata.tflite",
    val context: Context,
    val classifierListener: ClassifierListener?,
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder =
            ImageClassifier.ImageClassifierOptions
                .builder()
                .setScoreThreshold(threshold)
                .setMaxResults(maxResults)
        val baseOptionsBuilder =
            BaseOptions
                .builder()
                .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier =
                ImageClassifier.createFromFileAndOptions(
                    context,
                    modelName,
                    optionsBuilder.build(),
                )
        } catch (e: IllegalStateException) {
            classifierListener?.onError("Image classifier failed to initialize. See error logs for details")
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyImage(image: ImageProxy) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }
        val imageProcessor =
            ImageProcessor
                .Builder()
                .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                .add(CastOp(DataType.FLOAT32))
                .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(toBitmap(image)))

        val imageProcessingOptions =
            ImageProcessingOptions
                .builder()
                .setOrientation(getOrientationFromRotation(image.imageInfo.rotationDegrees))
                .build()

        var inferenceTime = SystemClock.uptimeMillis()
        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)

        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        classifierListener?.onResults(
            results,
            inferenceTime,
        )
    }

//    private fun classifyImage(image: Bitmap) {
//        try {
//            val model = ModelCoba.newInstance(applicationContext)
//
//            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
//            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//            byteBuffer.order(ByteOrder.nativeOrder())
//
//            val intValues = IntArray(imageSize * imageSize)
//            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
//            var pixel = 0
//            for (i in 0 until imageSize) {
//                for (j in 0 until imageSize) {
//                    val value = intValues[pixel++]
//                    byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255))
//                    byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255))
//                    byteBuffer.putFloat((value and 0xFF) * (1f / 255))
//                }
//            }
//
//            inputFeature0.loadBuffer(byteBuffer)
//
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//            val confidences = outputFeature0.floatArray
//            val maxPos = confidences.indices.maxByOrNull { confidences[it] } ?: -1
//            val maxConfidence = confidences[maxPos] * 100 // Konversi ke persentase
//            val classes = arrayOf(
//                "baca", "bantu", "makan", "bapak", "buangairkecil", "buat", "halo",
//                "ibu", "maaf", "mau", "kamu", "nama", "pagi", "paham", "sakit",
//                "sama-sama", "saya", "selamat", "siapa", "tanya", "tempat",
//                "terimakasih", "terlambat", "tidak", "tolong", "tugas"
//            )
//
//            // Tampilkan hasil dengan confidence
//            result.text = "Prediksi: ${classes[maxPos]} \nAkurasi: %.2f%%".format(maxConfidence)
//
//            model.close()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

    private fun toBitmap(image: ImageProxy): Bitmap {
        val bitmapBuffer =
            Bitmap.createBitmap(
                image.width,
                image.height,
                Bitmap.Config.ARGB_8888,
            )
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        image.close()
        return bitmapBuffer
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation =
        when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }

    interface ClassifierListener {
        fun onError(error: String)

        fun onResults(
            results: List<org.tensorflow.lite.task.vision.classifier.Classifications>?,
            inferenceTime: Long,
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
