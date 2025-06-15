package com.example.lawyerapp.data.repository


import android.system.Os.close
import com.example.lawyerapp.data.model.Letter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import android.util.Log



class LetterRepositoryImpl : LetterRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val lettersCollection = firestore.collection("letters")

    // --- getLetters is now WRAPPED in the callbackFlow block ---
    override fun getLetters(): Flow<List<Letter>> = callbackFlow {
        Log.d("LawAppDebug", "Repository: Trying to attach snapshot listener...")
        val listener = lettersCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("LawAppDebug", "Repository: Firebase listener error!", error)
                close(error)
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val letters = snapshot.toObjects(Letter::class.java)
                Log.d("LawAppDebug", "Repository: Success! Fetched ${letters.size} documents.")
                trySend(letters).isSuccess
            } else {
                Log.d("LawAppDebug", "Repository: Firebase returned a null or empty snapshot.")
                trySend(emptyList()).isSuccess
            }
        }

        awaitClose {
            Log.d("LawAppDebug", "Repository: Closing listener.")
            listener.remove()
        }
    }

    override fun getLetterById(letterId: String): Flow<Letter?> = callbackFlow {
        val docRef = lettersCollection.document(letterId)
        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            trySend(snapshot?.toObject(Letter::class.java)).isSuccess
        }
        awaitClose { listener.remove() }
    }

    // --- ONLY ONE updateLetter FUNCTION ---
    override suspend fun updateLetter(letter: Letter) {
        lettersCollection.document(letter.idLetter).set(letter).await()
    }

    override suspend fun deleteLetter(letterId: String) {
        try {
            lettersCollection.document(letterId).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}