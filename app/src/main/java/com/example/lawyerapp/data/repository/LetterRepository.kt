package com.example.lawyerapp.data.repository

import com.example.lawyerapp.data.model.Letter
import kotlinx.coroutines.flow.Flow

interface LetterRepository {
    fun getLetters(): Flow<List<Letter>>
    fun getLetterById(letterId: String): Flow<Letter?>
    suspend fun updateLetter(letter: Letter) // Now this uses the correct Letter
    suspend fun deleteLetter(letterId: String)
}