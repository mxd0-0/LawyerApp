package com.example.lawyerapp.data.model


import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.*
import kotlinx.parcelize.Parcelize

// This annotation helps Firestore ignore any fields in the document
// that aren't in your data class, preventing crashes.
@IgnoreExtraProperties
@Parcelize
data class Letter(
    // It's good practice to provide default values for all properties.
    // This prevents crashes if a document is missing a field.
    val category: String = "",
    val date: String = "",
    val description: String = "",
    val fullName: String = "",
    val idLetter: String = "",
    val phoneNumber: String = "",
    val title: String = "",
    val userId: String = "",
    val email: String = "" // Keep this for the detail screen UI
) : Parcelable