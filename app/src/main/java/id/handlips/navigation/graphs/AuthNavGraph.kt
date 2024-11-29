package id.handlips.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import id.handlips.views.auth.forgot_password.ForgotPasswordScreen
import id.handlips.views.auth.login.LoginScreen
import id.handlips.views.auth.register.RegisterScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = Route.AUTHENTICATION,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onClickLogin = {
                    navController.popBackStack()
                    navController.navigate(Route.HOME)
                },
                onClickGoogle = { /*TODO*/ },
                onClickRegister = { navController.navigate(Screen.Register.route) },
                onClickForgotPassword = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onClickBack = { navController.popBackStack() },
                onClickRegister = {
                    navController.popBackStack()
                },
                onClickLogin = { navController.popBackStack() },
                onClickGoogle = {}
            )
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController,
                onCLickBack = { navController.popBackStack() },
                onCLickSend = {}
            )
        }
    }
}


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    object OnBoarding : Screen("onboarding")
}