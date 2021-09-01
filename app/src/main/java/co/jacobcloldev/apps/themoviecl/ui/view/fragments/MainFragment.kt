package co.jacobcloldev.apps.themoviecl.ui.view.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.jacobcloldev.apps.themoviecl.R
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.databinding.FragmentMainBinding
import co.jacobcloldev.apps.themoviecl.ui.view.MainActivity
import co.jacobcloldev.apps.themoviecl.ui.view.adapters.MainAdapter
import co.jacobcloldev.apps.themoviecl.ui.view.fragments.viewmodel.MovieViewModel
import javax.inject.Inject

class MainFragment : Fragment(), MainAdapter.OnMovieClickListener, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    @Inject lateinit var movieViewModel: MovieViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var page = 1

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mActivity: FragmentActivity

    private val listMovies: ArrayList<Movie> = ArrayList()

    private val mainAdapter by lazy {
        MainAdapter(requireContext(), listMovies, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let {
            mActivity = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupRecyclerView()
        //  setupObserver()
        setSwipeRefreshLayout()
        setUpToobar()

        binding.bnPreview.setOnClickListener(this)
        binding.bnNext.setOnClickListener(this)
        binding.swFilter.setOnCheckedChangeListener(this)

        movieViewModel.movieByPage()

        movieViewModel.movie.observe(viewLifecycleOwner, Observer {
            listMovies.clear()
            listMovies.addAll(it)
            mainAdapter.notifyDataSetChanged()
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = false
            }
        })

        movieViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                movieViewModel.favoriteMovies()
            }
            R.id.action_saved_movies -> {
                movieViewModel.savedMovies()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMovieClick(idMovie: Long) {
        val bundle = Bundle()
        bundle.putLong("idMovie", idMovie)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }

    override fun onClick(v: View?) {
        binding.swFilter.isChecked = false
        when (v!!.id) {
            R.id.bnPreview -> {
                if (page == 1) {
                    binding.bnPreview.isEnabled = false
                    configureButtonPreview(R.color.grey)
                } else {
                    page -= 1
                    movieViewModel.setPage(page)
           movieViewModel.movieByPage()
                    configureButtonPreview(R.color.purple_500)
                }
            }
            R.id.bnNext -> {
                page += 1
                movieViewModel.setPage(page)

                movieViewModel.movieByPage()
                binding.bnPreview.isEnabled = true
                configureButtonPreview(R.color.purple_500)
                if (page == 1) {
                    binding.bnPreview.isEnabled = false
                    configureButtonPreview(R.color.grey)
                }
            }
        }
    }

    private fun configureButtonPreview(color: Int) {
        binding.bnPreview.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                color
            )
        )
    }

    private fun setupRecyclerView() {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        else
            binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 4)

        binding.rvPopularMovies.itemAnimator = DefaultItemAnimator()
        binding.rvPopularMovies.adapter = mainAdapter
    }

    private fun setSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            movieViewModel.movieByPage()
        }
    }

    private fun setUpToobar() {
        val mainActivity = mActivity as MainActivity
        val toolbar = binding.toolbar
        mainActivity.setSupportActionBar(toolbar)

        val navController = NavHostFragment.findNavController(this)
        NavigationUI.setupActionBarWithNavController(mainActivity, navController)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            filterListMovie()
        } else {
            movieViewModel.movieByPage()
        }
    }

    private fun filterListMovie() {
        val listFilter: ArrayList<Movie> = ArrayList()
        for (item in listMovies) {
            if (item.voteCount > 2000) {
                listFilter.add(item)
            }
        }
        listMovies.clear()
        listMovies.addAll(listFilter)
        mainAdapter.notifyDataSetChanged()
    }
}