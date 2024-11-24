package id.handlips.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.views.chat.ChatScreen
import id.handlips.views.home.HomeScreen
import id.handlips.views.profile.ProfileScreen
import id.handlips.views.shortcut.ShortcutScreen

@Composable
fun BottomNavGraph(
    onLogout: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        modifier = modifier
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Chat.route) {
            ChatScreen()
        }
        composable(route = BottomBarScreen.Shortcut.route) {
            ShortcutScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(onLogout = onLogout)
        }
    }
}