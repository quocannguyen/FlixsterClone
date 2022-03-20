package com.example.flixsterclone.view_holders

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.media.Image
import androidx.core.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixsterclone.Movie
import com.example.flixsterclone.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MovieViewHolder(itemView: View) : BaseMovieViewHolder(itemView) {

    private val ivPoster: ImageView = itemView.findViewById(R.id.ivPoster)
    private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    private val tvOverview: TextView = itemView.findViewById(R.id.tvOverview)

    override fun getSceneTransition() : ActivityOptionsCompat {
        val activity = context as Activity
        val visualPair = Pair(ivPoster as View, "visual")
        val titlePair = Pair(tvTitle as View, "title")
        val overviewPair = Pair(tvOverview as View, "overview")
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, visualPair, titlePair, overviewPair)
    }

    override fun populateViewHolder() {
        tvTitle.text = movie.title
        tvOverview.text = movie.overview

        val imageUrl = when (context.resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> movie.posterImageUrl
            Configuration.ORIENTATION_LANDSCAPE -> movie.backdropImageUrl
            else -> ""
        }

        Glide.with(context)
            .load(imageUrl)
//            .centerCrop() // scale image to fill the entire ImageView
            .transform(RoundedCornersTransformation(radius, margin))
            .placeholder(R.drawable.placeholder)
            .into(ivPoster)
    }
}