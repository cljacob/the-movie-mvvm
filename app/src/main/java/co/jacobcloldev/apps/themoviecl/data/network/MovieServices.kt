package co.jacobcloldev.apps.themoviecl.data.network

<<<<<<< HEAD
=======
import co.jacobcloldev.apps.themoviecl.BuildConfig
>>>>>>> e1885200c80f0492ec3530bce7d0f9207a08e248
import co.jacobcloldev.apps.themoviecl.core.RetrofitClient
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieServices {

    private val retrofit = RetrofitClient.getRetrofit()
<<<<<<< HEAD

    suspend fun getPopularMovier(apiClient: String, page: Int) : List<Movie> {
        //return Resource.Success(RetrofitClient.webservice.getPopularMovies(apiClient, page).results)
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getPopularMovies(apiClient, page)
=======

    private val apiKey = BuildConfig.THE_MOVIE_API_KEY

    suspend fun getPopularMovier(page: Int) : List<Movie> {
        //return Resource.Success(RetrofitClient.webservice.getPopularMovies(apiClient, page).results)
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getPopularMovies(apiKey, page)
>>>>>>> e1885200c80f0492ec3530bce7d0f9207a08e248
            response.body() ?: emptyList()
        }
    }

<<<<<<< HEAD
    suspend fun getDetailMovie(idMovie: Long, apiClient: String) : DetailMovie {
       // return Resource.Success(RetrofitClient.webservice.getDetailMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getDetailMovie(idMovie, apiClient)
=======
    suspend fun getDetailMovie(idMovie: Long) : DetailMovie {
       // return Resource.Success(RetrofitClient.webservice.getDetailMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getDetailMovie(idMovie, apiKey)
>>>>>>> e1885200c80f0492ec3530bce7d0f9207a08e248
            response.body() ?: DetailMovie(0, "", 0, "", "", "",
                "", "", false, 0.0, "", 0)
        }
    }

<<<<<<< HEAD
    suspend fun getVideoMovie(idMovie: Long, apiClient: String) : VideoMovie{
       // return Resource.Success(RetrofitClient.webservice.getVideoMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getVideoMovie(idMovie, apiClient)
=======
    suspend fun getVideoMovie(idMovie: Long) : VideoMovie{
       // return Resource.Success(RetrofitClient.webservice.getVideoMovie(idMovie, apiClient))
        return withContext(Dispatchers.IO){
            val response = retrofit.create(MovieApiClient::class.java).getVideoMovie(idMovie, apiKey)
>>>>>>> e1885200c80f0492ec3530bce7d0f9207a08e248
            response.body() ?: VideoMovie(0, emptyList())
        }
    }
}