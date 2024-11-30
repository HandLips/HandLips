package id.handlips.navigation.graphs

import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import id.handlips.navigation.BottomBarScreen
import id.handlips.views.chat.ChatScreen
import id.handlips.views.home.HomeScreen
import id.handlips.views.profile.ProfileScreen
import id.handlips.views.shortcut.ShortcutScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    onCLick: () -> Unit,
    onBackLogin: () -> Unit,
    onClickSubscribe: () -> Unit,
    onClickEvent: () -> Unit,
) {
    NavHost(
        navController = navController,
        route = Route.HOME,
        startDestination = BottomBarScreen.Home.route,
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(backLogin = onBackLogin, onClickSubscripe = onClickSubscribe, onClickEvent = onClickEvent)
        }
        composable(route = BottomBarScreen.Chat.route) {
            ChatScreen()
        }
        composable(route = BottomBarScreen.Shortcut.route) {
            ShortcutScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(onClickLogout = onCLick)
        }
    }
}
