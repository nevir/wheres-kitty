package com.whereskitty.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.whereskitty.game.GameViewModel
import com.whereskitty.ui.screens.GameScreen
import com.whereskitty.ui.screens.HomeScreen
import com.whereskitty.ui.screens.LevelSelectScreen
import com.whereskitty.ui.screens.VictoryScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object LevelSelect : Screen("level_select")
    data object Game : Screen("game/{levelId}") {
        fun routeFor(id: Int) = "game/$id"
    }
    data object Victory : Screen("victory/{levelId}/{stars}") {
        fun routeFor(levelId: Int, stars: Int) = "victory/$levelId/$stars"
    }
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {

        composable(Screen.Home.route) {
            HomeScreen(
                onPlayClicked = { navController.navigate(Screen.LevelSelect.route) }
            )
        }

        composable(Screen.LevelSelect.route) {
            LevelSelectScreen(
                gameViewModel = gameViewModel,
                onLevelSelected = { levelId ->
                    gameViewModel.startLevel(levelId)
                    navController.navigate(Screen.Game.routeFor(levelId))
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Game.route,
            arguments = listOf(navArgument("levelId") { type = NavType.IntType })
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1
            GameScreen(
                levelId = levelId,
                gameViewModel = gameViewModel,
                onLevelComplete = { stars ->
                    navController.navigate(Screen.Victory.routeFor(levelId, stars)) {
                        popUpTo(Screen.Game.routeFor(levelId)) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Victory.route,
            arguments = listOf(
                navArgument("levelId") { type = NavType.IntType },
                navArgument("stars") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1
            val stars = backStackEntry.arguments?.getInt("stars") ?: 1
            VictoryScreen(
                levelId = levelId,
                stars = stars,
                onNextLevel = { nextId ->
                    gameViewModel.startLevel(nextId)
                    navController.navigate(Screen.Game.routeFor(nextId)) {
                        popUpTo(Screen.LevelSelect.route)
                    }
                },
                onLevelSelect = {
                    navController.navigate(Screen.LevelSelect.route) {
                        popUpTo(Screen.LevelSelect.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
