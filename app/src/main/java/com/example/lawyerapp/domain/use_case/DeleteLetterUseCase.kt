package com.example.lawyerapp.domain.use_case

import com.example.lawyerapp.data.repository.LetterRepository
import com.example.lawyerapp.data.repository.LetterRepositoryImpl

class DeleteLetterUseCase(private val repository: LetterRepository = LetterRepositoryImpl()) {
    suspend operator fun invoke(letterId: String) = repository.deleteLetter(letterId)
}