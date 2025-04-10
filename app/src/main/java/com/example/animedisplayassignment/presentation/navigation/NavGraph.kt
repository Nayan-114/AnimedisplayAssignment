package com.example.animedisplayassignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animedisplayassignment.presentation.AnimeDetailScreen
import com.example.animedisplayassignment.presentation.AnimeListPageScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "anime_list"
    ) {
        composable("anime_list") {
            AnimeListPageScreen(navController = navController)
        }
        composable(
            route = "anime_detail/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
            AnimeDetailScreen(animeId = animeId)
        }
    }
}
