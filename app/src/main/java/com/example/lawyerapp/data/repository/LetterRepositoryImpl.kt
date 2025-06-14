package com.example.lawyerapp.data.repository


import com.example.lawyerapp.domain.model.Letter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import android.util.Log



class LetterRepositoryImpl : LetterRepository {

    // This will now work because you added the firebase-firestore library
    private val firestore = FirebaseFirestore.getInstance()

    // This refers to your "letters" collection from the screenshot
    private val lettersCollection = firestore.collection("letters")

    /**
     *  Listens for real-time updates from your "letters" collection in Firestore.
     */
    override fun getLetters(): Flow<List<Letter>> = callbackFlow {
        Log.d("LawAppDebug", "Repository: Trying to attach snapshot listener...")
        val listener = lettersCollection.addSnapshotListener { snapshot, error ->
            // Check for errors from Firebase
            if (error != null) {
                Log.e("LawAppDebug", "Repository: Firebase listener error!", error)
                close(error)
                return@addSnapshotListener
            }

            // Check if the snapshot has data
            if (snapshot != null && !snapshot.isEmpty) {
                val letters = snapshot.toObjects(Letter::class.java)
                Log.d("LawAppDebug", "Repository: Success! Fetched ${letters.size} documents.")
                trySend(letters).isSuccess
            } else {
                // This will tell us if Firebase connected but the collection was empty
                Log.d("LawAppDebug", "Repository: Firebase returned a null or empty snapshot.")
                trySend(emptyList()).isSuccess // Send an empty list to the UI
            }
        }

        awaitClose {
            Log.d("LawAppDebug", "Repository: Closing listener.")
            listener.remove()
        }
    }

    /**
     *  Deletes a document from the "letters" collection using its ID.
     */
    override suspend fun deleteLetter(letterId: String) {
        try {
            // This is the correct way to delete a document in Firestore
            lettersCollection.document(letterId).delete().await()
        } catch (e: Exception) {
            // In case of an error (e.g., no internet), it will be printed
            e.printStackTrace()
        }
    }
}