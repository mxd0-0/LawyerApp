package com.example.lawyerapp.presentation.requests_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerapp.domain.model.Letter
import com.example.lawyerapp.domain.use_case.DeleteLetterUseCase
import com.example.lawyerapp.domain.use_case.GetLettersUseCase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RequestsListViewModel : ViewModel() {

    private val getLettersUseCase = GetLettersUseCase()
    private val deleteLetterUseCase = DeleteLetterUseCase()

    private val _state = MutableStateFlow(RequestsListState())
    val state: StateFlow<RequestsListState> = _state.asStateFlow()

    init {
        getLetters()
    }

    private fun getLetters() {
        getLettersUseCase().onEach { letters ->
            _state.value = _state.value.copy(letters = letters)
        }.launchIn(viewModelScope)
    }

    fun onDeleteLetter(letter: Letter) {
        viewModelScope.launch {
            deleteLetterUseCase(letter.idLetter)
            // The list will auto-update because we are observing the flow
        }
    }
}