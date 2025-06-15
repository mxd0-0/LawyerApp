package com.example.lawyerapp.data.repository

import com.example.lawyerapp.data.model.Lawyer

interface LawyerRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(fullName: String, email: String, password: String): Result<Unit>
    suspend fun getProfileInformation(): Result<Lawyer>
}
