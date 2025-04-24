package com.lucas.pokedexapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lucas.pokedexapp.R
import com.lucas.pokedexapp.data.model.Pokemon
import com.squareup.picasso.Picasso

class PokemonAdapter(
    private var pokemonList: List<Pokemon>
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPokemon: ImageView = itemView.findViewById(R.id.imageViewPokemon)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
        val textViewType1: TextView = itemView.findViewById(R.id.textViewType1)
        val textViewType2: TextView = itemView.findViewById(R.id.textViewType2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        holder.textViewName.text = pokemon.name.replaceFirstChar { it.uppercase() }
        holder.textViewNumber.text = "#${pokemon.id}"

        Picasso.get()
            .load(pokemon.sprites.other.officialArtwork.frontDefault)
            .into(holder.imageViewPokemon)

        holder.textViewType1.visibility = View.GONE
        holder.textViewType2.visibility = View.GONE

        if (pokemon.types.isNotEmpty()) {
            val type1 = pokemon.types[0].type.name
            holder.textViewType1.text = traduzirTipo(type1)
            holder.textViewType1.visibility = View.VISIBLE
            setTypeColor(holder.textViewType1, type1)
        }

        if (pokemon.types.size > 1) {
            val type2 = pokemon.types[1].type.name
            holder.textViewType2.text = traduzirTipo(type2)
            holder.textViewType2.visibility = View.VISIBLE
            setTypeColor(holder.textViewType2, type2)
        }
    }

    override fun getItemCount(): Int = pokemonList.size

    fun updateData(newList: List<Pokemon>) {
        pokemonList = newList
        notifyDataSetChanged()
    }

    private fun traduzirTipo(tipo: String): String {
        return when (tipo.lowercase()) {
            "normal" -> "Normal"
            "fire" -> "Fogo"
            "water" -> "Água"
            "electric" -> "Elétrico"
            "grass" -> "Grama"
            "ice" -> "Gelo"
            "fighting" -> "Lutador"
            "poison" -> "Veneno"
            "ground" -> "Terra"
            "flying" -> "Voador"
            "psychic" -> "Psíquico"
            "bug" -> "Inseto"
            "rock" -> "Pedra"
            "ghost" -> "Fantasma"
            "dragon" -> "Dragão"
            "dark" -> "Sombrio"
            "steel" -> "Aço"
            "fairy" -> "Fada"
            else -> tipo
        }
    }
}
