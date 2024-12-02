package id.handlips.views.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import id.handlips.component.button.LongButton
import id.handlips.R
import id.handlips.component.dialog.DialogError
import id.handlips.ui.theme.poppins
import id.handlips.utils.Constraint.clientId

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onClickLogout: () -> Unit,
    onClickLangganan: () -> Unit,
    onClickCustomerService: () -> Unit,
    onClickGuide: () -> Unit,
    onCickGantiPassword: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(clientId)
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)
    var dialogError by remember { mutableStateOf(false) }
    var textError by remember { mutableStateOf("") }
    if (dialogError) {
        DialogError(onDismissRequest = { dialogError = false }, textError = textError)
    }
    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile Photo",
                modifier = Modifier.clip(shape = CircleShape),
            )
            Spacer(Modifier.height(20.dp))
            Row(
                modifier =
                Modifier
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
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
            Text("Ivan Try Wicaksono", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(viewModel.getCurrentEmail())
            Spacer(Modifier.height(12.dp))
            Button(
                shape = RoundedCornerShape(8.dp),
                onClick = { null },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue,
                    contentColor = Color.White
                ),
            ) { Text("Edit Profile") }

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
                                // Jika logout berhasil di ViewModel, panggil onClickLogout()
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
            modifier =
            Modifier
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
                modifier =
                Modifier
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
