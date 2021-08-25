package co.jacobcloldev.apps.themoviecl.domain

import android.content.Context
import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.model.Movie

class GetSaveMoviesUseCase(context: Context) {

    private val implementationRepo = ImplementationRepo(context)

    suspend operator fun invoke() : List<Movie> {
        val repo = implementationRepo.getMovieSaved()
        return if (!repo.isNullOrEmpty()){
            repo
        } else {
            emptyList()
        }
    }
}