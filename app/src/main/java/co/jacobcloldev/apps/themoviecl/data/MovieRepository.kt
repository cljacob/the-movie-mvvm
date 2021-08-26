package co.jacobcloldev.apps.themoviecl.data

import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices

class MovieRepository {

    private val api = MovieServices()

     suspend fun getPopularsMovies(apiKey: String, page: Int): List<Movie> {
        return api.getPopularMovier(apiKey, page)
    }

     suspend fun getDetailMovie(idMovie: Long, apiKey: String): DetailMovie {
        return api.getDetailMovie(idMovie, apiKey)
    }

     suspend fun getVideoMovie(idMovie: Long, apiKey: String): VideoMovie {
        return api.getVideoMovie(idMovie, apiKey)
    }

}