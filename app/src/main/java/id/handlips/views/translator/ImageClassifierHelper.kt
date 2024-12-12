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
import kotlin.use

class ImageClassifierHelper(
    var threshold: Float = 0f,
    var maxResults: Int = 1,
    val context: Context,
    val classifierListener: ClassifierListener?,
) {
    private var alphabetClassifier: ImageClassifier? = null
    private var wordClassifier: ImageClassifier? = null

    private val alphabetProcessor =
        ImageProcessor
            .Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.FLOAT32))
            .build()

    private val wordProcessor =
        ImageProcessor
            .Builder()
            .add(ResizeOp(150, 150, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.FLOAT32))
            .build()

    private fun setupClassifiers() {
        alphabetClassifier = initializeModel("model_alphabeth_metadata.tflite")
        wordClassifier = initializeModel("model_coba_metadata.tflite")
    }

    private fun initializeModel(modelName: String): ImageClassifier {
        val optionsBuilder =
            ImageClassifier.ImageClassifierOptions
                .builder()
                .setScoreThreshold(threshold)
                .setMaxResults(maxResults)
                .setBaseOptions(BaseOptions.builder().setNumThreads(4).build())
        return ImageClassifier.createFromFileAndOptions(context, modelName, optionsBuilder.build())
    }

    init {
        setupClassifiers()
    }

    fun classifyImageWithBoth(image: ImageProxy) {
        if (alphabetClassifier == null || wordClassifier == null) {
            setupClassifiers()
        }
        val tensorImage = TensorImage.fromBitmap(toBitmap(image))
        val imageOptions =
            ImageProcessingOptions
                .builder()
                .setOrientation(getOrientationFromRotation(image.imageInfo.rotationDegrees))
                .build()

        val alphabetImage = alphabetProcessor.process(tensorImage)
        val wordImage = wordProcessor.process(tensorImage)

        var inferenceTime = SystemClock.uptimeMillis()
        // Run classification on both models
        val alphabetResults = alphabetClassifier?.classify(alphabetImage, imageOptions)
        val wordResults = wordClassifier?.classify(wordImage, imageOptions)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        Log.d("ImageClassifierHelper", "Alphabet Results: $alphabetResults")
        Log.d("ImageClassifierHelper", "Word Results: $wordResults")

        // Combine results
        classifierListener?.onResults(
            mapOf(
                "Alphabet" to alphabetResults,
                "Word" to wordResults,
            ),
            inferenceTime,
        )
    }

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
            results: Map<String, List<org.tensorflow.lite.task.vision.classifier.Classifications>?>,
            inferenceTime: Long,
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
