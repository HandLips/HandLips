package id.handlips.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen("home", "Home", Icons.Outlined.Home)
    object Chat : BottomBarScreen("chat", "Chat", Icons.Outlined.Email)
    object Shortcut : BottomBarScreen("shortcut", "Shortcut", Icons.Outlined.PlayArrow)
    object Profile : BottomBarScreen("profile", "Profile", Icons.Outlined.Person)

}