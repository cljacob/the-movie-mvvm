package co.jacobcloldev.apps.themoviecl.ui.view.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.jacobcloldev.apps.themoviecl.R
import co.jacobcloldev.apps.themoviecl.core.Resource
import co.jacobcloldev.apps.themoviecl.core.VMFactory
import co.jacobcloldev.apps.themoviecl.data.model.Movie
import co.jacobcloldev.apps.themoviecl.data.model.MoviesModel
import co.jacobcloldev.apps.themoviecl.data.network.MovieServices
import co.jacobcloldev.apps.themoviecl.databinding.FragmentMainBinding
import co.jacobcloldev.apps.themoviecl.domain.ImplementationRepo
import co.jacobcloldev.apps.themoviecl.ui.view.MainActivity
import co.jacobcloldev.apps.themoviecl.ui.view.adapters.MainAdapter
import co.jacobcloldev.apps.themoviecl.ui.viewmodel.MainViewModel
import com.google.android.material.navigation.NavigationView

class MainFragment : Fragment(), MainAdapter.OnMovieClickListener, View.OnClickListener {

    private val viewModel by viewModels<MainViewModel> { VMFactory(ImplementationRepo(MovieServices())) }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var page = 1

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var mActivity: FragmentActivity

    private val listMovies: ArrayList<Movie> = ArrayList()

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
        setupRecyclerView()
        setupObserver()
        setSwipeRefreshLayout()
        setUpToobar()
        binding.bnPreview.setOnClickListener(this)
        binding.bnNext.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        if (requireActivity().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 2)
        else
            binding.rvPopularMovies.layoutManager = GridLayoutManager(requireContext(), 4)

        binding.rvPopularMovies.itemAnimator = DefaultItemAnimator()
    }

    private fun setSwipeRefreshLayout() {
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.setPage(page)
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupObserver() {
        viewModel.fetchPopularMoviesList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val listMovies = result.data as List<Movie>
                    if (!listMovies.isNullOrEmpty()) {
                        binding.rvPopularMovies.adapter =
                            MainAdapter(requireContext(), listMovies, this)
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Error loading movies ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    override fun onMovieClick(idMovie: Long) {
        val bundle = Bundle()
        bundle.putLong("idMovie", idMovie)
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }

    private fun setUpToobar() {
        val mainActivity = mActivity as MainActivity
        val toolbar = binding.toolbar
        mainActivity.setSupportActionBar(toolbar)

        val navController = NavHostFragment.findNavController(this)
        NavigationUI.setupActionBarWithNavController(mainActivity, navController)
        mainActivity.supportActionBar?.setHomeButtonEnabled(true)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(v: View?) {
       when(v!!.id){
           R.id.bnPreview -> {
               if (page == 1) {
                   binding.bnPreview.isEnabled = false
                   binding.bnPreview.setBackgroundColor(
                       ContextCompat.getColor(
                           requireContext(),
                           R.color.grey
                       )
                   )
               }else {
                   page =- 1
                   viewModel.setPage(page)
                   binding.bnPreview.setBackgroundColor(
                       ContextCompat.getColor(
                           requireContext(),
                           R.color.purple_500
                       )
                   )
               }
           }
           R.id.bnNext -> {
               page =+ 1
               viewModel.setPage(page)
               binding.bnPreview.setBackgroundColor(
                   ContextCompat.getColor(
                       requireContext(),
                       R.color.purple_500
                   )
               )
           }
       }
    }
}