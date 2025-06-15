package com.example.lawyerapp.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.lawyerapp.R
import com.example.lawyerapp.presentation.navigation.Screen

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel() // use Hilt
) {
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") } // Needed for sign-up
    var isSignIn by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Screen.RequestsList.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
        }
    }

    // Navigation when signed in
    LaunchedEffect(authState) {
        if (authState is AuthState.Success || authState is AuthState.ProfileLoaded) {
            navController.navigate(Screen.RequestsList.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
            viewModel.resetState()
        }
    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.justice_scale_svgrepo_com),
                contentDescription = null,
                tint = Color.Unspecified
            )

            if (!isSignIn) {
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) R.drawable.visibility_off else R.drawable.visibility
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { passwordVisible = !passwordVisible }
                    )
                },
                isError = passwordError != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            passwordError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 10.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            when (authState) {
                is AuthState.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(horizontal = 16.dp),
                        onClick = {
                            if (isSignIn) {
                                viewModel.signIn(email, password)
                            } else {
                                if (password.length < 8) {
                                    passwordError = "Too short (min 8)"
                                } else {
                                    viewModel.signUp(fullName, email, password)
                                }
                            }
                        }
                    ) {
                        Text(
                            if (isSignIn) "Sign In" else "Sign Up",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    TextButton(onClick = {
                        isSignIn = !isSignIn
                        passwordError = null
                    }) {
                        Text(
                            buildAnnotatedString {
                                if (isSignIn) {
                                    append("Don't have an account? ")
                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                        append("Sign Up")
                                    }
                                } else {
                                    append("Already have an account? ")
                                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                        append("Sign In")
                                    }
                                }
                            },
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            if (authState is AuthState.Error) {
                Text(
                    text = (authState as AuthState.Error).message,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
}
