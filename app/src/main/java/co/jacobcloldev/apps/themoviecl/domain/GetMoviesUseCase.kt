package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.data.MovieRepository
import co.jacobcloldev.apps.themoviecl.data.model.Movie

class GetMoviesUseCase {

    private val repository = MovieRepository()

    suspend operator fun invoke(apiKey: String, page: Int): List<Movie> = repository.getPopularsMovies(apiKey, page)

}