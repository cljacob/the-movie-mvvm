package co.jacobcloldev.apps.themoviecl.usecases


import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository : ImplementationRepo) {
    suspend operator fun invoke(page: Int): List<Movie> = repository.getPopularsMovies(page)
}