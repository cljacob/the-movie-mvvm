package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie

interface Repository {
    suspend fun getPopularsMovies(page: Int): List<Movie>

    suspend fun getDetailMovie(idMovie: Long): DetailMovie

    suspend fun getVideoMovie(idMovie: Long): VideoMovie

    suspend fun getMovieSaved(): List<Movie>

    suspend fun getMovieFavorite(): List<Movie>

    suspend fun insetMovie(movie: MovieEntity)

    suspend fun updateMovie(isFavorite: Boolean, idMovie: Long)
}