package com.example.lawyerapp.data.model


import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.*
import kotlinx.parcelize.Parcelize


@IgnoreExtraProperties
@Parcelize
data class Letter(
    val category: String = "",
    val date: String = "",
    val description: String = "",
    val fullName: String = "",
    val idLetter: String = "",
    val phoneNumber: String = "",
    val title: String = "",
    val userId: String = "",
    val email: String = "" ,
    val lawyerAnswer: String = ""
) : Parcelable