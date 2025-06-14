package com.example.lawyerapp.domain.use_case

import com.example.lawyerapp.data.repository.LetterRepository
import com.example.lawyerapp.data.repository.LetterRepositoryImpl


// For simplicity, we are not creating an instance but you would with DI (Hilt)
class GetLettersUseCase(private val repository: LetterRepository = LetterRepositoryImpl()) {
    operator fun invoke() = repository.getLetters()
}