package com.example.tst

import android.content.Intent
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
    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var placeholderTextView: TextView
    private lateinit var morePokemonButton: Button
    private var pokemonList = mutableListOf<PokemonData>()
    private var offset = 0
    private val limit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recuperar nome do usuário
        val sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "usuario")

        // Configurar TextView de boas-vindas
        placeholderTextView = findViewById(R.id.placeholderTextView)
        placeholderTextView.text = "Bem vindo $username"

        recyclerView = findViewById(R.id.pokemonRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        pokemonAdapter = PokemonAdapter(pokemonList) { pokemonData ->
            val intent = Intent(this, PokemonDetailActivity::class.java).apply {
                putExtra("IMAGE_URL", pokemonData.imageUrl)
                putExtra("NAME", pokemonData.name)
                putExtra("ABILITIES", pokemonData.abilities)
                putExtra("GAME_INDEX", pokemonData.gameIndex)
                putExtra("TYPES", pokemonData.types)
                putExtra("HEIGHT", pokemonData.height)
                putExtra("WEIGHT", pokemonData.weight)
            }
            startActivity(intent)
        }
        recyclerView.adapter = pokemonAdapter

        morePokemonButton = findViewById(R.id.morePokemon)
        morePokemonButton.setOnClickListener {
            loadPokemonData()
        }

        // Carregar dados iniciais
        loadPokemonData()
    }

    private fun loadPokemonData() {
        val apiService = RetrofitClientInstance.retrofitInstance.create(PokeApiService::class.java)
        val call = apiService.getPokemonList(offset, limit)

        call.enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (response.isSuccessful) {
                    val pokemonResults = response.body()?.results ?: emptyList()
                    for (pokemon in pokemonResults) {
                        val detailCall = apiService.getPokemonDetail(pokemon.name)
                        detailCall.enqueue(object : Callback<PokemonDetailResponse> {
                            override fun onResponse(call: Call<PokemonDetailResponse>, response: Response<PokemonDetailResponse>) {
                                if (response.isSuccessful) {
                                    response.body()?.let { detail ->
                                        val pokemonData = PokemonData(
                                            id = detail.id,
                                            name = detail.name,
                                            type1 = detail.types[0].type.name,
                                            type2 = if (detail.types.size > 1) detail.types[1].type.name else "",
                                            imageUrl = detail.sprites.front_default ?: "",
                                            abilities = detail.abilities.joinToString(", ") { it.ability.name },
                                            gameIndex = detail.game_indices.firstOrNull()?.game_index ?: 0,
                                            types = detail.types.joinToString(", ") { it.type.name },
                                            height = detail.height,
                                            weight = detail.weight
                                        )
                                        pokemonList.add(pokemonData)
                                        pokemonAdapter.notifyItemInserted(pokemonList.size - 1)
                                    }
                                }
                            }

                            override fun onFailure(call: Call<PokemonDetailResponse>, t: Throwable) {
                                Log.e("API_CALL", "Error fetching pokemon details", t)
                            }
                        })
                    }
                    offset += limit
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.e("API_CALL", "Error fetching pokemon list", t)
            }
        })
    }
}
