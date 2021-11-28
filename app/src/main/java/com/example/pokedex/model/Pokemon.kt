package com.example.pokedex.model

import java.io.Serializable

data class Pokemon(val image:String = "",
                   val name:String = "",
                   val type:String = "",
                   val health:Double = 0.0,
                   val attack:Double = 0.0,
                   val defense:Double = 0.0,
                   val speed:Double = 0.0,
                   val username:String = "",) : Serializable