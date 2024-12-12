package id.handlips.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import id.handlips.views.customer_service.CustomerServiceScreen
import id.handlips.views.guide.GuideScreen
import id.handlips.views.history.HistoryScreen
import id.handlips.views.menu_home.gemini.GeminiScreen
import id.handlips.views.menu_home.subscribe.SubscribeScreen
import id.handlips.views.update_profile.UpdateProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.detailsHomeNavGraph(navController: NavHostController, onClickBack: () -> Unit) {
    navigation(route = Route.DETAILS_HOME, startDestination = DetailsScreen.Subscribe.route){
        composable(route = DetailsScreen.Subscribe.route){
            SubscribeScreen(onCLickBack = onClickBack)
        }
        composable(route = DetailsScreen.History.route) {
            HistoryScreen()
        }
        composable(route = DetailsScreen.Event.route){
            GeminiScreen(onClickBack = onClickBack)
        }
        composable(route = DetailsScreen.CustomerService.route){
            CustomerServiceScreen(onClickBack = onClickBack)
        }
        composable(route = DetailsScreen.Guide.route){
            GuideScreen(onClickBack = onClickBack)
        }
        composable(
            route = DetailsScreen.UpdateProfile.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("photoUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val photoUrl = backStackEntry.arguments?.getString("photoUrl") ?: ""

            UpdateProfileScreen(
                onClickBack = onClickBack,
                name = name,
                photoUrl = photoUrl
            )
        }
    }
}



sealed class DetailsScreen(val route: String) {
    object Subscribe : DetailsScreen(route = "SUBSCRIPE")
    object History : DetailsScreen(route = "HISTORY")
    object Event : DetailsScreen(route = "EVENT")
    object CustomerService: DetailsScreen(route = "CUSTOMER_SERVICE")
    object Guide: DetailsScreen(route = "GUIDE")
    object UpdateProfile : DetailsScreen(route = "update_profile/{name}/{photoUrl}") {
        fun createRoute(name: String, photoUrl: String) = "update_profile/$name/$photoUrl"
    }

}