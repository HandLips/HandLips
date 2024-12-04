package id.handlips.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.views.home.HomeScreen
import id.handlips.views.profile.ProfileScreen
import id.handlips.views.shortcut.ShortcutScreen
import id.handlips.views.translator.ChatScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    onCLick: () -> Unit,
    onClickSubscribe: () -> Unit,
    onClickEvent: () -> Unit,
    onClickGuide: () -> Unit,
    onClickLangganan: () -> Unit,
    onClickCustomerService: () -> Unit,
    onClickGantiPassword: () -> Unit,
) {
    NavHost(
        navController = navController,
        route = Route.HOME,
        startDestination = BottomBarScreen.Home.route,
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen( onClickSubscripe = onClickSubscribe, onClickEvent = onClickEvent)
        }
        composable(route = BottomBarScreen.Chat.route) {
            ChatScreen()
        }
        composable(route = BottomBarScreen.Shortcut.route){
            ShortcutScreen(navHostController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen(onClickLogout = onCLick, onClickLangganan = onClickLangganan, onClickCustomerService = onClickCustomerService, onClickGuide = onClickGuide, onCickGantiPassword = onClickGantiPassword)
        }

    }

}

