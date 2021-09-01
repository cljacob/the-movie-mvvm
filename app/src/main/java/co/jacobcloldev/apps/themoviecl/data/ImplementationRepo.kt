package co.jacobcloldev.apps.themoviecl.data

import co.jacobcloldev.apps.themoviecl.data.db.DBServices
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices
import co.jacobcloldev.apps.themoviecl.domain.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImplementationRepo @Inject constructor(
    private val localDataSource: DBServices,
    private val remoteDataSource: MovieServices
) : Repository {

    override suspend fun getMovieSaved(): List<Movie> {
        val listMovies = mutableListOf<Movie>()
        val data = localDataSource.getMovieSaved()
        for (item in data) {
            val movie = Movie(
                item.backdropPath,
                item.id,
                item.originalTitle,
                item.posterPath,
                item.voteAverage,
                item.voteCount
            )
            listMovies.add(movie)
        }
        return listMovies
    }

    override suspend fun getMovieFavorite(): List<Movie> {
        val listMovies = mutableListOf<Movie>()
        val data = localDataSource.getMovieFavorite()
        for (item in data) {
            val movie = Movie(
                item.backdropPath,
                item.id,
                item.originalTitle,
                item.posterPath,
                item.voteAverage,
                item.voteCount
            )
            listMovies.add(movie)
        }
        return listMovies
    }

    override suspend fun insetMovie(movie: MovieEntity) {
        localDataSource.saveMovieIntoRoom(movie)
    }

    override suspend fun updateMovie(isFavorite: Boolean, idMovie: Long) {
        localDataSource.updateMovieIntoRoom(isFavorite, idMovie)
    }

    override suspend fun getPopularsMovies(page: Int): List<Movie> {
        return remoteDataSource.getPopularMovier(page)
    }

    override suspend fun getDetailMovie(idMovie: Long): DetailMovie {
        return remoteDataSource.getDetailMovie(idMovie)
    }

    override suspend fun getVideoMovie(idMovie: Long): VideoMovie {
        return remoteDataSource.getVideoMovie(idMovie)
    }
}