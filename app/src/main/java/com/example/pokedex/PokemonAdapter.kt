package com.example.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.model.Pokemon


class PokemonAdapter :  RecyclerView.Adapter<PokemonViewHolder>(){

    private val pokemons = ArrayList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.pokemonrow, parent, false)
        val pokemonViewHolder = PokemonViewHolder(view)

        return pokemonViewHolder
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonn = pokemons[position]
        holder.pokemontextrow.text = pokemonn.name

    }

    fun addPokemon(newPokemon : Pokemon){
        pokemons.add(newPokemon)

    }

    fun clearPokemon(){
        pokemons.clear()
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

} //end of class