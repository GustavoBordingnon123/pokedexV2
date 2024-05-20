package com.example.tst

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class tst : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val pokemonList = mutableListOf<PokemonData>()
    private var offset = 0
    private val limit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tst)

        // Recuperar o nome do usuário passado pelo Intent
        val username = intent.getStringExtra("USERNAME")
        val placeholderTextView = findViewById<TextView>(R.id.placeholderTextView)
        placeholderTextView.text = "Bem vindo, $username"

        recyclerView = findViewById(R.id.pokemonRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = PokemonAdapter(pokemonList)
        recyclerView.adapter = adapter

        fetchPokemonData(limit, offset)

        findViewById<Button>(R.id.morePokemon).setOnClickListener {
            offset += limit
            fetchPokemonData(limit, offset)
        }
    }

    private fun fetchPokemonData(limit: Int, offset: Int) {
        ApiClient.apiService.getPokemonList(limit, offset).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (response.isSuccessful) {
                    val pokemonResponse = response.body()
                    pokemonResponse?.results?.forEach { result ->
                        ApiClient.apiService.getPokemonDetail(result.url).enqueue(object : Callback<PokemonDetailResponse> {
                            override fun onResponse(call: Call<PokemonDetailResponse>, response: Response<PokemonDetailResponse>) {
                                if (response.isSuccessful) {
                                    val pokemonDetail = response.body()
                                    val types = pokemonDetail?.types?.joinToString(", ") { it.type.name }
                                    val imageUrl = pokemonDetail?.sprites?.front_default ?: ""
                                    pokemonList.add(PokemonData(0, result.name.capitalize(), types ?: "", "", imageUrl))
                                    adapter.notifyDataSetChanged()
                                }
                            }

                            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                                Log.e("API_ERROR", "Failed to fetch Pokemon detail", t)
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.e("API_ERROR", "Failed to fetch data", t)
            }
        })
    }
}
