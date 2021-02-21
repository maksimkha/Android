package com.example.tabatatimer

import java.io.Serializable


data class Sequence(var name:String, var color: String, var phases: ArrayList<Phase>) : Serializable {
}