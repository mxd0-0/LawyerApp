package com.example.lawyerapp.domain.use_case

import com.example.lawyerapp.data.model.Lawyer
import com.example.lawyerapp.data.repository.LawyerRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: LawyerRepository
) {

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return repository.signIn(email, password)
    }

    suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> {
        return repository.signUp(fullName, email, password)
    }

    suspend fun getProfileInformation(): Result<Lawyer> {
        return repository.getProfileInformation()
    }
}
