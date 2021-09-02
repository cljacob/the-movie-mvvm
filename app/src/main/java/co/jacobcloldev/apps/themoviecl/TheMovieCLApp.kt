package co.jacobcloldev.apps.themoviecl

import android.app.Application
import co.jacobcloldev.apps.themoviecl.di.ApplicationComponent
import co.jacobcloldev.apps.themoviecl.di.DaggerApplicationComponent


class TheMovieCLApp: Application() {
        lateinit var appComponent: ApplicationComponent

        override fun onCreate() {
                super.onCreate()
               appComponent = DaggerApplicationComponent.factory().create(this)
        }
}