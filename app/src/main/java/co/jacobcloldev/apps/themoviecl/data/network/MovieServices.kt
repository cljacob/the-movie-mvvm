package co.jacobcloldev.apps.themoviecl.data.network

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.core.RetrofitClient
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie

class MovieServices {

    suspend fun getPopularMovier(apiClient: String, page: Int) : Resource<List<Movie>> {
        return Resource.Success(RetrofitClient.webservice.getPopularMovies(apiClient, page).results)
    }

    suspend fun getDetailMovie(idMovie: Long, apiClient: String) : Resource<DetailMovie>{
        return Resource.Success(RetrofitClient.webservice.getDetailMovie(idMovie, apiClient))
    }

    suspend fun getVideoMovie(idMovie: Long, apiClient: String) : Resource<VideoMovie>{
        return Resource.Success(RetrofitClient.webservice.getVideoMovie(idMovie, apiClient))
    }
}