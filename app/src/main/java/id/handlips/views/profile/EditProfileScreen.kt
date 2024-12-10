package id.handlips.views.profile

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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.handlips.component.textfield.GeneralTextField
import id.handlips.component.button.LongButton
import id.handlips.R
import id.handlips.ui.theme.White

@Composable
private fun EditUserProfile(onClickEditProfile: () -> Unit) {
    var name by remember { mutableStateOf("TODO: Get nama user") }

    Scaffold { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.clip(shape = CircleShape),
                )
                FloatingActionButton(
                    containerColor = White,
                    shape = CircleShape,
                    onClick = { /*TODO: onClickEditProfilePicture*/ },
                    modifier = Modifier.padding(4.dp).size(48.dp).align(Alignment.BottomEnd),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = "Edit Profile Picture",
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
            GeneralTextField(
                title = "Nama",
                label = "Masukkan nama Anda",
                value = name,
                onValueChange = { name = it },
            )
            LongButton(text = "Simpan", onClick = onClickEditProfile)
        }
    }
}

@Preview
@Composable
private fun EditProfilePreview() {
    EditUserProfile(onClickEditProfile = {})
}