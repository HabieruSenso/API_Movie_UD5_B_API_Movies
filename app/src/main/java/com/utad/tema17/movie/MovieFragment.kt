package com.utad.tema17.movie

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.utad.tema17.R
import com.utad.tema17.api.ApiRest
import com.utad.tema17.details.DetailsFragment
import com.utad.tema17.genres.GenresResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {

    private  var swipeRefresh: SwipeRefreshLayout? = null
    private lateinit var rvPeliculas: RecyclerView

    private var adapter: MovieAdapter? = null
    var data: ArrayList<MovieResponse.Movie> = ArrayList()

    private var loader: View? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvPeliculas= view.findViewById<RecyclerView>(R.id.rvPeliculas)
        swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

/////////////////////
        // to do esto de abajos para el titulo

        val movieAgent =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {

                arguments?.getSerializable("genre", GenresResponse.Genre::class.java)

            } else {

                arguments?.getSerializable("genre") as? GenresResponse.Genre

            }

        (activity as? AppCompatActivity)?.supportActionBar?.title = movieAgent?.name


        // ESTO ES ALGO
        movieAgent?.id.toString()
        // Esto se acab ese algo
        Log.i("pepa", movieAgent.toString());
        //aqui acabo lo de la movida
        //////////////


       // rvPeliculas= view.findViewById<RecyclerView>(R.id.rvPeliculas)
        //swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        // loader = findViewById<View>(R.id.loader)

        //Esto hace que vaya de un fragment a otro y que inforamcion muestar

        val mLayoutManager = GridLayoutManager(context, 2)
        rvPeliculas.layoutManager = mLayoutManager
        adapter = MovieAdapter(data){ movie->

            activity?.let{


                val fragment = DetailsFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("movie",movie)


                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.container,fragment).commit()
            }

        }

//aqui se acaba

        rvPeliculas.adapter = adapter


        ApiRest.initService()
        //  Aquí hago la petición al servicio.
        getMovies( movieAgent?.id.toString())

        swipeRefresh?.setOnRefreshListener {
            getMovies( movieAgent?.id.toString())
        }
    }

   private fun getMovies(id: String) {
                    val call = ApiRest.service.getMovies(id)

                    call.enqueue(object : Callback<MovieResponse> {
                        override fun onResponse(
                            call: Call<MovieResponse>,
                            response: Response<MovieResponse>
                        ) {
                            val body = response.body()

                            if (response.isSuccessful && body != null) {
                                data.clear()
                                data.addAll(body.results)
                                Log.d(TAG, data.toString())
                                adapter?.notifyDataSetChanged()
                                // Imprimir aqui el listado con logs
                            } else {
                                Log.e(TAG, response.errorBody()?.string() ?: "Error")
                            }
                            swipeRefresh?.isRefreshing = false

                        }

                        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                            Log.e(TAG, t.message.toString())
                            swipeRefresh?.isRefreshing
            }
        })
    }
}