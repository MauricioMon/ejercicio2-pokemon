package com.tuapp.ejercicio2.feature.pokemon.data.repository

import com.tuapp.ejercicio2.feature.pokemon.data.remote.PokemonApi
import com.tuapp.ejercicio2.feature.pokemon.data.remote.dto.PokemonDetailDto
import com.tuapp.ejercicio2.feature.pokemon.data.remote.dto.PokemonResultDto
import com.tuapp.ejercicio2.feature.pokemon.domain.model.Pokemon
import com.tuapp.ejercicio2.feature.pokemon.domain.model.PokemonDetail
import com.tuapp.ejercicio2.feature.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Result<List<Pokemon>> {
        return try {
            val response = api.getPokemonList(limit, offset)
            val pokemonList = response.results.map { it.toDomain() }
            Result.success(pokemonList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        return try {
            val response = api.getPokemonDetail(name)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun PokemonResultDto.toDomain(): Pokemon {
        val id = url.trimEnd('/').substringAfterLast('/').toInt()
        return Pokemon(
            id = id,
            name = name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
        )
    }

    private fun PokemonDetailDto.toDomain(): PokemonDetail {
        return PokemonDetail(
            id = id,
            name = name,
            imageUrl = sprites.frontDefault.orEmpty(),
            height = height,
            weight = weight,
            types = types.map { it.type.name }
        )
    }
}