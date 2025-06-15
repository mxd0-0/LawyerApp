package com.example.lawyerapp.presentation.requests_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerapp.data.model.Letter
import com.example.lawyerapp.domain.use_case.DeleteLetterUseCase
import com.example.lawyerapp.domain.use_case.GetLettersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class RequestsListViewModel @Inject constructor(
    private val getLettersUseCase: GetLettersUseCase, // Inject the Use Case
    private val deleteLetterUseCase: DeleteLetterUseCase // Inject the Use Case
) : ViewModel() {

    private val _state = MutableStateFlow(RequestsListState())
    val state: StateFlow<RequestsListState> = _state.asStateFlow()

    init {
        getLetters()
    }

    private fun getLetters() {
        // The rest of the code is the same, just using the injected use cases
        getLettersUseCase().onEach { letters ->
            _state.value = _state.value.copy(letters = letters)
        }.launchIn(viewModelScope)
    }

    fun onDeleteLetter(letter: Letter) {
        viewModelScope.launch {
            deleteLetterUseCase(letter.idLetter)
        }
    }
}