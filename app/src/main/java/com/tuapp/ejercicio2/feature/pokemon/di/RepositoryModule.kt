package com.tuapp.ejercicio2.feature.pokemon.di

import com.tuapp.ejercicio2.feature.pokemon.data.repository.PokemonRepositoryImpl
import com.tuapp.ejercicio2.feature.pokemon.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository
}