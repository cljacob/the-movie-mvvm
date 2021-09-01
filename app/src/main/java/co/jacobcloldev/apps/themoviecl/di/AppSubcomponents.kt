package co.jacobcloldev.apps.themoviecl.di

import co.jacobcloldev.apps.themoviecl.ui.view.fragments.DetailFragment
import co.jacobcloldev.apps.themoviecl.ui.view.fragments.MainFragment
import dagger.Subcomponent

@Subcomponent(modules = [LisMovieModule::class, DetailMovieModile::class])
interface AppSubcomponents {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AppSubcomponents
    }

    fun inject(movie: MainFragment)

    fun inject(detailMovie: DetailFragment)
}