package id.handlips.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.views.bottom_nav.BottomNavScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.HOME,
        route = Route.ROOT
    ) {
        composable(route = Route.HOME) {
            BottomNavScreen(onCLickLogout = {
                navController.popBackStack()
                navController.navigate(Route.AUTHENTICATION)
            }, onBackLogin = {
                navController.popBackStack()
                navController.navigate(Route.AUTHENTICATION)
            })
        }
        authNavGraph(navController = navController)
    }


}

object Route {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
}