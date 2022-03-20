package com.example.flixsterclone.view_holders

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flixsterclone.Movie
import com.example.flixsterclone.activities.MovieDetailActivity

const val MOVIE_EXTRA = "movie_extra"
private const val TAG = "BaseMovieViewHolder"
abstract class BaseMovieViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var context: Context
    lateinit var movie: Movie

    // Glide Transformations - Rounded Corners
    val radius = 30 // corner radius, higher value = more rounded
    val margin = 10 // crop margin, set to 0 for corners with no crop

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // 2. Use the Intent system to navigate to the new Activity
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, movie)
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            val sceneTransition = getSceneTransition()
            context.startActivity(intent, sceneTransition.toBundle())
        } else {
            // Swap without transition
            context.startActivity(intent)
        }

    }
    abstract fun getSceneTransition() : ActivityOptionsCompat

    fun bindMovie(context: Context, movie: Movie) {
        this.context = context
        this.movie = movie
        populateViewHolder()
    }
    abstract fun populateViewHolder()
}