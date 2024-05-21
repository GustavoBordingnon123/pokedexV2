package com.example.tst

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    fun getPokemonList(@Query("offset") offset: Int, @Query("limit") limit: Int): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun getPokemonDetail(@Path("name") name: String): Call<PokemonDetailResponse>
}
