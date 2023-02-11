package com.utad.tema17.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRest {

    lateinit var service: ApiService

    val URL = "https://api.themoviedb.org/3/"
    val URL_IMAGES = "https://image.tmdb.org/t/p/w500"
    val api_key = "b604bb760239015c6b2a440bf8bd91fe" //metemos nuestra api key
    val language = "es-ESP"

    fun initService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ApiService::class.java)
    }
}