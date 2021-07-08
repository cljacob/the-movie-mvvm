package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices

class ImplementationRepo(private val dataSource: MovieServices) : Repository {

    override suspend fun getPopularsMovies(apiKey: String, page: Int): Resource<List<Movie>> {
        return dataSource.getPopularMovier(apiKey, page)
    }

    override suspend fun getDetailMovie(idMovie: Long, apiKey: String): Resource<DetailMovie> {
        return dataSource.getDetailMovie(idMovie, apiKey)
    }

    override suspend fun getVideoMovie(idMovie: Long, apiKey: String): Resource<VideoMovie> {
        return dataSource.getVideoMovie(idMovie, apiKey)
    }
}