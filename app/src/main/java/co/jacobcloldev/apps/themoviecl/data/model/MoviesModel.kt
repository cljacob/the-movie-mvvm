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

data class Movie (
  //  val adult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

   /* @SerializedName("genre_ids")
    val genreIDS: List<Long>,
*/
    val id: Long,

   /* @SerializedName("original_language")
    val originalLanguage: String,*/

    @SerializedName("original_title")
    val originalTitle: String,

   /* val overview: String,
    val popularity: Double,
*/
    @SerializedName("poster_path")
    val posterPath: String,
/*

    @SerializedName("release_date")
    val releaseDate: String,
*/

    val title: String,
   // val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,

    /*@SerializedName("vote_count")
    val voteCount: Long*/
)