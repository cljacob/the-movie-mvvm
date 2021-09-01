package co.jacobcloldev.apps.themoviecl.di

import co.jacobcloldev.apps.themoviecl.data.network.MovieApiClient
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideGsonConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitService(gsonConverterFactory: GsonConverterFactory) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiClient(retrofit: Retrofit): MovieApiClient{
        return retrofit.create(MovieApiClient::class.java)
    }
}

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"