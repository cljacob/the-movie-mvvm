package co.jacobcloldev.apps.themoviecl.data.model

import com.google.gson.annotations.SerializedName

data class MoviesModel(
    val page: Long,
    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long
)

data class Movie(

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    val id: Long,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("poster_path")
    val posterPath: String,

    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,
)

data class DetailMovie(

    @SerializedName("backdrop_path")
    val backdropPath: String,

    val budget: Long,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    val overview: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("release_date")
    val releaseDate: String,

    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    val homepage: String
)

data class VideoMovie(
    val id: Long,
    val results: List<Video>
)

data class Video (
    val key: String,
    val name: String,
    val site: String,
    val type: String
)