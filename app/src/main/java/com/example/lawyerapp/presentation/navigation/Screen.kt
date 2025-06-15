package com.example.lawyerapp.presentation.navigation


import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object RequestsList : Screen("requests_list")
    object Auth : Screen("auth")
    object RequestDetail : Screen("request_detail/{letterId}") {
        fun createRoute(letterId: String) = "request_detail/$letterId"
    }
}