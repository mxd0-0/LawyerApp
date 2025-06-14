package com.example.lawyerapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Letter(
    val idLetter: String = "",
    val category: String = "Consult",
    val date: String = "",
    val description: String = "",
    val fullName: String = "",
    val email: String = "example@gmail.com", // Added for detail screen
    val phoneNumber: String = "",
    val title: String = "",
    val userId: String = ""
) : Parcelable
