package co.jacobcloldev.apps.themoviecl.data.db

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity

class DBServices(private val appDataBase: AppDataBase) {
    suspend fun saveMovieIntoRoom(movie: MovieEntity){
        appDataBase.movieDao().insertMovie(movie)
    }

    suspend fun updateMovieIntoRoom(isFavorite: Boolean, idMovie: Long){
        appDataBase.movieDao().updateMovie(isFavorite, idMovie)
    }

    suspend fun getMovieSaved(): Resource<List<MovieEntity>> {
        return Resource.Success(appDataBase.movieDao().getSavedMovies())
    }

    suspend fun getMovieFavorite(): Resource<List<MovieEntity>> {
        return Resource.Success(appDataBase.movieDao().getGetFavoritesMovies())
    }
}