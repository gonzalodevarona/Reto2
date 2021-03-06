package com.example.pokedex.gui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R

class PokemonViewHolder(itemView: View, listener: PokemonAdapter.IonItemClickListener) :RecyclerView.ViewHolder(itemView){

    //UI controllers
    var pokemontextrow : TextView = itemView.findViewById(R.id.pokemontextrow)
    var pokemonimagerow : ImageView = itemView.findViewById(R.id.pokemonimagerow)

    init{
        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }

} //end of class