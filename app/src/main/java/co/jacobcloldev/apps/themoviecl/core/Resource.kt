package co.jacobcloldev.apps.themoviecl.core

sealed class Resource<out T> {
    class Loading<out T>: Resource<T>()
    data class Success<out T>(val data: T): Resource<T>()
    data class Failure<out T>(val exception: T) : Resource<T>()
}