package id.handlips.navigation.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.navigation.BottomBarScreen
import id.handlips.views.chat.ChatScreen
import id.handlips.views.home.HomeScreen
import id.handlips.views.profile.ProfileScreen
import id.handlips.views.shortcut.ShortcutScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController, onCLick: () -> Unit, onBackLogin: () -> Unit
) {
    NavHost(
        navController = navController,
        route = Route.HOME,
        startDestination = BottomBarScreen.Home.route
    ){
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(backLogin = onBackLogin)
        }
        composable(route = BottomBarScreen.Chat.route) {
            ChatScreen()
        }
        composable(route = BottomBarScreen.Shortcut.route){
            ShortcutScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen(onClickLogout = onCLick)
        }
    }
}