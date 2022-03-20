package com.example.flixsterclone.view_holders

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixsterclone.Movie
import com.example.flixsterclone.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class MovieViewHolderBackdropOnly(itemView: View) : BaseMovieViewHolder(itemView) {

    private val ivBackdrop: ImageView = itemView.findViewById(R.id.ivBackdrop)

    override fun getSceneTransition(): ActivityOptionsCompat {
        val activity = context as Activity
        val visualPair = Pair(ivBackdrop as View, "visual")
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, visualPair)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun populateViewHolder() {
        val imageUrl = movie.backdropImageUrl

        Glide.with(context)
            .load(imageUrl)
//            .centerCrop() // scale image to fill the entire ImageView
            .transform(RoundedCornersTransformation(radius, margin))
            .placeholder(R.drawable.placeholder)
            .into(ivBackdrop)
    }
}