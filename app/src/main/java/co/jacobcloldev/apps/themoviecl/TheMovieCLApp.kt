package co.jacobcloldev.apps.themoviecl

import android.app.Application
import co.jacobcloldev.apps.themoviecl.di.DaggerApplicationComponent


class TheMovieCLApp: Application() {
        val appComponent = DaggerApplicationComponent.create()

}