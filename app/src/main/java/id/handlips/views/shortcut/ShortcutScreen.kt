package id.handlips.views.shortcut

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShortcutScreen(modifier: Modifier = Modifier) {
    Scaffold (modifier = modifier.fillMaxSize()){ it ->
        modifier.padding(it)
        Text("Ini Shortcut")
    }
}



