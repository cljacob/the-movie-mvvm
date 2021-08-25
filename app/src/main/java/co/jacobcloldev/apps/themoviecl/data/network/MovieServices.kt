package co.jacobcloldev.apps.themoviecl.data.network

import co.jacobcloldev.apps.themoviecl.BuildConfig
import co.jacobcloldev.apps.themoviecl.core.RetrofitClient
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieServices {

    private val retrofit = RetrofitClient.getRetrofit()

    private val apiKey = BuildConfig.THE_MOVIE_API_KEY

    suspend fun getPopularMovier(page: Int) : List<Movie> {
        //return Resource.Success(RetrofitClient.webservice.getPopularMovies(apiClient, page).results)
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getPopularMovies(apiKey, page)
            response.body() ?: emptyList()
        }
    }

    suspend fun getDetailMovie(idMovie: Long) : DetailMovie {
       // return Resource.Success(RetrofitClient.webservice.getDetailMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getDetailMovie(idMovie, apiKey)
            response.body() ?: DetailMovie(0, "", 0, "", "", "",
                "", "", false, 0.0, "", 0)
        }
    }

    suspend fun getVideoMovie(idMovie: Long) : VideoMovie{
       // return Resource.Success(RetrofitClient.webservice.getVideoMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getVideoMovie(idMovie, apiKey)
            response.body() ?: VideoMovie(0, emptyList())
        }
    }
}