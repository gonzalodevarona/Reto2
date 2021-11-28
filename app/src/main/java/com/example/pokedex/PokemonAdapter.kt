package com.example.pokedex

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.model.Pokemon
import java.io.IOException


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

        var loadImage  = LoadImage(holder.pokemonimagerow)
        loadImage.execute(pokemonn.image)



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

    private class LoadImage(val imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {




        override fun doInBackground(vararg p0: String?): Bitmap {
            var urlLink = p0[0]
            var bitmap : Bitmap? = null

            try {
                var inputStream = java.net.URL(urlLink).openStream()
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch ( e: IOException){
                e.printStackTrace()
            }


            return bitmap!!
        }

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }




} //end of class