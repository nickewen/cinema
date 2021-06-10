package com.coldandroid.cinema.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coldandroid.cinema.R
import com.coldandroid.cinema.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var movieListAdapter: MovieListAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initAdapter()
        initObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMovies()
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        movieListAdapter = MovieListAdapter()
        binding.recyclerView.adapter = movieListAdapter
    }

    private fun initObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, observerViewState())
    }

    private fun observerViewState() = Observer<ViewState> {
        when (it) {
            is ViewState.Loading -> { showLoading() }
            is ViewState.MoviesLoaded ->  {
                hideLoading()
                movieListAdapter.setMovies(it.movies)
            }
            is ViewState.MoviesLoadFailure -> {
                hideLoading()
                showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(requireContext(), R.string.get_movies_error_message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun loadMovies() {
        viewModel.getMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}