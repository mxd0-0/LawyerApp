package com.example.lawyerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.lawyerapp.presentation.navigation.AppNavigation
import com.example.lawyerapp.ui.theme.LawAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LawAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

