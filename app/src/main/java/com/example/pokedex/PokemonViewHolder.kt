package com.example.pokedex

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PokemonViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    //UI controllers
    var pokemontextrow : TextView = itemView.findViewById(R.id.pokemontextrow)
    var pokemonimagerow : ImageView = itemView.findViewById(R.id.pokemonimagerow)

    init{

    }
} //end of class