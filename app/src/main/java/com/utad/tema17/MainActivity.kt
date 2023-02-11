package com.utad.tema17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import com.utad.tema17.genres.GenresFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager?.beginTransaction()?.replace(R.id.container, GenresFragment())
            ?.commit()
    }
    /*private fun getMovies() {
        val call = ApiRest.service.getMovies("Genro pulsado por el usuario")
    }
*/
}