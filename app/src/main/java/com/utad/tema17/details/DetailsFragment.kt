package com.utad.tema17.details

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import com.utad.tema17.R
import com.utad.tema17.api.ApiRest
import com.utad.tema17.movie.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsFragment : Fragment() {

    var data: ArrayList<MovieResponse.Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieAgent =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {

                arguments?.getSerializable("movie", MovieResponse.Movie::class.java)

            } else {

                arguments?.getSerializable("movie") as? MovieResponse.Movie

            }

        (activity as? AppCompatActivity)?.supportActionBar?.title = ""


        val tituloPelicula = view.findViewById<TextView>(R.id.textTituloInfoPeliculas)
                tituloPelicula.text = movieAgent?.title

        val image = view.findViewById<ImageView>(R.id.imagenInfoPeliculas)
        Picasso.get().load(ApiRest.URL_IMAGES + movieAgent?.poster_path).into(image)

        val descripcionPelicula = view.findViewById<TextView>(R.id.textDescripcionInfoPeliculas)
        descripcionPelicula.text = movieAgent?.overview


    }


}
