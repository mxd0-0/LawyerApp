package com.example.lawyerapp.domain.use_case

import com.example.lawyerapp.data.repository.LetterRepository
import com.example.lawyerapp.data.repository.LetterRepositoryImpl
import javax.inject.Inject // <-- Add this import
import com.example.lawyerapp.domain.use_case.DeleteLetterUseCase
import com.example.lawyerapp.domain.use_case.GetLettersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel

class GetLettersUseCase @Inject constructor(
    private val repository: LetterRepository // Remove the default value
) {
    operator fun invoke() = repository.getLetters()
}