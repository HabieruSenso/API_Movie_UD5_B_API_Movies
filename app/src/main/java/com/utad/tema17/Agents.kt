package com.utad.tema17


import com.google.gson.annotations.SerializedName

data class Agents(
    val `data`: List<Data>,
    val status: Int
) {
    data class Data(
        val abilities: List<Ability>,
        val displayIconSmall: String,
        val displayName: String,
    ) {
        data class Ability(
            val description: String,
            val displayIcon: String,
            val displayName: String,
            val slot: String
        )
    }
}