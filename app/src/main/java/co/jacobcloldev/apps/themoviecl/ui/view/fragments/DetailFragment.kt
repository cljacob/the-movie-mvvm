package co.jacobcloldev.apps.themoviecl.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import co.jacobcloldev.apps.themoviecl.R
import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.core.VMFactory
import co.jacobcloldev.apps.themoviecl.data.db.AppDataBase
import co.jacobcloldev.apps.themoviecl.data.model.DetailMovie
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MovieEntity
import co.jacobcloldev.apps.themoviecl.data.model.VideoMovie
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices
import co.jacobcloldev.apps.themoviecl.databinding.FragmentDetailBinding
import co.jacobcloldev.apps.themoviecl.domain.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.ui.view.MainActivity
import co.jacobcloldev.apps.themoviecl.ui.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout

class DetailFragment : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    private val viewModel by activityViewModels<MainViewModel> {
        VMFactory(
            ImplementationRepo(
                MovieServices(AppDataBase.getDataBase(requireActivity().applicationContext))
            )
        )
    }

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
        setupObserver()
        setUpToobar()
        initCollapsingToolbar()
        binding.txHomePage.setOnClickListener(this)
        binding.bnTeaser.setOnClickListener(this)
        binding.bnTrailler.setOnClickListener(this)
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

    private fun setupObserver() {
        viewModel.setIdMovie(idMovie)
        viewModel.fetchDetailMovie.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    movie = result.data as DetailMovie

                    val urlImage = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                    Glide.with(requireContext()).load(urlImage).placeholder(R.drawable.load)
                        .into(binding.thumbnailImageheader)

                    binding.txTitle.text = movie.originalTitle
                    binding.txPlotSynopsis.text = movie.overview
                    binding.txReleaseDate.text = movie.releaseDate
                    binding.txUserRating.text = movie.voteAverage.toString()
                    binding.txBudget.text = movie.budget.toString()
                    binding.txHomePage.text = movie.homepage
                    binding.txHomePage.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    getVideoMovie()
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error loading data movies ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getVideoMovie() {
        viewModel.fetchVideoMovie.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val videoMovie = result.data as VideoMovie
                    if (!videoMovie.results.isNullOrEmpty()) {
                        for (item in videoMovie.results) {
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
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error loading video movies ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun saveMovie() {
        viewModel.saveMovie(
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
        viewModel.updataMovie(isFavorite, movie.id)
    }
}