package com.utad.tema17.api


import com.utad.tema17.genres.GenresResponse
import com.utad.tema17.movie.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language
    ): Call<GenresResponse>

    @GET("discover/movie")
    fun getMovies(
        @Query("with_genres") with_genres: String,
        @Query("api_key") apikey: String = ApiRest.api_key,
        @Query("language") language: String = ApiRest.language
    ): Call<MovieResponse>
}
