package com.example.flixsterclone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixsterclone.Movie
import com.example.flixsterclone.MovieAdapter
import com.example.flixsterclone.R
import com.example.flixsterclone.databinding.ActivityMovieListBinding
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "MainActivity"
private const val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

class MovieListActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private lateinit var rvMovies: RecyclerView
    // Store the binding
    private lateinit var binding: ActivityMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the content view (replacing `setContentView`)
//        setContentView(R.layout.activity_movie_list)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)

        rvMovies = binding.rvMovies
        val movieAdapter = MovieAdapter(this, movies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)
//        // Animators
//        rvMovies.itemAnimator = SlideInUpAnimator()

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: JSON data: $json")
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movie list: $movies")
                } catch (e: JSONException) {
                    Log.e(TAG, "Encountered exception: $e")
                }
            }

        })
    }
}