package com.example.flixsterclone

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flixsterclone.view_holders.MovieViewHolder
import com.example.flixsterclone.view_holders.MovieViewHolderBackdropOnly

private const val TAG = "MovieAdapter"
class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val popularViewType = 0
    private val lessPopularViewType = 1

    override fun getItemViewType(position: Int): Int {
        return if (movies[position].isPopular()) popularViewType else lessPopularViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val viewHolder = when (viewType) {
            popularViewType -> {
                val view = inflater.inflate(R.layout.item_movie_backdrop_only, parent, false)
                MovieViewHolderBackdropOnly(view)
            }
            lessPopularViewType -> {
                val view = inflater.inflate(R.layout.item_movie, parent, false)
                MovieViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_movie, parent, false)
                MovieViewHolder(view)
            }
        }

        return viewHolder
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = movies[position]
//        holder.bind(movie)

        when (holder.itemViewType) {
            popularViewType -> {
                val movieViewHolderBackdropOnly = holder as MovieViewHolderBackdropOnly
                movieViewHolderBackdropOnly.bindMovie(context, movie)
            }
            lessPopularViewType -> {
                val movieViewHolder = holder as MovieViewHolder
                movieViewHolder.bindMovie(context, movie)
            }
            else -> {
                val movieViewHolder = holder as MovieViewHolder
                movieViewHolder.bindMovie(context, movie)
            }
        }
    }

}
