package com.example.lawyerapp.presentation.request_detail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lawyerapp.data.model.Letter
import com.example.lawyerapp.data.repository.LetterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RequestDetailState(
    val letter: Letter? = null,
    val isLoading: Boolean = true, // <-- THIS IS THE MISSING PROPERTY
    val isSaving: Boolean = false
)

@HiltViewModel
class RequestDetailViewModel @Inject constructor(
    private val repository: LetterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(RequestDetailState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<String>("letterId")?.let { letterId ->
            repository.getLetterById(letterId).onEach { letter ->
                _state.value = _state.value.copy(letter = letter, isLoading = false)
            }.launchIn(viewModelScope)
        }
    }

    fun updateLawyerAnswer(answer: String) {
        _state.value.letter?.let { currentLetter ->
            // Update the local state immediately for a responsive UI
            val updatedLetter = currentLetter.copy(lawyerAnswer = answer)
            _state.value = _state.value.copy(letter = updatedLetter)
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            _state.value.letter?.let { letterToSave ->
                _state.value = _state.value.copy(isSaving = true)
                repository.updateLetter(letterToSave)
                _state.value = _state.value.copy(isSaving = false)
            }
        }
    }
}