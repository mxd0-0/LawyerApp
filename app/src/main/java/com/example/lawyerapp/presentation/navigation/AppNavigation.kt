package com.example.lawyerapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lawyerapp.domain.model.Letter
import com.example.lawyerapp.presentation.request_detail.RequestDetailScreen
import com.example.lawyerapp.presentation.requests_list.RequestsListScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.RequestsList.route) {
        composable(
            route = Screen.RequestsList.route
        ) {
            RequestsListScreen(
                onNavigateToDetail = { letter ->
                    // Pass the letter object to the next screen
                    navController.currentBackStackEntry?.savedStateHandle?.set("letter", letter)
                    navController.navigate(Screen.RequestDetail.route)
                }
            )
        }

        composable(
            route = Screen.RequestDetail.route
        ) {
            // Retrieve the letter object
            val letter = navController.previousBackStackEntry?.savedStateHandle?.get<Letter>("letter")
            RequestDetailScreen(
                letter = letter,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}