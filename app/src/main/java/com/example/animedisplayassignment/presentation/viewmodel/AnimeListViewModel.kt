package com.example.animedisplayassignment.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animedisplayassignment.data.remote.AnimeRepository
import com.example.animedisplayassignment.presentation.state.AnimeListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val repository: AnimeRepository = AnimeRepository()
) : ViewModel() {

    private val _animeState = mutableStateOf<AnimeListUiState>(AnimeListUiState.Loading)
    val animeState: State<AnimeListUiState> = _animeState

    init {
        fetchTopAnime()
    }

    fun fetchTopAnime() {
        viewModelScope.launch(Dispatchers.IO) {
            _animeState.value = try {
                val data = repository.getAnimeList()
                AnimeListUiState.Success(data)
            } catch (e: Exception) {
                AnimeListUiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}