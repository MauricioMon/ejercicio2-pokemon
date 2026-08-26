package com.tuapp.ejercicio2.feature.pokemon.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import com.tuapp.ejercicio2.feature.pokemon.domain.model.Pokemon
import androidx.compose.foundation.layout.fillMaxWidth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    //filtra lo que el usuario quiera
    val filteredList = remember(uiState.pokemonList, uiState.searchQuery) {
        if (uiState.searchQuery.isBlank()) {
            uiState.pokemonList
        } else {
            uiState.pokemonList.filter {
                it.name.contains(uiState.searchQuery, ignoreCase = true)
            }
        }
    }

    // Detecta cuándo el usuario llegó cerca del final de la lista
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex >= filteredList.size - 5
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && filteredList.isNotEmpty()) {
            viewModel.loadNextPage()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            label = { Text("Buscar Pokémon") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null && filteredList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = uiState.errorMessage ?: "Error")
                        Button(onClick = viewModel::retry) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(state = listState) {
                    items(filteredList, key = { it.id }) { pokemon ->
                        PokemonListItem(
                            pokemon = pokemon,
                            onClick = onPokemonClick
                        )
                    }
                    if (uiState.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}