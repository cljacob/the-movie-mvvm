package co.jacobcloldev.apps.themoviecl.ui.view.fragments.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import co.jacobcloldev.apps.themoviecl.di.ActivityScope
import co.jacobcloldev.apps.themoviecl.usecases.GetMovieByIdUseCase
import co.jacobcloldev.apps.themoviecl.usecases.GetVideoByIdUseCase
import co.jacobcloldev.apps.themoviecl.usecases.SaveDetailMovieUseCase
import co.jacobcloldev.apps.themoviecl.usecases.SaveFavoriteMovieUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class DetailViewModel @Inject constructor(val getDetailMovieUseCase: GetMovieByIdUseCase,
                                          val getVideoByMovie: GetVideoByIdUseCase,
                                          val savedMovie: SaveDetailMovieUseCase,
                                          val  updateMovie: SaveFavoriteMovieUseCase
) : ViewModel() {

    private var idMovie: Long = 0

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