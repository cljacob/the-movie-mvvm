package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie

interface Repository {
    suspend fun getPopularsMovies(apiKey: String, page: Int): List<Movie>

    suspend fun getDetailMovie(idMovie: Long, apiKey: String): DetailMovie

    suspend fun getVideoMovie(idMovie: Long, apiKey: String): VideoMovie

    suspend fun getMovieSaved(): Resource<List<MovieEntity>>

    suspend fun getMovieFavorite(): Resource<List<MovieEntity>>

    suspend fun insetMovie(movie: MovieEntity)

    suspend fun updateMovie(isFavorite: Boolean, idMovie: Long)
}