package com.example.lawyerapp.domain.use_case

import com.example.lawyerapp.data.repository.LetterRepository
import com.example.lawyerapp.data.repository.LetterRepositoryImpl
import javax.inject.Inject

class DeleteLetterUseCase @Inject constructor(
    private val repository: LetterRepository // Remove the default value
) {
    suspend operator fun invoke(letterId: String) = repository.deleteLetter(letterId)
}