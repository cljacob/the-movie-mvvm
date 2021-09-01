package co.jacobcloldev.apps.themoviecl.usecases

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(private val repository : ImplementationRepo) {
    suspend operator fun invoke(idMovie: Long): DetailMovie = repository.getDetailMovie(idMovie)
}