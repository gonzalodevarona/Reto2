package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pokedex.databinding.ActivityBuscadorBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.User
import com.example.pokedex.util.Constants
import com.example.pokedex.util.HTTPSWebUtilDomi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.gson.JsonObject




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
                    val response = HTTPSWebUtilDomi().GETRequest("${Constants.BASE_URL_POKEAPI}api/v2/pokemon/${namePokemon}")

                    try {
                        val response = HTTPSWebUtilDomi().GETRequest("${Constants.BASE_URL_POKEAPI}api/v2/pokemon/${namePokemon}")
                        val json = Gson().toJson(response)
                        val jobj: JsonObject = Gson().fromJson(response, JsonObject::class.java)

                        val stats = jobj["stats"].toString()
                        val types = jobj["types"].toString()

                        createPokemon(namePokemon, types ,stats)




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

    fun createPokemon(name: String, types: String, stats: String) {
        //TODO
        var arrayStats = stats.split("},")
        var arrayTypes = types.split("},")
        var  i = 0

        val stats: IntArray = intArrayOf(0, 0, 0, 0, 0, 0)

        var typesString = ""

        while (i<arrayStats.size){
            var stat = arrayStats[i]
            var begin = stat.indexOf(":")
            var end = stat.indexOf(",")

            stats[i] = stat.subSequence(begin+1, end).toString().toInt()
            i++

        }

        i = 0

        while (i<arrayTypes.size){
            var type = arrayTypes[i]
            var begin = type.indexOf("\":\"")
            var end = type.indexOf("\",")

            typesString = typesString+type.subSequence(begin+3, end).toString().replaceFirstChar { it.uppercaseChar() }
            typesString = typesString+"-"

            i++

        }

        typesString = typesString.subSequence(0, typesString.length-1).toString()
        Log.e(">>>",typesString)

        //TODO
        //Need to process image
        val newPokemon : Pokemon = Pokemon("",
            name.replaceFirstChar { it.uppercaseChar() },
            typesString,
            stats[0].toDouble(),
            stats[1].toDouble(),
            stats[2].toDouble(),
            stats[5].toDouble() )



    }

    fun checkPokemon() : Boolean{
        //TODO
        val query = Firebase.firestore.collection("trainers").whereEqualTo("username", username)
        query.get().addOnCompleteListener { task ->

            //Si usuario no existe
            if (task.result?.size() == 0){

                Firebase.firestore.collection("trainers").add(User(username))
                startActivity(intent)

            } else{

                //Si usuario existe
                startActivity(intent)
            }

        }

        return true
    }

} //end of class