package co.jacobcloldev.apps.themoviecl.ui.viewmodel

import androidx.lifecycle.*
import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.domain.Repository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repo: Repository): ViewModel() {

    private val page = MutableLiveData<Int>()

    fun setPage(page: Int){
        this.page.value = page
    }

    init {
        setPage(1)
    }

    val fetchPopularMoviesList = page.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                val apiKey = "80b9f3fb17b1fca2b325bf91ad96ff9d"
                emit(repo.getPopularsMovies(apiKey, it))
            } catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }
}