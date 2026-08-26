package com.tuapp.ejercicio2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.ejercicio2.feature.pokemon.presentation.detail.PokemonDetailScreen
import com.tuapp.ejercicio2.feature.pokemon.presentation.list.PokemonListScreen

private const val ROUTE_LIST = "pokemon_list"
private const val ROUTE_DETAIL = "pokemon_detail"
private const val ARG_POKEMON_NAME = "pokemonName"

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_LIST
    ) {
        composable(route = ROUTE_LIST) {
            PokemonListScreen(
                onPokemonClick = { pokemon ->
                    navController.navigate("$ROUTE_DETAIL/${pokemon.name}")
                }
            )
        }

        composable(
            route = "$ROUTE_DETAIL/{$ARG_POKEMON_NAME}",
            arguments = listOf(
                navArgument(ARG_POKEMON_NAME) { type = NavType.StringType }
            )
        ) {
            PokemonDetailScreen()
        }
    }
}