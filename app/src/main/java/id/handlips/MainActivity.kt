package id.handlips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.handlips.navigation.graphs.NavGraph
import id.handlips.ui.theme.HandlipsTheme

@AndroidEntryPoint
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

