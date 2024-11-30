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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import id.handlips.ui.theme.Blue
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.component.button.LongButton
import id.handlips.R

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
    Scaffold(modifier = modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingValues).padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile Photo",
                modifier = Modifier.clip(shape = CircleShape),
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier =
                Modifier
                    .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Canvas(modifier = Modifier.size(12.dp)) {
                    drawCircle(color = Color.Gray)
                    drawCircle(color = Color.Blue, style = Stroke(width = 3f))
                }
                Spacer(Modifier.width(8.dp))
                Text("Reguler")
            }
            Spacer(Modifier.height(12.dp))
            Text("Ivan Try Wicaksono", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("ivan@gmail.com")
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
                SectionItem("Langganan", "Description", onClickItem = onClickLangganan)
                SectionItem("Customer Service", "Description", onClickItem = onClickCustomerService)
                SectionItem("Guide", "Description", false, onClickItem = onClickGuide)
            }

            Section("Pengaturan") {
                SectionItem("Ganti Password", "Description", onClickItem = onCickGantiPassword)
                SectionItem("Log Out", "", false, onClickItem = {
                    if (viewModel.logout()) {
                        onClickLogout()
                    }
                })
            }
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
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 8.dp),
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
    onClickItem: () -> Unit
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
                Icon(Icons.Default.Person, contentDescription = "Person")
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
