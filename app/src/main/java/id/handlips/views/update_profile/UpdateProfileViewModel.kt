package id.handlips.views.update_profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Uri
import id.handlips.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import javax.inject.Inject

// ViewModel
class UpdateProfileViewModel @Inject constructor(
//    private val repository: Repository
) : ViewModel() {

//    private val _uploadState = MutableStateFlow<Resource<String>>(Resource.Initial)
//    val uploadState: StateFlow<Resource<String>> = _uploadState.asStateFlow()

//    fun uploadImage(uri: Uri, context: Context) {
//        viewModelScope.launch {
//            _uploadState.value = Resource.Loading
//
//            try {
//                val inputStream = context.contentResolver.openInputStream(uri)
//                val file = createTempFile(context, inputStream)
//
//                // Compress image
//                val compressedFile = Compressor.compress(context, file) {
//                    resolution(1024, 1024)
//                    quality(80)
//                    format(Bitmap.CompressFormat.JPEG)
//                }
//
//                // Upload to your API
//                val result = repository.uploadImage(compressedFile)
//                _uploadState.value = Resource.Success(result.imageUrl)
//
//            } catch (e: Exception) {
//                _uploadState.value = Resource.Error(e.message ?: "Unknown error occurred")
//            }
//        }
//    }

//    private fun createTempFile(context: Context, inputStream: InputStream?): File {
//        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
//        tempFile.deleteOnExit()
//        inputStream?.use { input ->
//            tempFile.outputStream().use { output ->
//                input.copyTo(output)
//            }
//        }
//        return tempFile
//    }
}