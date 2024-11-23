package id.handlips.views.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    Scaffold (modifier = modifier.fillMaxSize()){
        Text("Ini Chat")
    }
}