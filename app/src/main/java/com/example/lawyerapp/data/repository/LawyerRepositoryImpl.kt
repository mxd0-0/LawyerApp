package com.example.lawyerapp.data.repository

import com.example.lawyerapp.data.model.Lawyer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LawyerRepositoryImpl(auth1: FirebaseAuth, firestore1: FirebaseFirestore) : LawyerRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val collection = firestore.collection("lawyers")

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: return Result.failure(Exception("UID not found"))

            val lawyer = Lawyer(fullName = fullName, email = email)
            firestore.collection("lawyers").document(uid).set(lawyer).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileInformation(): Result<Lawyer> {
        val uid = auth.currentUser?.uid ?: return Result.failure(Exception("User not logged in"))

        return try {
            val snapshot = collection.document(uid).get().await()
            val lawyer = snapshot.toObject(Lawyer::class.java)
                ?: return Result.failure(Exception("Lawyer profile not found"))
            Result.success(lawyer)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
