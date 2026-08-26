package com.tuapp.ejercicio2.feature.pokemon.presentation.detail

import com.tuapp.ejercicio2.feature.pokemon.domain.model.PokemonDetail

data class PokemonDetailUiState(
    val pokemonDetail: PokemonDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)