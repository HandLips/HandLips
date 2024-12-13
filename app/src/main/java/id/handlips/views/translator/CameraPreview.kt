package id.handlips.views.translator

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onClassificationResults: (String, String) -> Unit,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
) {
    val context: Context = LocalContext.current
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture =
        remember {
            ProcessCameraProvider.getInstance(context)
        }

    val previewView =
        remember {
            PreviewView(context)
        }

    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val imageClassifierHelper =
            ImageClassifierHelper(
                threshold = 0f,
                maxResults = 3,
                context = context,
                classifierListener =
                    object : ImageClassifierHelper.ClassifierListener {
                        override fun onError(error: String) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResults(
                            results: Map<String, List<Classifications>?>,
                            inferenceTime: Long,
                        ) {
                            results.let { it ->
                                Log.e("CameraPreview", "Results: $it")
                                val alphabetResults =
                                    results["Alphabet"]
                                        ?.firstOrNull()
                                        ?.categories
                                        ?.sortedByDescending { it.score }
                                        ?.joinToString("\n") {
                                            "${it.label}: " +
                                                NumberFormat
                                                    .getPercentInstance()
                                                    .format(it.score)
                                                    .trim()
                                        }
                                        ?: "No alphabet detected"

                                val wordResults =
                                    results["Word"]
                                        ?.firstOrNull()
                                        ?.categories
                                        ?.sortedByDescending { it.score }
                                        ?.joinToString("\n") {
                                            "${it.label}: " +
                                                NumberFormat
                                                    .getPercentInstance()
                                                    .format(it.score)
                                                    .trim()
                                        }
                                        ?: "No word detected"

                                Log.d("onResults", "Alphabet Results: \n$alphabetResults")
                                Log.d("onResults", "Word Results: \n$wordResults")

                                onClassificationResults(
                                    "Alphabet Results:\n$alphabetResults\n\nWord Results:\n$wordResults",
                                    "$inferenceTime ms",
                                )
                            }
                        }
                    },
            )

        val preview =
            Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

        val resolutionSelector =
            ResolutionSelector
                .Builder()
                .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
                .build()
        val imageAnalyzer =
            ImageAnalysis
                .Builder()
                .setResolutionSelector(resolutionSelector)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also { analysis ->
                    analysis.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                        imageClassifierHelper.classifyImageWithBoth(image)
                    }
                }

        val cameraSelector =
            CameraSelector
                .Builder()
                .requireLensFacing(lensFacing)
                .build()

        Log.d("PreviewCam", cameraSelector.toString())

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer,
            )
        } catch (exc: Exception) {
            Log.e("CameraView", "Error binding camera lifecycle: ${exc.message}")
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { previewView },
//        factory = { context ->
//            PreviewView(context)
//                .apply {
//                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
//                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
//                    scaleType = PreviewView.ScaleType.FILL_START
//                }.also { previewView ->
//                    previewView.controller = cameraController
//                    cameraController.bindToLifecycle(lifecycleOwner)
//                }
//        },
    )
}
