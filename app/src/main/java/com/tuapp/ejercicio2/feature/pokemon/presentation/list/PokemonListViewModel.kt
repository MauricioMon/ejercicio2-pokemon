package com.tuapp.ejercicio2.feature.pokemon.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.ejercicio2.feature.pokemon.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PAGE_SIZE = 20

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListUiState())
    val uiState: StateFlow<PokemonListUiState> = _uiState.asStateFlow()

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        val state = _uiState.value
        if (state.isLoading || state.isLoadingMore || state.endReached) return

        val isFirstPage = state.pokemonList.isEmpty()

        _uiState.update {
            it.copy(
                isLoading = isFirstPage,
                isLoadingMore = !isFirstPage,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val result = repository.getPokemonList(
                limit = PAGE_SIZE,
                offset = state.currentOffset
            )

            result.onSuccess { newPokemon ->
                _uiState.update {
                    it.copy(
                        pokemonList = it.pokemonList + newPokemon,
                        currentOffset = it.currentOffset + PAGE_SIZE,
                        isLoading = false,
                        isLoadingMore = false,
                        endReached = newPokemon.isEmpty()
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = error.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun retry() {
        loadNextPage()
    }
}