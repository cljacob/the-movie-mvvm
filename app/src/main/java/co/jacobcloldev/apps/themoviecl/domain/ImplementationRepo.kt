package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices

class ImplementationRepo(private val dataSource: MovieServices) : Repository {

    override suspend fun getPopularsMovies(apiClient: String, page: Int): Resource<List<Movie>> {
        return dataSource.getPopularMovier(apiClient, page)
    }
}