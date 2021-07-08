package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
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

    override suspend fun getMovieSaved(): Resource<List<MovieEntity>> {
        return dataSource.getMovieSaved()
    }

    override suspend fun getMovieFavorite(): Resource<List<MovieEntity>> {
        return dataSource.getMovieFavorite()
    }

    override suspend fun insetMovie(movie: MovieEntity) {
        dataSource.saveMovieIntoRoom(movie)
    }

    override suspend fun updateMovie(isFavorite: Boolean, idMovie: Long) {
        dataSource.updateMovieIntoRoom(isFavorite, idMovie)
    }
}