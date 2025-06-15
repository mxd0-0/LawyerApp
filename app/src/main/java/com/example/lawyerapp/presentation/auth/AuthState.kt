package com.example.lawyerapp.presentation.auth

import com.example.lawyerapp.data.model.Lawyer


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
    data class ProfileLoaded(val lawyer: Lawyer) : AuthState()
}
