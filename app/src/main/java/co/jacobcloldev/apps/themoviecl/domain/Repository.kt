package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie

interface Repository {
    suspend fun getPopularsMovies(apiKey: String, page: Int): Resource<List<Movie>>

    suspend fun getDetailMovie(idMovie: Long, apiKey: String): Resource<DetailMovie>

    suspend fun getVideoMovie(idMovie: Long, apiKey: String): Resource<VideoMovie>

}