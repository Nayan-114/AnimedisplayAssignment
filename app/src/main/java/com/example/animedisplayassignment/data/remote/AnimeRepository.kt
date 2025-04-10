package com.example.animedisplayassignment.data.remote

import com.example.animedisplayassignment.data.model.AnimeItem

class AnimeRepository {
    suspend fun getAnimeList(): List<AnimeItem> {
        val response = RetrofitInstance.animeApi.getTopAnime()
        if(response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body.data
            } else throw Exception("Empty response body")
        } else {
            throw Exception("Error ${response.code()} ${response.message()}")
        }
    }

    suspend fun getAnimeDetail(id: Int): AnimeItem {
        val response = RetrofitInstance.animeApi.getAnimeDetail(id)
        if(response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return body.data
            } else throw Exception("Empty response body")
        } else {
            throw Exception("Error ${response.code()} ${response.message()}")
        }
    }
}
