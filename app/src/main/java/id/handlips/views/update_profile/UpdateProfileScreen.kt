package id.handlips.views.update_profile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import id.handlips.component.textfield.GeneralTextField
import id.handlips.component.button.LongButton
import id.handlips.R
import id.handlips.component.dialog.DialogError
import id.handlips.ui.theme.White
import id.handlips.utils.Resource
import id.handlips.views.profile.ProfileViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    onClickBack: () -> Unit,
    name: String,
    photoUrl: String,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var updateName by remember { mutableStateOf(name) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
        }
    }

    // Observe update profile result
    selectedImageUri?.let {
        viewModel.updateProfile(name, it.toFile(context)).value?.let { resource ->
        when (resource) {
            is Resource.Loading -> {
                isLoading = true
            }
            is Resource.Success -> {
                isLoading = false
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                onClickBack()
            }
            is Resource.Error -> {
                isLoading = false
                errorMessage = resource.message
                showError = true
            }
        }
    }
    }

    if (showError) {
        DialogError(
            onDismissRequest = { showError = false },
            textError = errorMessage
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text("Update Profile")
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                AsyncImage(
                    model = selectedImageUri ?: if (photoUrl != "") photoUrl else R.drawable.profile,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(130.dp)
                )
                FloatingActionButton(
                    containerColor = White,
                    shape = CircleShape,
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .padding(4.dp)
                        .size(48.dp)
                        .align(Alignment.BottomEnd),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Edit Profile Picture",
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            GeneralTextField(
                title = stringResource(R.string.name),
                label = stringResource(R.string.enter_your_name),
                value = updateName,
                onValueChange = { updateName = it },
            )

            Spacer(Modifier.height(32.dp))

            LongButton(
                text = "Simpan",
                onClick = {
                    selectedImageUri?.let { uri ->
                        val file = uri.toFile(context)
                        viewModel.updateProfile(updateName, file)
                    }
                },
            )
        }
    }
}

// Extension function untuk convert Uri ke File
fun Uri.toFile(context: Context): File {
    val tempFile = File(context.cacheDir, "profile_image_${System.currentTimeMillis()}.jpg")
    tempFile.createNewFile()

    context.contentResolver.openInputStream(this)?.use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return tempFile
}