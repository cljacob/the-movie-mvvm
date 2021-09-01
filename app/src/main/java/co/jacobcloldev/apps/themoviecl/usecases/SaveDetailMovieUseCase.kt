package co.jacobcloldev.apps.themoviecl.usecases

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import javax.inject.Inject

class SaveDetailMovieUseCase @Inject constructor(private val repository : ImplementationRepo) {
    suspend operator fun invoke(movie: MovieEntity): Boolean{
        return try {
            repository.insetMovie(movie)
            true
        } catch (e: Exception){
            false
        }
    }
}