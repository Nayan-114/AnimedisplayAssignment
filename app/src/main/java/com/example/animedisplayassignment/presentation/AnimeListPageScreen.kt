package com.example.animedisplayassignment.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.animedisplayassignment.presentation.state.AnimeListUiState
import com.example.animedisplayassignment.presentation.viewmodel.AnimeListViewModel

@Composable
fun AnimeListPageScreen(
    navController: NavController,
    viewModel: AnimeListViewModel = viewModel()
) {
    val state by viewModel.animeState
    when (state) {
        is AnimeListUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AnimeListUiState.Success -> {
            val animeList = (state as AnimeListUiState.Success).data
            LazyColumn {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Top Anime List",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
                items(animeList) { anime ->
                    AnimeCard(anime) {
                        navController.navigate("anime_detail/${anime.mal_id}")
                    }
                }
            }
        }

        is AnimeListUiState.Error -> {
            val message = (state as AnimeListUiState.Error).message
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: $message")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchTopAnime() }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}
