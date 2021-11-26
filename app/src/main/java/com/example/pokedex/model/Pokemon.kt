package com.example.pokedex.model

import java.io.Serializable

data class Pokemon(val image:String, val name:String, val type:String, val health:Double, val attack:Double, val defense:Double, val speed:Double)