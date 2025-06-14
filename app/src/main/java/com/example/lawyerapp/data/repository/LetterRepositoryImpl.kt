package com.example.lawyerapp.data.repository

import com.example.lawyerapp.domain.model.Letter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID

class LetterRepositoryImpl : LetterRepository {

    // In a real app, this would be your Firestore listener.
    private val mockLetters = MutableStateFlow<List<Letter>>(
        (1..10).map {
            Letter(
                idLetter = UUID.randomUUID().toString(),
                title = "Title Consult",
                description = "This is a Small desciption of the jda bd...",
                date = "12/12/2012",
                fullName = "USER$it$it$it$it",
                email = "Mohamed@gmailfb$it.com"
            )
        }
    )

    override fun getLetters(): Flow<List<Letter>> {
        return mockLetters
    }

    override suspend fun deleteLetter(letterId: String) {
        // Simulate deleting a letter
        val currentList = mockLetters.value.toMutableList()
        currentList.removeAll { it.idLetter == letterId }
        mockLetters.value = currentList
    }
}