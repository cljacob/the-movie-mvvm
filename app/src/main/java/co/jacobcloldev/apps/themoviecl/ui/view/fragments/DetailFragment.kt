package co.jacobcloldev.apps.themoviecl.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import co.jacobcloldev.apps.themoviecl.R
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.databinding.FragmentDetailBinding
import co.jacobcloldev.apps.themoviecl.ui.view.MainActivity
import co.jacobcloldev.apps.themoviecl.ui.view.fragments.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var idMovie: Long = 0

    private lateinit var mActivity: FragmentActivity

    private var isShow = false
    private var scrollRange = -1
    private var urlTriller = ""
    private var urlTeaser = ""

    private lateinit var movie: DetailMovie

    private var flagFavorite = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            mActivity = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            idMovie = it.getLong("idMovie")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setUpToobar()
        initCollapsingToolbar()
        binding.txHomePage.setOnClickListener(this)
        binding.bnTeaser.setOnClickListener(this)
        binding.bnTrailler.setOnClickListener(this)

        detailViewModel.detailMovie.observe(viewLifecycleOwner, Observer {
            val urlImage = "https://image.tmdb.org/t/p/w500${it.posterPath}"
            Glide.with(requireContext()).load(urlImage).placeholder(R.drawable.load)
                .into(binding.thumbnailImageheader)

            binding.txTitle.text = it.originalTitle
            binding.txPlotSynopsis.text = it.overview
            binding.txReleaseDate.text = it.releaseDate
            binding.txUserRating.text = it.voteAverage.toString()
            binding.txBudget.text = it.budget.toString()
            binding.txHomePage.text = it.homepage
            binding.txHomePage.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            movie = it

            detailViewModel.fetchVideoMovie()
        })

        detailViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        detailViewModel.videMovieUrl.observe(viewLifecycleOwner, Observer {
            if (!it.results.isNullOrEmpty()) {
                for (item in it.results) {
                    if (item.site == "YouTube") {
                        when (item.type) {
                            "Trailer" -> {
                                binding.bnTrailler.visibility = View.VISIBLE
                                urlTriller = "https://www.youtube.com/watch?v=${item.key}"
                            }
                            "Teaser" -> {
                                binding.bnTeaser.visibility = View.VISIBLE
                                urlTeaser = "https://www.youtube.com/watch?v=${item.key}"
                            }
                        }
                    }
                }
                saveMovie()
            }
        })

        detailViewModel.setIdMovie(idMovie)
        detailViewModel.fetchDetailMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                val favorite = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
                val unFavorite =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_border)
                if (flagFavorite) {
                    item.icon = unFavorite
                    updateMovie(false)
                    flagFavorite = false
                } else {
                    item.icon = favorite
                    updateMovie(true)
                    flagFavorite = true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout?.totalScrollRange ?: -1
        }
        if (scrollRange + verticalOffset == 0) {
            binding.collapsingToolbar.title = getString(R.string.movie_detail)
            isShow = true
        } else {
            binding.collapsingToolbar.title = " "
            isShow = false
        }
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.bnTrailler -> {
                openIntenActionView(urlTriller)
            }
            R.id.bnTeaser -> {
                openIntenActionView(urlTeaser)
            }
            R.id.txHomePage -> {
                openIntenActionView(binding.txHomePage.text.toString())
            }
        }
    }

    private fun openIntenActionView(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun setUpToobar() {
        val mainActivity = mActivity as MainActivity
        val toolbar = binding.toolbar
        mainActivity.setSupportActionBar(toolbar)

        val navController = NavHostFragment.findNavController(this)
        NavigationUI.setupActionBarWithNavController(mainActivity, navController)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initCollapsingToolbar() {
        binding.collapsingToolbar.title = " "
        binding.appBar.setExpanded(true)
        binding.appBar.addOnOffsetChangedListener(this)
    }


    private fun saveMovie() {
        detailViewModel.setContext(requireContext())
        detailViewModel.saveMovie(
            MovieEntity(
                movie.id,
                movie.backdropPath,
                movie.budget,
                movie.originalLanguage,
                movie.originalTitle,
                movie.overview,
                movie.posterPath,
                movie.releaseDate,
                urlTriller,
                urlTeaser,
                movie.voteAverage,
                movie.homepage,
                false,
                movie.voteCount
            )
        )
        Toast.makeText(
            requireContext(),
            "The movie was save.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun updateMovie(isFavorite: Boolean) {
        detailViewModel.setContext(requireContext())
        detailViewModel.updataMovie(isFavorite, movie.id)
    }
}