package com.example.lawyerapp.di


import com.example.lawyerapp.data.repository.LetterRepository
import com.example.lawyerapp.data.repository.LetterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.ktx.Firebase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLetterRepository(
        auth: FirebaseAuth // Ask Hilt to provide FirebaseAuth
    ): LetterRepository {
        return LetterRepositoryImpl(auth) // Pass it to the repository
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}