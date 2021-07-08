package co.jacobcloldev.apps.themoviecl.ui.viewmodel

import androidx.lifecycle.*
import co.jacobcloldev.apps.themoviecl.BuildConfig
import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.domain.Repository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repo: Repository): ViewModel() {

    private val page = MutableLiveData<Int>()
    private val idMovie = MutableLiveData<Long>()
    private val apiKey = BuildConfig.THE_MOVIE_API_KEY

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
                emit(repo.getPopularsMovies(apiKey, it))
            } catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }


    fun setIdMovie(idMovie: Long){
        this.idMovie.value =  idMovie
    }

    val fetchDetailMovie = idMovie.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getDetailMovie(it, apiKey))
            } catch (e: java.lang.Exception){
                emit(Resource.Failure(e))
            }
        }
    }


    val fetchVideoMovie = idMovie.distinctUntilChanged().switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getVideoMovie(it, apiKey))
            } catch (e: java.lang.Exception){
                emit(Resource.Failure(e))
            }
        }
    }

}