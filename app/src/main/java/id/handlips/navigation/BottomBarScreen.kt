import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import id.handlips.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: @Composable () -> Painter,
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = { painterResource(R.drawable.ic_home) } // Sesuaikan dengan nama file drawable Anda
    )
    object Chat : BottomBarScreen(
        route = "CHAT",
        title = "CHAT",
        icon = { painterResource(R.drawable.ic_message) }
    )
    object Shortcut : BottomBarScreen(
        route = "SHORTCUT",
        title = "SHORTCUT",
        icon = { painterResource(R.drawable.ic_bolt) }
    )
    object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "PROFILE",
        icon = { painterResource(R.drawable.ic_person) }
    )
}
