package id.handlips.views.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import id.handlips.component.button.LongButton

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onClickLogout: () -> Unit,viewModel: ProfileViewModel = hiltViewModel()) {
    Scaffold (modifier = modifier.fillMaxSize()){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Welcome to Profile")
            LongButton(
                onClick = {
                    Log.d("Logout", "Logout: ${viewModel.logout()}")
                    if (viewModel.logout()){
                        onClickLogout()

                    }
                },
                text = "Logout"
            )
        }
    }
}