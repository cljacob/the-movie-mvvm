package co.jacobcloldev.apps.themoviecl.usecases

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import javax.inject.Inject

class SaveFavoriteMovieUseCase @Inject constructor(private val repository : ImplementationRepo) {
    suspend operator fun invoke(isFavorite: Boolean, idMovie: Long): Boolean{
        return try {
            repository.updateMovie(isFavorite, idMovie)
            true
        } catch (e: Exception){
            false
        }
    }
}