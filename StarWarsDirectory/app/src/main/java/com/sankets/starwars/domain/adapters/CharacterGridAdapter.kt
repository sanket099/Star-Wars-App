package com.sankets.starwars.domain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sankets.starwars.databinding.LayoutCharacterItemBinding
import com.sankets.starwars.domain.models.StarWarsCharactersUI

/**
 * Adapter to Support Recycler view for Characters
 */
class CharacterGridAdapter(
    private val onItemClick: (StarWarsCharactersUI) -> Unit,
) : RecyclerView.Adapter<CharacterGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(
        private val binding: LayoutCharacterItemBinding,
        private val onItemClick: (StarWarsCharactersUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: StarWarsCharactersUI) {
            binding.name.text = character.name
            binding.height.text = "Height : ${character.height}"
            binding.hairColor.text = "Hair Color : ${character.hairColor}"
            binding.skinColor.text = "Skin Color : ${character.skinColor}"
            binding.eyeColor.text = "Eye Color : ${character.eyeColor}"
            binding.birthYear.text = "Birth Year : ${character.birthYear}"
            binding.gender.text = "Gender : ${character.gender}"

            itemView.setOnClickListener {
                onItemClick.invoke(character)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<StarWarsCharactersUI>() {
        override fun areItemsTheSame(
            oldItem: StarWarsCharactersUI, newItem: StarWarsCharactersUI
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: StarWarsCharactersUI, newItem: StarWarsCharactersUI
        ): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.gender == newItem.gender &&
                    oldItem.height == newItem.height &&
                    oldItem.hairColor == newItem.hairColor &&
                    oldItem.eyeColor == newItem.eyeColor &&
                    oldItem.skinColor == newItem.skinColor &&
                    oldItem.birthYear == newItem.birthYear
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
