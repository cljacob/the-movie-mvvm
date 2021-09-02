package co.jacobcloldev.apps.themoviecl.di

import co.jacobcloldev.apps.themoviecl.ui.view.MainActivity
import co.jacobcloldev.apps.themoviecl.ui.view.fragments.DetailFragment
import co.jacobcloldev.apps.themoviecl.ui.view.fragments.MainFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface AppSubcomponents {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AppSubcomponents
    }

    fun inject(activity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(detailFragment: DetailFragment)
}