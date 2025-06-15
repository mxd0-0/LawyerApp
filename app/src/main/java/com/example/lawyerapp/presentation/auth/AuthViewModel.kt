package com.example.lawyerapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerapp.domain.use_case.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject




@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authUseCase.signIn(email, password)
            _authState.value = result.fold(
                onSuccess = { AuthState.Success("Signed in successfully") },
                onFailure = { AuthState.Error(it.message ?: "Sign-in failed") }
            )
        }
    }

    fun signUp(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authUseCase.signUp(fullName, email, password)
            _authState.value = result.fold(
                onSuccess = { AuthState.Success("Account created successfully") },
                onFailure = { AuthState.Error(it.message ?: "Sign-up failed") }
            )
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authUseCase.getProfileInformation()
            _authState.value = result.fold(
                onSuccess = { lawyer -> AuthState.ProfileLoaded(lawyer) },
                onFailure = { AuthState.Error(it.message ?: "Failed to load profile") }
            )
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

