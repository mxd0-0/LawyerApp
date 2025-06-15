package com.example.lawyerapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.lawyerapp.presentation.auth.AuthScreen
import com.example.lawyerapp.presentation.request_detail.RequestDetailScreen
import com.example.lawyerapp.presentation.requests_list.RequestsListScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Auth.route) {

        composable(route = Screen.Auth.route) {
            AuthScreen(navController = navController)
        }

        composable(route = Screen.RequestsList.route) {
            RequestsListScreen(
                onNavigateToDetail = { letter ->
                    navController.navigate(Screen.RequestDetail.createRoute(letter.idLetter))
                }
            )
        }
        composable(
            route = Screen.RequestDetail.route,
            arguments = listOf(navArgument("letterId") { type = NavType.StringType })
        ) {
            RequestDetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
