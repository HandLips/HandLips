package id.handlips.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.views.bottom_nav.BottomNavScreen
import id.handlips.views.auth.forgot_password.ForgotPasswordScreen
import id.handlips.views.auth.login.LoginScreen
import id.handlips.views.auth.register.RegisterScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.ForgotPassword.route){
            ForgotPasswordScreen(navController)
        }
        composable(route = Screen.Main.route) {
            BottomNavScreen(onLogout = { navController.navigate(Screen.Login.route){
                popUpTo(Screen.Main.route){
                    inclusive = true
                }
            } })
        }
    }
}