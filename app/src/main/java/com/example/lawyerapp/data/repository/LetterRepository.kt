package com.example.lawyerapp.data.repository

import com.example.lawyerapp.domain.model.Letter
import kotlinx.coroutines.flow.Flow

interface LetterRepository {
    fun getLetters(): Flow<List<Letter>>
    suspend fun deleteLetter(letterId: String)
}