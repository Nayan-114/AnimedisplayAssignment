package com.example.animedisplayassignment.presentation.state

import com.example.animedisplayassignment.data.model.AnimeItem

sealed class AnimeListUiState {
    object Loading : AnimeListUiState()
    data class Success(val data: List<AnimeItem>) : AnimeListUiState()
    data class Error(val message: String) : AnimeListUiState()
}

sealed class AnimeDetailUiState {
    object Loading : AnimeDetailUiState()
    data class Success(val data: AnimeItem) : AnimeDetailUiState()
    data class Error(val message: String) : AnimeDetailUiState()
}
