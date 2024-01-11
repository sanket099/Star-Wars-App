package com.sankets.starwars.domain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sankets.starwars.databinding.LayoutFilmItemBinding
import com.sankets.starwars.domain.models.StarWarsFilmsUI

/**
 * Adapter to Support Recycler view for Films
 */
class FilmGridAdapter(private val onItemClick: (StarWarsFilmsUI) -> Unit) :
    RecyclerView.Adapter<FilmGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutFilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.bind(character)
    }

    override fun getItemCount(): Int = differ.currentList.size

    class ViewHolder(
        private val binding: LayoutFilmItemBinding,
        private val onItemClick: (StarWarsFilmsUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(film: StarWarsFilmsUI) {
            binding.name.text = film.title

            itemView.setOnClickListener {
                onItemClick.invoke(film)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<StarWarsFilmsUI>() {
        override fun areItemsTheSame(
            oldItem: StarWarsFilmsUI, newItem: StarWarsFilmsUI
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: StarWarsFilmsUI, newItem: StarWarsFilmsUI
        ): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.openingCrawl == newItem.openingCrawl &&
                    oldItem.director == newItem.director &&
                    oldItem.producer == newItem.producer &&
                    oldItem.releaseDate == newItem.releaseDate &&
                    oldItem.episodeId == newItem.episodeId
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}
