package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity

interface Repository {

    suspend fun getMovieSaved(): List<Movie>

    suspend fun getMovieFavorite(): List<Movie>

    suspend fun insetMovie(movie: MovieEntity)

    suspend fun updateMovie(isFavorite: Boolean, idMovie: Long)
}