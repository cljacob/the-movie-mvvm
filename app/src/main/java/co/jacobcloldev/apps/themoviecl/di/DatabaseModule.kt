package co.jacobcloldev.apps.themoviecl.di

import android.content.Context
import androidx.room.Room
import co.jacobcloldev.apps.themoviecl.data.db.AppDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideMoviesDatabase(context: Context): AppDataBase{
        return Room.databaseBuilder(context, AppDataBase::class.java, MOVIE_DATABASE_NAME).build()
    }
}

private const val MOVIE_DATABASE_NAME = "movie_table"