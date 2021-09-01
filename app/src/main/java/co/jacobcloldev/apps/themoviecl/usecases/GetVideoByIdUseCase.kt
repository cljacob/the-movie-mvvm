package co.jacobcloldev.apps.themoviecl.usecases

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import javax.inject.Inject

class GetVideoByIdUseCase @Inject constructor(private val repository : ImplementationRepo) {
    suspend operator fun invoke(idMovie: Long): VideoMovie = repository.getVideoMovie(idMovie)
}