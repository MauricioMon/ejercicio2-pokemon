package com.tuapp.ejercicio2.feature.pokemon.domain.repository

import com.tuapp.ejercicio2.feature.pokemon.domain.model.Pokemon
import com.tuapp.ejercicio2.feature.pokemon.domain.model.PokemonDetail

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Result<List<Pokemon>>
    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>
}