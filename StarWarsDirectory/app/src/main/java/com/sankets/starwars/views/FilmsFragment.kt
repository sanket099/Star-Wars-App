package com.sankets.starwars.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.sankets.starwars.R
import com.sankets.starwars.databinding.FragmentFilmsBinding
import com.sankets.starwars.domain.adapters.FilmGridAdapter
import com.sankets.starwars.domain.utils.collectLatestLifecycleFlow
import com.sankets.starwars.viewmodel.StarWarsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmsFragment : Fragment() {

    private lateinit var binding: FragmentFilmsBinding

    private lateinit var filmsAdapter: FilmGridAdapter
    private val viewModel: StarWarsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmsBinding.inflate(layoutInflater)

        initViews()

        collectLatestLifecycleFlow(viewModel.filmFlow) { films ->
            if (films.isNotEmpty()) {
                filmsAdapter.differ.submitList(films)
            }
        }

        collectLatestLifecycleFlow(viewModel.selectedCharacter) { name ->
            if (name.isNotEmpty()) {
                binding.heading.text = getString(R.string.films_by, name.lowercase())
            }
        }

        collectLatestLifecycleFlow(viewModel.filmsLoading) { isLoading ->
            showComponents(isLoading)
        }

        return binding.root
    }

    private fun initViews() {
        filmsAdapter = FilmGridAdapter(onItemClick = {})
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(binding.root.context, 2)
            adapter = filmsAdapter
        }
    }

    private fun showComponents(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                heading.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                heading.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopFilmJob()
    }
}