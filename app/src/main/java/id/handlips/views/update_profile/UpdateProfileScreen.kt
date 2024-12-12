package id.handlips.views.update_profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import id.handlips.component.dialog.DialogSuccess
import id.handlips.component.loading.LoadingAnimation
import id.handlips.data.model.DataProfile
import id.handlips.data.model.ProfileModel
import id.handlips.ui.theme.White
import id.handlips.utils.Resource
import id.handlips.utils.UiState
import id.handlips.utils.uriToFile
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    onClickBack: () -> Unit,
    viewModel: UpdateProfileViewModel = hiltViewModel()
) {
    val currentUser = viewModel.getCurrent()
    val isGoogleLogin = currentUser?.providerData?.any { it.providerId == "google.com" } ?: false
    var profile by remember { mutableStateOf<DataProfile?>(null) }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var isLoading by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    var showDialogSuccess by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            Log.d("UpdateProfileScreen", "Selected image URI: $uri")
            selectedImageUri = uri
        }
    )
    val userDisplayInfo = remember(currentUser, profile) {
        if (isGoogleLogin) {
            ProfileModel(
                name = currentUser?.displayName ?: context.getString(R.string.guest),
                photoUrl = currentUser?.photoUrl.toString(),
                email = currentUser?.email.toString()
            )
        } else {
            ProfileModel(
                name = profile?.name ?: context.getString(R.string.guest),
                photoUrl = profile?.profile_picture_url ?: "",
                email = currentUser?.email.toString()
            )
        }
    }
    var updateName by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        Log.d("UpdateProfileScreen", "Current UI State: $uiState")
        when (uiState) {
            is UiState.Success -> {
                Log.d("UpdateProfileScreen", "Profile update successful")
                showDialogSuccess = true
            }

            is UiState.Error -> {
                errorMessage = (uiState as UiState.Error).message
                Log.d("UpdateProfileScreen", "Error occurred: $errorMessage")
                showError = true
            }

            is UiState.Loading -> {
                Log.d("UpdateProfileScreen", "Loading...")
                isLoading = true
            }

            else -> {
                Log.d("UpdateProfileScreen", "UI State: Idle")
                isLoading = false
            }
        }

        viewModel.getProfile(userDisplayInfo.email.toString()).observeForever { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("HomeScreen", "Fetching profile: Loading...")
                    isLoading = true
                }

                is Resource.Success -> {
                    isLoading = false
                    profile = resource.data.data
                    updateName = profile?.name ?: ""
                    Log.d("HomeScreen", "Profile fetched successfully: ${profile?.name}")
                }

                is Resource.Error -> {
                    isLoading = false
                }
            }
        }
    }


    if (showError) {
        DialogError(
            onDismissRequest = { showError = false },
            textError = errorMessage
        )
        Log.d("UpdateProfileScreen", "Error dialog dismissed: $errorMessage")
    }
    if (showDialogSuccess) {
        DialogSuccess(
            onDismissRequest = onClickBack,
            textSuccess = stringResource(R.string.update_profile_success)
        )
    }
    if (isLoading) {
        LoadingAnimation()
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
                    model = selectedImageUri
                        ?: if (userDisplayInfo.photoUrl != "") userDisplayInfo.photoUrl else R.drawable.profile,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .size(130.dp)
                )
                FloatingActionButton(
                    containerColor = White,
                    shape = CircleShape,
                    onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
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
                onValueChange = { updateName = it }
            )

            Spacer(Modifier.height(32.dp))

            LongButton(
                text = "Save",
                onClick = {
                    selectedImageUri?.let { uri ->
                        Log.d(
                            "UpdateProfileScreen",
                            "Preparing to upload profile with image URI: $uri"
                        )
                        val file = uriToFile(uri, context).absoluteFile
                        viewModel.updateProfile(
                            email = userDisplayInfo.email.toString(),
                            name = updateName,
                            profilePicture = file
                        )
                        Log.d("UpdateProfileScreen", "Converted URI to file: ${file}")
                    }

                },
            )

        }
    }
}

fun Uri.toFile(context: Context): File {
    Log.d("UpdateProfileScreen", "Converting URI to file: $this")
    val tempFile = File(context.cacheDir, "profile_image_${System.currentTimeMillis()}.jpg")
    tempFile.createNewFile()

    context.contentResolver.openInputStream(this)?.use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    Log.d("UpdateProfileScreen", "File created at: ${tempFile.absolutePath}")
    return tempFile
}