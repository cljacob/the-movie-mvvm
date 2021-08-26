package co.jacobcloldev.apps.themoviecl.domain

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import javax.inject.Inject

class GetSaveMoviesUseCase @Inject constructor(private val implementationRepo: ImplementationRepo) {

    suspend operator fun invoke() : List<Movie> {
        val repo = implementationRepo.getMovieSaved()
        return if (!repo.isNullOrEmpty()){
            repo
        } else {
            emptyList()
        }
    }
}