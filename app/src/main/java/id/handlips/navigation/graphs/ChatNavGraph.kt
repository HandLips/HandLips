package id.handlips.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import id.handlips.views.translator.TranslatorScreen

fun NavGraphBuilder.chatNavGraph(navController: NavHostController, onClickHistoryDetail: () -> Unit) {
    navigation(
        route = Route.CHAT,
        startDestination = ChatScreen.Translator.route,
    ) {
        composable(route = ChatScreen.Translator.route) {
            TranslatorScreen(
                onClickHistoryDetail = onClickHistoryDetail
            )
        }
    }
}

sealed class ChatScreen(
    val route: String,
) {
    object Translator : ChatScreen("translator")
}
