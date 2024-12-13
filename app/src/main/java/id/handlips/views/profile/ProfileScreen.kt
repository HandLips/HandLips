package id.handlips.views.profile

import android.provider.ContactsContract.Profile
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import id.handlips.ui.theme.Blue
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import id.handlips.R
import id.handlips.component.dialog.DialogError
import id.handlips.data.model.DataProfile
import id.handlips.data.model.ProfileModel
import id.handlips.ui.theme.poppins
import id.handlips.utils.Constraint.clientId
import id.handlips.utils.Resource

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit,
    onClickLangganan: () -> Unit,
    onClickCustomerService: () -> Unit,
    onClickGuide: () -> Unit,
    onCickGantiPassword: () -> Unit,
    onClickUpdateProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val currentUser = viewModel.getCurrent()
    val isGoogleLogin = currentUser?.providerData?.any { it.providerId == "google.com" } ?: false
    var profile by remember { mutableStateOf<DataProfile?>(null) }
    var loading by remember { mutableStateOf(false) }
    var dialogError by remember { mutableStateOf(false) }
    var textError by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
        .requestIdToken(clientId).build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

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


    if (dialogError) {
        DialogError(onDismissRequest = { dialogError = false }, textError = textError)
    }

    LaunchedEffect(Unit) {
        viewModel.getProfile(userDisplayInfo.email.toString()).observeForever { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("HomeScreen", "Fetching profile: Loading...")
                    loading = true
                }

                is Resource.Success -> {
                    loading = false
                    profile = resource.data.data
                    Log.d("HomeScreen", "Profile fetched successfully: ${profile?.name}")
                }

                is Resource.Error -> {
                    loading = false
                }
            }
        }
    }
    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = if (userDisplayInfo.photoUrl != "") userDisplayInfo.photoUrl else R.drawable.profile,
                contentDescription = "Deskripsi gambar",
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(130.dp) // Ukuran gambar
            )
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(color = Color.Gray)
                    drawCircle(color = Color.Blue, style = Stroke(width = 3f))
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    "Reguler", style = TextStyle(
                        fontFamily = poppins, fontWeight = FontWeight.Normal, fontSize = 12.sp
                    )
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(userDisplayInfo.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(userDisplayInfo.email.toString())
            Spacer(Modifier.height(12.dp))
            Section("Inventaris") {
                SectionItem(
                    "Langganan",
                    "Ayo mulai berlangganan",
                    onClickItem = onClickLangganan,
                    color = Color.Green,
                    icon = R.drawable.ic_payment
                )
                SectionItem(
                    "Customer Service",
                    "Kami siap membantu",
                    onClickItem = onClickCustomerService,
                    color = Blue,
                    icon = R.drawable.ic_customer_service
                )
                SectionItem(
                    "Guide",
                    "Semua menjadi mudah",
                    false,
                    onClickItem = onClickGuide,
                    color = Color.Cyan,
                    icon = R.drawable.ic_bolt
                )
            }

            Section("Pengaturan") {
                SectionItem(
                    "Ganti Password",
                    "Jaga keamanan data",
                    onClickItem = onCickGantiPassword,
                    color = Color.Black.copy(alpha = 0.9f),
                    icon = R.drawable.ic_change_password
                )
                SectionItem("Log Out", "Yakin Mau Log Out", false, onClickItem = {
                    googleSignInClient.signOut().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Google sign-out berhasil
                            if (viewModel.logout()) {
                                onClickLogout()
                            }
                        } else {
                            dialogError = true
                            textError = "Google sign-out failed: ${task.exception?.message}"
                        }
                    }
                }, color = Color.Red, icon = R.drawable.ic_logout)
            }
            Spacer(Modifier.padding(bottom = 30.dp))
        }
    }
}

@Preview
@Composable
private fun ProfilePreview() {
//    ProfileScreen(onClickLogout = { null })
}

@Composable
fun Section(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Text(text = title, modifier = Modifier.padding(vertical = 8.dp), fontSize = 18.sp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 5.dp, // Tinggi bayangan
                    shape = RoundedCornerShape(15.dp), // Bentuk bayangan
                ),
        ) {
            content()
        }
    }
}

@Composable
fun SectionItem(
    title: String,
    description: String,
    divider: Boolean = true,
    onClickItem: () -> Unit,
    color: Color,
    icon: Int,
) {
    Surface(onClick = onClickItem) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = icon),
                    colorFilter = ColorFilter.tint(color),
                    contentDescription = "Person Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(title)
                    if (description.isNotBlank()) {
                        Spacer(Modifier.height(4.dp))
                        Text(description, color = Color.Gray)
                    }
                }
            }
            if (divider) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
            }
        }
    }
}
