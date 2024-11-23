package id.handlips.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.handlips.views.auth.forgot_password.ForgotPasswordScreen
import id.handlips.views.auth.login.LoginScreen
import id.handlips.views.auth.register.RegisterScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.ForgotPassword.route){
            ForgotPasswordScreen(navController)
        }
    }
}