package com.example.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.model.Pokemon
import com.example.pokedex.util.LoadImage


class PokemonAdapter :  RecyclerView.Adapter<PokemonViewHolder>(){

    private var pokemons = ArrayList<Pokemon>()
    private lateinit var mListener: IonItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.pokemonrow, parent, false)
        val pokemonViewHolder = PokemonViewHolder(view, mListener)

        return pokemonViewHolder
    }

    fun setOnItemClickListener(listener: IonItemClickListener){
        mListener = listener
    }


    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemonn = pokemons[position]
        holder.pokemontextrow.text = pokemonn.name

        var loadImage  = LoadImage(holder.pokemonimagerow)
        loadImage.execute(pokemonn.image)



    }

    fun addPokemon(newPokemon : Pokemon){
        pokemons.add(newPokemon)

    }

    fun getPokemon(position: Int): Pokemon {
        return pokemons.get(position)
    }


    fun clearPokemon(){
        pokemons.clear()
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }




    interface IonItemClickListener{
        fun onItemClick(position: Int)
    }




} //end of class