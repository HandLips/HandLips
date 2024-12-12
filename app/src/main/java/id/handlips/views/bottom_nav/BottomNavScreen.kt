package id.handlips.views.bottom_nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import id.handlips.navigation.graphs.BottomNavGraph
import id.handlips.ui.theme.Blue

@Composable
fun BottomNavScreen(
    onCLickLogout: () -> Unit,
    onClickSubscribe: () -> Unit,
    onClickEvent: () -> Unit,
    onClickGuide: () -> Unit,
    onClickLangganan: () -> Unit,
    onClickCustomerService: () -> Unit,
    onClickUpdateProfile: () -> Unit,
    onClickGantiPassword: () -> Unit,
    onCreateChat: () -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
        bottomBar = { BottomBar(navController = navController) },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(
                navController = navController,
                onCLick = onCLickLogout,
                onClickSubscribe = onClickSubscribe,
                onClickEvent = onClickEvent,
                onClickGuide = onClickGuide,
                onClickLangganan = onClickLangganan,
                onClickCustomerService = onClickCustomerService,
                onClickGantiPassword = onClickGantiPassword,
                onCreateChat = onCreateChat,
                onClickUpdateProfile = onClickUpdateProfile
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens =
        listOf(
            BottomBarScreen.Home,
            BottomBarScreen.Chat,
            BottomBarScreen.Shortcut,
            BottomBarScreen.Profile,
        )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Blue,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .height(65.dp)
            .clip(RoundedCornerShape(16.dp)),
        tonalElevation = 8.dp
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = screen.icon(),
                        contentDescription = screen.title,
                        modifier = Modifier.size(25.dp),
                    )
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue,
                    unselectedIconColor = Color.White.copy(alpha = 0.5f),
                    selectedTextColor = Blue, // Jika teks ada, berwarna biru
                    unselectedTextColor = Color.White.copy(alpha = 0.5f),
                    indicatorColor = Color.White, // Background putih saat dipilih
                ),
            )
        }
    }
}
