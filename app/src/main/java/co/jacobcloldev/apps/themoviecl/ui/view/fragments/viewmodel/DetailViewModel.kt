package co.jacobcloldev.apps.themoviecl.ui.view.fragments.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import co.jacobcloldev.apps.themoviecl.domain.GetMovieByIdUseCase
import co.jacobcloldev.apps.themoviecl.domain.GetVideoByIdUseCase
import co.jacobcloldev.apps.themoviecl.domain.SaveDetailMovieUseCase
import co.jacobcloldev.apps.themoviecl.domain.SaveFavoriteMovieUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(val getDetailMovieUseCase: GetMovieByIdUseCase,
                                          val getVideoByMovie: GetVideoByIdUseCase,
                                          val savedMovie: SaveDetailMovieUseCase,
                                          val  updateMovie: SaveFavoriteMovieUseCase) : ViewModel() {

    private var idMovie: Long = 0
    private lateinit var context: Context

    val isLoading = MutableLiveData<Boolean>()
    val detailMovie = MutableLiveData<DetailMovie>()
    val videMovieUrl = MutableLiveData<VideoMovie>()

    fun setIdMovie(idMovie: Long) {
        this.idMovie = idMovie
    }

    fun fetchDetailMovie() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getDetailMovieUseCase(idMovie)

            if (result.id != 0L) {
                detailMovie.postValue(result)
            }
            isLoading.postValue(false)
        }
    }


    fun fetchVideoMovie(){
       viewModelScope.launch {
           isLoading.postValue(true)
           val result = getVideoByMovie(idMovie)
           if (result.id != 0L){
                videMovieUrl.postValue(result)
           }
           isLoading.postValue(false)
        }
    }

    fun setContext(context: Context){
        this.context = context
    }

    fun saveMovie(movie: MovieEntity){
        viewModelScope.launch {
            isLoading.postValue(true)
           val result = savedMovie(movie)
           if (result){
               isLoading.postValue(false)
           }
        }
    }

    fun updataMovie(isFavorite: Boolean, idMovie: Long){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = updateMovie(isFavorite, idMovie)
            if (result){
                isLoading.postValue(false)
            }
        }
    }

}