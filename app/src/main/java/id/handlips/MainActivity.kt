package id.handlips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import id.handlips.navigation.NavGraph
import id.handlips.ui.theme.HandlipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandlipsTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}

