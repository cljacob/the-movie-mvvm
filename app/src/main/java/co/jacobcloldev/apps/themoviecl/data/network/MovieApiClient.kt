package co.jacobcloldev.apps.themoviecl.data.network

import co.jacobcloldev.apps.themoviecl.data.model.MoviesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiClient {
    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int) : MoviesModel

}