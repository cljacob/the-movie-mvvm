package co.jacobcloldev.apps.themoviecl.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.domain.MovieDao

@Database(entities = arrayOf(MovieEntity::class), version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}