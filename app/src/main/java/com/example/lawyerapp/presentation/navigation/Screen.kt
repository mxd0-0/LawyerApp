package com.example.lawyerapp.presentation.navigation


import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.lawyerapp.domain.model.Letter

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object RequestsList : Screen("requests_list")

    object RequestDetail : Screen(
        route = "request_detail",
        arguments = listOf(navArgument("letter") {
            type = NavType.ParcelableType(Letter::class.java)
        })
    )
}