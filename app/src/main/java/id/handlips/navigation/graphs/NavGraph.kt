package id.handlips.navigation.graphs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.utils.welcomePages
import id.handlips.views.bottom_nav.BottomNavScreen
import id.handlips.views.home.HomeViewModel
import id.handlips.views.on_boarding.OnBoardingScreen
import id.handlips.views.on_boarding.OnBoardingViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    onBoardingViewModel: OnBoardingViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val isOnboardingCompleted by onBoardingViewModel.isOnboardingCompleted.collectAsState()
    val isLogin = homeViewModel.isLoggin()

    NavHost(
        navController = navController,
        startDestination = if (isLogin) {
            Route.HOME
        } else {
            if (isOnboardingCompleted) Route.AUTHENTICATION else Route.ONBOARDING
        },
        route = Route.ROOT
    ) {
        composable(route = Route.HOME) {
            BottomNavScreen(
                onCreateChat = {
                    navController.navigate(Route.CHAT)
                },
                onCLickLogout = {
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
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onClickUpdateProfile = {
                    navController.navigate(DetailsScreen.UpdateProfile.route)
                })
        }
        composable(route = Route.ONBOARDING) {
            OnBoardingScreen(
                pages = welcomePages,
                onClickLogin = {
                    navController.popBackStack()
                    navController.navigate(Route.AUTHENTICATION)
                }
            )
        }
        authNavGraph(navController = navController)
        detailsHomeNavGraph(navController, onClickBack = {
            navController.popBackStack()
        })
        chatNavGraph(navController = navController)
    }
}


object Route {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS_HOME = "details_home_graph"
    const val ONBOARDING = "onboarding_graph"
    const val CHAT = "chat_graph"
}