package com.example.animedisplayassignment.data.model

data class AnimeListResponse(
    val data: List<AnimeItem>
)

data class AnimeDetailResponse(
    val data: AnimeItem
)

data class AnimeItem(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val rating: String?,
    val images: Images,
    val trailer: Trailer?,
    val synopsis: String?,
    val score: Double?,
    val genres: List<Genre>?,
)

data class Images(
    val jpg: Jpg?
)

data class Jpg(
    val image_url: String?,
    val small_image_url: String?,
    val large_image_url: String?,
)

data class Genre(
    val name: String
)

data class Trailer(
    val youtube_id: String?,
    val url: String?,
    val images: Jpg,
    val embed_url: String?
)
