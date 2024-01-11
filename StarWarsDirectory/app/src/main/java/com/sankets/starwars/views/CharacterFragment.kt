package com.sankets.starwars.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sankets.starwars.databinding.FragmentCharactersBinding
import com.sankets.starwars.domain.adapters.CharacterGridAdapter
import com.sankets.starwars.domain.utils.collectLatestLifecycleFlow
import com.sankets.starwars.viewmodel.StarWarsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var mainActivity: MainActivity

    private lateinit var binding: FragmentCharactersBinding

    private val viewModel: StarWarsViewModel by activityViewModels()
    private lateinit var characterAdapter: CharacterGridAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)

        initViews()

        collectLatestLifecycleFlow(viewModel.characterFlow) { characters ->
            if (characters.isNotEmpty()){
                characterAdapter.differ.submitList(characters)
            }
        }

        collectLatestLifecycleFlow(viewModel.charactersLoading) { isLoading ->
            Timber.tag("LOADING").d("LOADING $isLoading")
            showComponents(isLoading)
        }

        binding.buttonSortFilter.setOnClickListener {
            mainActivity.loadFilterFragment()
        }

        return binding.root
    }

    private fun initViews() {
        characterAdapter = CharacterGridAdapter(onItemClick = { starWarsCharactersUI ->
            viewModel.hitFilmApiWithFilmId(starWarsCharactersUI.films, starWarsCharactersUI.name)
            mainActivity.loadFilmFragment()
        })
        binding.heading.visibility = View.VISIBLE

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(binding.root.context, 2)
            adapter = characterAdapter
            visibility = View.VISIBLE
            addOnScrollListener(object  : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(!canScrollVertically(RecyclerView.VERTICAL) && dy > 0){
                        viewModel.hitCharacterApiWithPage()

                    }
                }
            })

        }
    }

    private fun showComponents(isLoading : Boolean){
        if(isLoading){
            binding.apply {
                progressBar.visibility = View.VISIBLE
            }
        }
        else{
            binding.apply {
                progressBar.visibility = View.GONE
            }
        }
    }

}

