package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.Movie

interface Repository {
    suspend fun getPopularsMovies(apiClient: String, page: Int): Resource<List<Movie>>
}