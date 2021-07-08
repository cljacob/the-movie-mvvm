package co.jacobcloldev.apps.themoviecl.ui.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.jacobcloldev.apps.themoviecl.R
import co.jacobcloldev.apps.themoviecl.core.BaseViewHolder
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.databinding.ItemRowMovieBinding
import com.bumptech.glide.Glide

class MainAdapter(private val context: Context, private val moviesList: ArrayList<Movie>, private val itemClickListener: OnMovieClickListener) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    var moviesFilterList = ArrayList<Movie>()

    init {
        moviesFilterList = moviesList
    }

    interface OnMovieClickListener{
        fun onMovieClick(idMovie: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemRowMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(moviesList[position], position)
        }
    }

    override fun getItemCount() = moviesList.size

    inner class MainViewHolder(private val binding: ItemRowMovieBinding) :
        BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie, position: Int): Unit = with(binding) {
            val urlImage = "https://image.tmdb.org/t/p/w500${item.posterPath}"
            Glide.with(context).load(urlImage).placeholder(R.drawable.load).into(thumbnail)
            title.text = item.originalTitle
            rating.text = item.voteAverage.toString()
            itemView.setOnClickListener { itemClickListener.onMovieClick(item.id) }
        }
    }

}