package co.jacobcloldev.apps.themoviecl.di

import android.app.Activity
import android.content.Context
import co.jacobcloldev.apps.themoviecl.domain.Repository
import dagger.BindsInstance
import dagger.Component
import java.sql.DatabaseMetaData
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {
}