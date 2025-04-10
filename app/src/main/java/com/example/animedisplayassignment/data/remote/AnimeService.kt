package com.example.animedisplayassignment.data.remote

import com.example.animedisplayassignment.data.model.AnimeDetailResponse
import com.example.animedisplayassignment.data.model.AnimeListResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeService {
    @GET("top/anime")
    suspend fun getTopAnime(): Response<AnimeListResponse>

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): Response<AnimeDetailResponse>
}

object RetrofitInstance {
    val animeApi: AnimeService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeService::class.java)
    }
}
