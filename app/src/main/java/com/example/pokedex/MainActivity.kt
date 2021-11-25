package com.example.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)


        binding.accessPokedex.setOnClickListener {
            var username = binding.username.text.toString()
            username = username.trim()

            if(username != ""){
                val intent = Intent(this, Buscador::class.java).apply {
                    putExtra("username", username)
                }

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




            } else{
                Toast.makeText(this, "Error: No has digitado el nombre del entrenador Pokémon",Toast.LENGTH_LONG).show()
            }

        }


    }

} //end of class