package com.sankets.starwars.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.sankets.starwars.databinding.FragmentFiltersBinding
import com.sankets.starwars.domain.utils.Constants
import com.sankets.starwars.domain.utils.collectLatestLifecycleFlow
import com.sankets.starwars.viewmodel.StarWarsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFilterFragment : BottomSheetDialogFragment() {

    private val viewModel: StarWarsViewModel by activityViewModels()

    private lateinit var binding : FragmentFiltersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltersBinding.inflate(layoutInflater)

        binding.apply {
            this.chipUpdatedAt.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    this.chipCreatedAt.isChecked = false
                    this.chipName.isChecked = false
                    viewModel.sortCharacters(Constants.SortBy.UPDATED_AT.name)
                }

            }

            this.chipCreatedAt.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    this.chipUpdatedAt.isChecked = false
                    this.chipName.isChecked = false
                    viewModel.sortCharacters(Constants.SortBy.CREATED_AT.name)
                }
            }

            this.chipName.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    this.chipUpdatedAt.isChecked = false
                    this.chipUpdatedAt.isChecked = false
                    viewModel.sortCharacters(Constants.SortBy.NAME.name)
                }
            }

            collectLatestLifecycleFlow(viewModel.characterHairColors) { hairColors ->
                if (hairColors.isNotEmpty()){
                    createAndAddChips(this.hairColorChipGroup, hairColors.distinct(), Constants.FilterType.HAIR_COLOR.name)
                }
            }

            collectLatestLifecycleFlow(viewModel.characterSkinColors) { skinColors ->
                if (skinColors.isNotEmpty()){
                    createAndAddChips(this.skinColorChipGroup, skinColors.distinct(), Constants.FilterType.SKIN_COLOR.name)
                }
            }

            collectLatestLifecycleFlow(viewModel.characterEyeColors) { eyeColors ->
                if (eyeColors.isNotEmpty()){
                    createAndAddChips(this.eyeColorChipGroup, eyeColors.distinct(), Constants.FilterType.EYE_COLOR.name)
                }
            }
        }
        return binding.root
    }

    // Filter chips will be created based on characters
    private fun createAndAddChips(chipGroup: ChipGroup, chipTexts: List<String>, filterType : String) {
        chipGroup.removeAllViews()
        for (text in chipTexts) {
            val chip = createChip(text, filterType)
            chipGroup.addView(chip)
        }
    }

    private fun createChip(text: String, filterType : String): Chip {
        val chip = Chip(requireContext())
        chip.text = text

         chip.isCheckable = true
         chip.setOnCheckedChangeListener { _, isChecked ->
             if(isChecked){
                 when(filterType){
                     Constants.FilterType.HAIR_COLOR.name -> {
                         uncheckAllChips(binding.hairColorChipGroup)
                         viewModel.filterHairColor(text)
                     }
                     Constants.FilterType.SKIN_COLOR.name -> {
                         uncheckAllChips(binding.hairColorChipGroup)
                         viewModel.filterSkinColor(text)
                     }
                     Constants.FilterType.EYE_COLOR.name -> {
                         uncheckAllChips(binding.hairColorChipGroup)
                         viewModel.filterEyeColor(text)
                     }
                 }

             }
         }

        return chip
    }

    private fun uncheckAllChips(chipGroup: ChipGroup) {
        for (index in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(index) as? Chip
            chip?.isChecked = false
        }
    }
}