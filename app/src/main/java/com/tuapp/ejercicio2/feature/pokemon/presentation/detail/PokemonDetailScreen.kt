package com.tuapp.ejercicio2.feature.pokemon.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tuapp.ejercicio2.feature.pokemon.domain.model.PokemonDetail

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage ?: "Error",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            uiState.pokemonDetail != null -> {
                PokemonDetailContent(detail = uiState.pokemonDetail!!)
            }
        }
    }
}

@Composable
private fun PokemonDetailContent(detail: PokemonDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = detail.imageUrl,
            contentDescription = detail.name,
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = detail.name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = "Altura: ${detail.height / 10.0} m")
        Text(text = "Peso: ${detail.weight / 10.0} kg")

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            items(detail.types) { type ->
                Surface(
                    modifier = Modifier.padding(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = type.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}