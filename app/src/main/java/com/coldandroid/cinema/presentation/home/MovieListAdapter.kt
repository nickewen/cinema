package com.coldandroid.cinema.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coldandroid.cinema.databinding.ViewMovieBinding
import com.coldandroid.cinema.presentation.ext.loadFromUrl


class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private var movies: Array<MovieUI> = emptyArray()

    fun setMovies(newMovies: List<MovieUI>) {
        movies = newMovies.toTypedArray()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val viewBinding = ViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = movies.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie =  movies[position]
        holder.title.text = movie.title
        holder.description.text = movie.description
        holder.poster.loadFromUrl(IMAGE_BASE_URL.plus(movie.posterUrl))
    }

    class MovieViewHolder(viewMovieBinding: ViewMovieBinding) : RecyclerView.ViewHolder(viewMovieBinding.root) {
        val title = viewMovieBinding.title
        val description = viewMovieBinding.description
        val poster = viewMovieBinding.poster
    }

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w185_and_h278_bestv2"
    }
}