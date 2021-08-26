package co.jacobcloldev.apps.themoviecl.domain

import androidx.room.*
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity WHERE favorite == :isFavorite")
    suspend fun getGetFavoritesMovies(isFavorite: Boolean = true) : List<MovieEntity>

    @Query("SELECT * FROM MovieEntity")
    suspend fun getSavedMovies() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("UPDATE MovieEntity SET favorite = :isFavorite WHERE id == :id")
    suspend fun updateMovie(isFavorite: Boolean, id: Long)
}