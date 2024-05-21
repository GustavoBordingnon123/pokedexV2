package com.example.tst

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList: List<PokemonData>, private val onItemClick: (PokemonData) -> Unit) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View, val onItemClick: (PokemonData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemonImage)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        private val pokemonType1: TextView = itemView.findViewById(R.id.pokemonType1)
        private val pokemonType2: TextView = itemView.findViewById(R.id.pokemonType2)

        fun bind(pokemonData: PokemonData) {
            Glide.with(itemView.context).load(pokemonData.imageUrl).into(pokemonImage)
            pokemonName.text = pokemonData.name
            pokemonType1.text = pokemonData.type1
            pokemonType2.text = pokemonData.type2
            itemView.setOnClickListener { onItemClick(pokemonData) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card, parent, false)
        return PokemonViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size
}
