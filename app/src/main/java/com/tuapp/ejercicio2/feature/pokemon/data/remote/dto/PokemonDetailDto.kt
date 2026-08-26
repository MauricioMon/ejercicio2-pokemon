package com.tuapp.ejercicio2.feature.pokemon.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDetailDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: SpritesDto,
    val types: List<TypeSlotDto>
)

data class SpritesDto(
    @SerializedName("front_default")
    val frontDefault: String?
)

data class TypeSlotDto(
    val slot: Int,
    val type: TypeDto
)

data class TypeDto(
    val name: String,
    val url: String
)