package co.jacobcloldev.apps.themoviecl.usecases

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import javax.inject.Inject

class GetFavoriteMovieUseCase @Inject constructor( private val implementationRepo : ImplementationRepo) {
    suspend operator fun invoke() : List<Movie> {
        val repo = implementationRepo.getMovieFavorite()
        return if (!repo.isNullOrEmpty()){
            repo
        } else {
            emptyList()
        }
    }

}