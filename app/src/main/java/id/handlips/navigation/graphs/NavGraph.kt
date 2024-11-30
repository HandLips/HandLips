package id.handlips.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import id.handlips.R
import id.handlips.navigation.BottomBarScreen
import id.handlips.views.bottom_nav.BottomNavScreen
import id.handlips.views.menu_home.gemini.GeminiScreen
import id.handlips.views.history.HistoryScreen
import id.handlips.views.on_boarding.OnBoardingScreen
import id.handlips.views.on_boarding.OnboardingPage
import id.handlips.views.menu_home.subscribe.SubscribeScreen

@Composable
fun NavGraph(navController: NavHostController) {
//    val startDestination = if (isOnboardingCompleted) Route.HOME else Route.ONBOARDING
    NavHost(
        navController = navController,
        startDestination = Route.HOME,
        route = Route.ROOT
    ) {
        composable(route = Route.HOME) {
            BottomNavScreen(
                onCLickLogout = {
                    navController.popBackStack()
                    navController.navigate(Route.AUTHENTICATION)
                },
                onBackLogin = {
                    navController.popBackStack()
                    navController.navigate(Route.AUTHENTICATION)
                },
                onClickSubscribe = {
                    navController.navigate(DetailsScreen.Subscribe.route)
                },
                onClickEvent = {
                    navController.navigate(DetailsScreen.Event.route)
                },
                onClickLangganan = {
                    navController.navigate(DetailsScreen.Subscribe.route)
                },
                onClickCustomerService = {
                    navController.navigate(DetailsScreen.CustomerService.route)
                },
                onClickGuide = {
                    navController.navigate(DetailsScreen.Guide.route)
                },
                onClickGantiPassword = {
                    navController.navigate(Screen.ChangePassword.route)
                })
        }
//        composable(route = Route.ONBOARDING) {
//            OnBoardingScreen(
//                pages = welcomePages,
//                onClickLogin = {
//                    navController.popBackStack()
//                    navController.navigate(Route.AUTHENTICATION)
//                }
//            )
//        }
        authNavGraph(navController = navController)
        detailsHomeNavGraph(navController, onClickBack = {
            navController.popBackStack()
        })
    }
}


object Route {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS_HOME = "details_home_graph"
//    const val ONBOARDING = "onboarding_graph"
}