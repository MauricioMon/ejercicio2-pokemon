package com.tuapp.ejercicio2.feature.pokemon.presentation.list

import com.tuapp.ejercicio2.feature.pokemon.domain.model.Pokemon

data class PokemonListUiState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val currentOffset: Int = 0,
    val endReached: Boolean = false
)