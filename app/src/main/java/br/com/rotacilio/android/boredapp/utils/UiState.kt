package br.com.rotacilio.android.boredapp.utils

interface UiState {
    object Empty : UiState
    object Loading : UiState
    data class Error(val code: Int, val message: String) : UiState
}