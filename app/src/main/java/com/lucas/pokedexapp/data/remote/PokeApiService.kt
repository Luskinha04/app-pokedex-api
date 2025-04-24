package com.lucas.pokedexapp.data.remote

import com.lucas.pokedexapp.data.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    @GET("pokemon/{name}")
    fun getPokemonByName(
        @Path("name") name: String
    ): Call<Pokemon>
}
