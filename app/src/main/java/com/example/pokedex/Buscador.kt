package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.ActivityBuscadorBinding
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.util.Constants
import com.example.pokedex.util.HTTPSWebUtilDomi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Buscador : AppCompatActivity() {

    private lateinit var binding: ActivityBuscadorBinding

    private lateinit var username: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscadorBinding.inflate(layoutInflater)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)

        username = intent.extras?.getString("username")!!



        binding.catchBtn.setOnClickListener {
            var namePokemon = binding.catchPokemon.text.toString()

            if (namePokemon != ""){
                namePokemon = namePokemon.lowercase()
                namePokemon = namePokemon.trim()

                lifecycleScope.launch(Dispatchers.IO) {

                    try {
                        val response = HTTPSWebUtilDomi().GETRequest("${Constants.BASE_URL_POKEAPI}api/v2/pokemon/${namePokemon}")
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main){
                            Toast.makeText(this@Buscador, "Error: El nombre del Pokémon a capturar es incorrecto",Toast.LENGTH_LONG)
                        }

                    }

                }

            } else{
                Toast.makeText(this, "Error: El nombre del Pokémon no puede estar vacio",Toast.LENGTH_LONG)

            }
        }




    }

} //end of class