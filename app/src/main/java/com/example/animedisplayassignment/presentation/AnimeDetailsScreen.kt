package com.example.animedisplayassignment.presentation

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animedisplayassignment.data.model.AnimeItem
import com.example.animedisplayassignment.data.model.Genre
import com.example.animedisplayassignment.presentation.state.AnimeDetailUiState
import com.example.animedisplayassignment.presentation.viewmodel.AnimeDetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnimeDetailScreen(
    animeId: Int,
    viewModel: AnimeDetailViewModel = viewModel()
) {
    val state by viewModel.animeState
    LaunchedEffect(animeId) {
        viewModel.fetchAnimeById(animeId)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        when (state) {
            is AnimeDetailUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AnimeDetailUiState.Success -> {
                val anime = (state as AnimeDetailUiState.Success).data
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    AnimeVideoCard(anime = anime)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Score: ${anime.score ?: "N/A"} | Episodes: ${anime.episodes ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    anime.rating?.let {
                        RatingBadge(it)
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    anime.genres?.let {
                        AnimeGenres(anime.genres)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = anime.synopsis ?: "No synopsis available",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            is AnimeDetailUiState.Error -> {
                Text(
                    text = "Error: ${(state as AnimeDetailUiState.Error).message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun AnimeGenres(
    genreList: List<Genre>
) {
    Text(
        text = "Genres",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genreList.take(3).forEach { it->
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = it.name,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun AnimeVideoCard(
    anime: AnimeItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        if (!anime.trailer?.embed_url.isNullOrBlank()) {
            anime.trailer.youtube_id?.let { videoId ->
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        val playerView = YouTubePlayerView(context).apply {
                            layoutParams = FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }
                        (context as LifecycleOwner).lifecycle.addObserver(playerView)
                        playerView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(player: YouTubePlayer) {
                                player.cueVideo(videoId, 0f)
                            }
                        })
                        playerView
                    }
                )
            }
        } else {
            AsyncImage(
                model = anime.images.jpg?.large_image_url,
                contentDescription = anime.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun RatingBadge(rating: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color.LightGray, // Light Blue background
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = rating,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.DarkGray, // Deep Blue text
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}
