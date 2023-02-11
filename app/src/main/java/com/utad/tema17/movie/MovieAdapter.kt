package com.utad.tema17.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.utad.tema17.R
import com.utad.tema17.api.ApiRest
import com.utad.tema17.genres.GenresResponse

class MovieAdapter(private val data: ArrayList<MovieResponse.Movie>, val onClick:(MovieResponse.Movie)->Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val card = v.findViewById<CardView>(R.id.cardPeliculas)
        val image = v.findViewById<ImageView>(R.id.imagenPeliculas)

        fun bind(item: MovieResponse.Movie) {
            Picasso.get().load(ApiRest.URL_IMAGES + item.poster_path).into(image)

            card.setOnClickListener {
                //Log.v("Pulso sobre", item.id.toString())
                Log.v("Pulso sobre", item.title)
                onClick(item)
            }
        }
    }
}