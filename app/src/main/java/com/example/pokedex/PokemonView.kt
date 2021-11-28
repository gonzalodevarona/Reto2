package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.databinding.ActivityPokemonViewBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.util.LoadImage

class PokemonView : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonViewBinding

    private lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonViewBinding.inflate(layoutInflater)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)


        pokemon = (intent.extras?.get("pokemon") as Pokemon?)!!

        var loadImage  = LoadImage(binding.imagePokemon)
        loadImage.execute(pokemon.image)

        binding.pokemonName.text = pokemon.name
        binding.attackValue.text = pokemon.attack.toString()
        binding.defenseValue.text = pokemon.defense.toString()
        binding.lifeValue.text = pokemon.health.toString()
        binding.speedValue.text = pokemon.speed.toString()
    }
}