package co.jacobcloldev.apps.themoviecl.ui.view.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.jacobcloldev.apps.themoviecl.BuildConfig
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.domain.GetMoviesUseCase
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movie = MutableLiveData<List<Movie>>()
    private var page = 1
    private val apiKey = BuildConfig.THE_MOVIE_API_KEY
    private val isLoading = MutableLiveData<Boolean>()

    var getMoviesUseCase = GetMoviesUseCase()

    fun setPage(page: Int){
        this.page = page
    }

    fun onCreate() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMoviesUseCase(apiKey, page)

            if (!result.isNullOrEmpty()){
                movie.postValue(result)
                isLoading.postValue(false)
            }
        }
    }
}