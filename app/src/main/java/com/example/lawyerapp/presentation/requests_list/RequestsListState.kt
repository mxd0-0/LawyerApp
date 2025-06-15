package com.example.lawyerapp.presentation.requests_list

import com.example.lawyerapp.data.model.Letter

data class RequestsListState(
    val letters: List<Letter> = emptyList(),
    val isLoading: Boolean = false
)