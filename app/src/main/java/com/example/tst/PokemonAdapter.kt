package com.example.tst

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList: List<PokemonData>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemonImage)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        val pokemonType1: TextView = itemView.findViewById(R.id.pokemonType1)
        val pokemonType2: TextView = itemView.findViewById(R.id.pokemonType2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_card, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl)
            .into(holder.pokemonImage)
        holder.pokemonName.text = pokemon.name
        holder.pokemonType1.text = pokemon.type1
        holder.pokemonType2.text = pokemon.type2
    }

    override fun getItemCount(): Int = pokemonList.size
}
