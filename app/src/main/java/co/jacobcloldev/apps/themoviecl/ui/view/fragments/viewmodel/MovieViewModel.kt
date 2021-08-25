package co.jacobcloldev.apps.themoviecl.ui.view.fragments.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.domain.GetFavoriteMovieUseCase
import co.jacobcloldev.apps.themoviecl.domain.GetMoviesUseCase
import co.jacobcloldev.apps.themoviecl.domain.GetSaveMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel @Inject constructor(val getMoviesUseCase : GetMoviesUseCase) : ViewModel() {

    private var page = 1

    private lateinit var context: Context

    val movie = MutableLiveData<List<Movie>>()
    val isLoading = MutableLiveData<Boolean>()

    fun setPage(page: Int){
        this.page = page
    }

    fun movieByPage() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getMoviesUseCase(page)

            if (!result.isNullOrEmpty()){
                movie.postValue(result)
            }
            isLoading.postValue(false)
        }
    }

    fun setContext(context: Context){
        this.context = context
    }

    private var getFavoriteMovie = GetFavoriteMovieUseCase(context)

    fun favoriteMovies(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getFavoriteMovie()
            if (result.isNotEmpty()){
                movie.postValue(result)
            }
            isLoading.postValue(false)
        }
    }

    private var getSavedMovies = GetSaveMoviesUseCase(context)

    fun savedMovies(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getSavedMovies()
            if (result.isNotEmpty()){
                movie.postValue(result)
            }
            isLoading.postValue(false)
        }
    }

}