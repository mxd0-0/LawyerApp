package com.example.lawyerapp.di


import com.example.lawyerapp.data.repository.LawyerRepository
import com.example.lawyerapp.data.repository.LawyerRepositoryImpl
import com.example.lawyerapp.data.repository.LetterRepository
import com.example.lawyerapp.data.repository.LetterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideLetterRepository(
        auth: FirebaseAuth
    ): LetterRepository {
        return LetterRepositoryImpl(auth)
    }

    @Provides
    @Singleton
    fun provideLawyerRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): LawyerRepository {
        return LawyerRepositoryImpl(auth, firestore)
    }
}
