package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.google.firebase.firestore.QueryDocumentSnapshot







class Buscador : AppCompatActivity() {

    private lateinit var binding: ActivityBuscadorBinding

    private lateinit var username: String

    //STATE
    private val adapter = PokemonAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuscadorBinding.inflate(layoutInflater)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)

        username = intent.extras?.getString("username")!!


        //Recrear el estado
        val pokemonRecycler = binding.pokemonRecycler
        pokemonRecycler.setHasFixedSize(true)
        pokemonRecycler.layoutManager = LinearLayoutManager(this)
        pokemonRecycler.adapter = adapter

        addPokemonsToRecycler()



        binding.catchBtn.setOnClickListener {
            var namePokemon = binding.catchPokemon.text.toString()

            if (namePokemon != ""){
                namePokemon = namePokemon.lowercase()
                namePokemon = namePokemon.trim()


                lifecycleScope.launch(Dispatchers.IO) {

                    try {
                        namePokemon = namePokemon.trim()
                        val response = HTTPSWebUtilDomi().GETRequest("${Constants.BASE_URL_POKEAPI}api/v2/pokemon/${namePokemon}")

                        val jobj: JsonObject = Gson().fromJson(response, JsonObject::class.java)



                        namePokemon = namePokemon.replaceFirstChar { it.uppercaseChar() }

                        if(checkPokemon(namePokemon) == false){

                            val stats = jobj["stats"].toString()
                            val types = jobj["types"].toString()
                            val images = jobj["sprites"].toString()

                            var newPokemon = createPokemon(namePokemon, images, types ,stats)
                            Firebase.firestore.collection("pokemon").add(newPokemon)
                            withContext(Dispatchers.Main){
                                Toast.makeText(this@Buscador, "${namePokemon} ha sido capturado exitosamente!",Toast.LENGTH_LONG)
                                addPokemonsToRecycler()
                            }
                        } else{
                            withContext(Dispatchers.Main){
                                Toast.makeText(this@Buscador, "El Pokémon ya ha sido capturado antes",Toast.LENGTH_LONG)
                            }
                        }




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

    fun createPokemon(name: String, images: String, types: String, stats: String) : Pokemon{

        var arrayStats = stats.split("},")
        var arrayTypes = types.split("},")

        var beginImage = images.indexOf("front_default")
        var endImage = images.indexOf("front_female")

        var imageUrl = images.subSequence(beginImage, endImage).toString()
        imageUrl = imageUrl.replace("front_default","")
        imageUrl = imageUrl.replace("\":\"","")
        imageUrl = imageUrl.replace("\",\"","")

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

        val newPokemon : Pokemon = Pokemon(
            imageUrl,
            name,
            typesString,
            stats[0].toDouble(),
            stats[1].toDouble(),
            stats[2].toDouble(),
            stats[5].toDouble(),
            username)

        return newPokemon


    }

    fun checkPokemon(pokemonName : String) : Boolean{

        //TODO THIS METHOD DOES NOT WORK
        var exists = false
        val query = Firebase.firestore.collection("pokemon").whereEqualTo("name",pokemonName)
       // query.whereEqualTo("username",username)

        query.get().addOnCompleteListener { task ->

            //Si pokemon existe
            var count = task.result?.size()
            if (task.result?.size() != 0){

                exists = true

            }

        }

        return exists
    }


    fun addPokemonsToRecycler(){

        adapter.clearPokemon()

        val query = Firebase.firestore.collection("pokemon").whereEqualTo("username", username)
        query.get().addOnCompleteListener { task ->
            Log.e(">>>", task.result!!.toString()+" gay")

            for (document in task.result!!) {
                Log.e(">>>", document["name"].toString())

                var newPokemon = Pokemon(
                                document["image"].toString(),
                                document["name"].toString(),
                                document["type"].toString(),
                                document["health"].toString().toDouble(),
                                document["attack"].toString().toDouble(),
                                document["defense"].toString().toDouble(),
                                document["speed"].toString().toDouble(),
                                document["username"].toString())

                adapter.addPokemon(newPokemon)
            }

        }


    }

} //end of class