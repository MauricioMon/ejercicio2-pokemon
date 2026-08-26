package com.tuapp.ejercicio2.feature.pokemon.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val types: List<String>
)