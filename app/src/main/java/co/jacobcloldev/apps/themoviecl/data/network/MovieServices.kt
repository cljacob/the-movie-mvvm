package co.jacobcloldev.apps.themoviecl.data.network

import co.jacobcloldev.apps.themoviecl.core.RetrofitClient
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieServices {

    private val retrofit = RetrofitClient.getRetrofit()

    suspend fun getPopularMovier(apiClient: String, page: Int) : List<Movie> {
        //return Resource.Success(RetrofitClient.webservice.getPopularMovies(apiClient, page).results)
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getPopularMovies(apiClient, page)
            response.body() ?: emptyList()
        }
    }

    suspend fun getDetailMovie(idMovie: Long, apiClient: String) : DetailMovie {
       // return Resource.Success(RetrofitClient.webservice.getDetailMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getDetailMovie(idMovie, apiClient)
            response.body() ?: DetailMovie(0, "", 0, "", "", "",
                "", "", false, 0.0, "", 0)
        }
    }

    suspend fun getVideoMovie(idMovie: Long, apiClient: String) : VideoMovie{
       // return Resource.Success(RetrofitClient.webservice.getVideoMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getVideoMovie(idMovie, apiClient)
            response.body() ?: VideoMovie(0, emptyList())
        }
    }
}