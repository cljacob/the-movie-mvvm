package co.jacobcloldev.apps.themoviecl.data.network

import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.MoviesModel
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiClient {
    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int) : MoviesModel

    @GET("{idMovie}")
    suspend fun getDetailMovie(@Path("idMovie") idMovie: Long, @Query("api_key") apiKey: String): DetailMovie

    @GET("{idMovie}/videos")
    suspend fun getVideoMovie(@Path("idMovie") idMovie: Long, @Query("api_key") apiKey: String): VideoMovie

}