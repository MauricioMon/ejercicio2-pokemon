// feature/pokemon/presentation/detail/PokemonDetailViewModel.kt
package com.tuapp.ejercicio2.feature.pokemon.presentation.detail

import androidx.lifecycle.SavedStateHandle
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

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        val pokemonName: String = savedStateHandle.get<String>("pokemonName") ?: ""
        if (pokemonName.isNotBlank()) {
            loadPokemonDetail(pokemonName)
        }
    }

    private fun loadPokemonDetail(name: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            val result = repository.getPokemonDetail(name)

            result.onSuccess { detail ->
                _uiState.update {
                    it.copy(isLoading = false, pokemonDetail = detail)
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Error desconocido"
                    )
                }
            }
        }
    }
}