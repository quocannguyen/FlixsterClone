package com.example.flixsterclone.activities

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixsterclone.Movie
import com.example.flixsterclone.R
import com.example.flixsterclone.databinding.ActivityMovieDetailBinding
import com.example.flixsterclone.databinding.ActivityMovieListBinding
import com.example.flixsterclone.view_holders.MOVIE_EXTRA
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import okhttp3.Headers
import java.lang.Exception

private const val TAG = "MovieDetailActivity"
private val YOUTUBE_DATA_API_KEY = Resources.getSystem().getString(R.string.youtube_data_api_key)
private const val TRAILER_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MovieDetailActivity : YouTubeBaseActivity() {
    private lateinit var ytPlayerView: YouTubePlayerView
    // Store the binding
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the content view (replacing `setContentView`)
//        setContentView(R.layout.activity_movie_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)

        ytPlayerView = findViewById(R.id.player)

        // Create or access the data to bind
        val movie = intent.getParcelableExtra<Movie>(MOVIE_EXTRA) as Movie
        // Attach the user to the binding
        binding.movie = movie

        val client = AsyncHttpClient()
        client.get(TRAILER_URL.format(movie.movieId), object: JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?
            ) {
                Log.e(TAG, "onFailure: $statusCode")
                Log.e("movieId", "onFailure: ${movie.movieId}", )
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "onSuccess: ")
                val results = json.jsonObject.getJSONArray("results")
                if (results.length() == 0) {
                    Log.w(TAG, "onSuccess: No movie trailers found", )
                    return
                }
                for (i in 0 until results.length()) {
                    val movieTrailerJson = results.getJSONObject(i)
                    val site = movieTrailerJson.getString("site")
                    if (site == "YouTube") {
                        // Play the Youtube video with this trailer
                        val youtubeKey = movieTrailerJson.getString("key")
                        initializeYoutube(youtubeKey, movie)
                        break
                    }
                }
            }

        })
    }

    private fun initializeYoutube(youtubeKey: String, movie: Movie) {

        ytPlayerView.initialize(YOUTUBE_DATA_API_KEY, object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider?,
                youTubePlayer: YouTubePlayer,
                p2: Boolean
            ) {
                if (movie.isPopular())
                    youTubePlayer.loadVideo(youtubeKey)
                else
                    youTubePlayer.cueVideo(youtubeKey)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.e(TAG, "onInitializationFailure: ${p1.toString()}", )
            }

        })
    }
}