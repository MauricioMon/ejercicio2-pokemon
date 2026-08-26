
package com.tuapp.ejercicio2.feature.pokemon.data.remote

import com.tuapp.ejercicio2.feature.pokemon.data.remote.dto.PokemonDetailDto
import com.tuapp.ejercicio2.feature.pokemon.data.remote.dto.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponseDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetailDto
}