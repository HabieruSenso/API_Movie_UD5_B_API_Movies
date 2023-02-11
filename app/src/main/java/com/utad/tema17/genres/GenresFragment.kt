package com.utad.tema17.genres

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
import com.utad.tema17.movie.MovieFragment
import com.utad.tema17.R
import com.utad.tema17.api.ApiRest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GenresFragment : Fragment() {
    val TAG = "MainActivity "

    private  var swipeRefresh: SwipeRefreshLayout? = null
    private lateinit var rvGeneros: RecyclerView

    private var adapter: GenresAdapter? = null
    var data: ArrayList<GenresResponse.Genre> = ArrayList()

    private var loader : View?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Nteflix"

        rvGeneros= view.findViewById<RecyclerView>(R.id.rvGeneros)
        swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        // loader = findViewById<View>(R.id.loader)



        val mLayoutManager = GridLayoutManager(context, 2)
        rvGeneros.layoutManager = mLayoutManager
        adapter = GenresAdapter(data){ genre->

            activity?.let{


                val fragment = MovieFragment()
                fragment.arguments = Bundle()
                fragment.arguments?.putSerializable("genre",genre)


                it.supportFragmentManager.beginTransaction().addToBackStack(null)
                    .replace(R.id.container,fragment).commit()
            }

        }
        rvGeneros.adapter = adapter


        ApiRest.initService()
        //  Aquí hago la petición al servicio.
        getGenres()

        swipeRefresh?.setOnRefreshListener {
            getGenres()
        }

    }

    private fun getGenres() {
        val call = ApiRest.service.getGenres()
        call.enqueue(object : Callback<GenresResponse> {
            override fun onResponse(
                call: Call<GenresResponse>,
                response: Response<GenresResponse>
            ) {
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    data.clear()
                    data.addAll(body.genres)
                    Log.d(TAG, data.toString())
                    adapter?.notifyDataSetChanged()
                    // Imprimir aqui el listado con logs
                } else {
                    Log.e(TAG, response.errorBody()?.string() ?: "Error")
                }
                swipeRefresh?.isRefreshing = false

            }

            override fun onFailure(call: Call<GenresResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                swipeRefresh?.isRefreshing
            }
        })
    }
}