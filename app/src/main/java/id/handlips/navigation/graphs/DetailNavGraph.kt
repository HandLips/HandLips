package id.handlips.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import id.handlips.views.customer_service.CustomerServiceScreen
import id.handlips.views.guide.GuideScreen
import id.handlips.views.history.HistoryScreen
import id.handlips.views.menu_home.gemini.GeminiScreen
import id.handlips.views.menu_home.subscribe.SubscribeScreen

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
    }
}



sealed class DetailsScreen(val route: String) {
    object Subscribe : DetailsScreen(route = "SUBSCRIPE")
    object History : DetailsScreen(route = "HISTORY")
    object Event : DetailsScreen(route = "EVENT")
    object CustomerService: DetailsScreen(route = "CUSTOMER_SERVICE")
    object Guide: DetailsScreen(route = "GUIDE")
}