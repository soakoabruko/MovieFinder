package com.team.moviefinder.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team.moviefinder.ui.details.DetailsScreen
import com.team.moviefinder.ui.home.HomeScreen
import com.team.moviefinder.ui.search.SearchScreen
import com.team.moviefinder.ui.settings.SettingsScreen

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home",
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("home") {
            HomeScreen(navController)
        }

        composable("search") {
            SearchScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetails = { id ->
                    navController.navigate("details/$id")
                },
            )
        }

        composable(
            route = "details/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: return@composable

            DetailsScreen(
                id = id,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable("settings") {
            SettingsScreen(
                navController = navController,
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}