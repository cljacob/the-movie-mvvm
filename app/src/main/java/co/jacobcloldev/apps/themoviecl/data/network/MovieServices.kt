package co.jacobcloldev.apps.themoviecl.data.network

import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.core.RetrofitClient
import co.jacobcloldev.apps.themoviecl.data.model.Movie

class MovieServices {

    suspend fun getPopularMovier(apiClient: String, page: Int) : Resource<List<Movie>> {
        return Resource.Success(RetrofitClient.webservice.getPopularMovies(apiClient, page).results)
    }

}