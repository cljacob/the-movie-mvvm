package co.jacobcloldev.apps.themoviecl.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.jacobcloldev.apps.themoviecl.domain.Repository

class VMFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Repository::class.java).newInstance(repo)
    }
}