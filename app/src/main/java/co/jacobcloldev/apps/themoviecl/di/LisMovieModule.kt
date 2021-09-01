package co.jacobcloldev.apps.themoviecl.di

import co.jacobcloldev.apps.themoviecl.data.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.data.db.AppDataBase
import co.jacobcloldev.apps.themoviecl.data.db.DBServices
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices
import co.jacobcloldev.apps.themoviecl.domain.Repository
import dagger.Module
import dagger.Provides

@Module
class LisMovieModule {

    /*@Provides
    fun provideGetMoviesUseCase()*/

    @Provides
    fun provideRepository(localDataSource: DBServices,
                          remoteDataSource : MovieServices
    ) : Repository = ImplementationRepo(localDataSource, remoteDataSource)

   /* @Provides
    fun provideLocalDataSource(appDataBase: AppDataBase) : DBServices = */

    /*@Provides
    fun provideRemoteDataSource()*/
}