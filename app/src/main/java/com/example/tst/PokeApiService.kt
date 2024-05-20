package com.example.tst

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import retrofit2.http.Url

interface PokeApiService {
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonResponse>

    @GET
    fun getPokemonDetail(@Url url: String): Call<PokemonDetailResponse>
}
