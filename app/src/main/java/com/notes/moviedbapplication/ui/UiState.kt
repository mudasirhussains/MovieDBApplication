package com.notes.moviedbapplication.ui

sealed class UiState

object LoadingState:UiState()
object UnloadingState:UiState()
class ErrorState(val message: String) : UiState()
object EmptyState : UiState()