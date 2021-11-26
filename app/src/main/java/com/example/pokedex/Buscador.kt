package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.ActivityBuscadorBinding
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.util.Constants
import com.example.pokedex.util.HTTPSWebUtilDomi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            lifecycleScope.launch(Dispatchers.IO) {
                HTTPSWebUtilDomi().GETRequest("${Constants.BASE_URL_POKEAPI}/${binding.catchPokemon.text}")
            }
        }




    }

} //end of class