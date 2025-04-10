package com.example.animedisplayassignment.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animedisplayassignment.data.remote.AnimeRepository
import com.example.animedisplayassignment.presentation.state.AnimeDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val repository: AnimeRepository = AnimeRepository()
) : ViewModel() {

    private val _animeState = mutableStateOf<AnimeDetailUiState>(AnimeDetailUiState.Loading)
    val animeState: State<AnimeDetailUiState> = _animeState

    fun fetchAnimeById(animeId: Int) {
        if (_animeState.value !is AnimeDetailUiState.Success) {
            viewModelScope.launch(Dispatchers.IO) {
                _animeState.value = AnimeDetailUiState.Loading
                try {
                    val anime = repository.getAnimeDetail(animeId)
                    _animeState.value = AnimeDetailUiState.Success(anime)
                } catch (e: Exception) {
                    _animeState.value =
                        AnimeDetailUiState.Error(e.localizedMessage ?: "Unknown error")
                }
            }
        }
    }
}