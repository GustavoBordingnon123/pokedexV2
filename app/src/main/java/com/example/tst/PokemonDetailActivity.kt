package com.example.tst

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

class PokemonDetailActivity : AppCompatActivity() {

    private val viewModel: PokemonDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        // Configurar views
        val imageView = findViewById<ImageView>(R.id.pokemonImageView)
        val nameTextView = findViewById<TextView>(R.id.pokemonNameTextView)
        val abilitiesTextView = findViewById<TextView>(R.id.pokemonAbilitiesTextView)
        val gameIndexTextView = findViewById<TextView>(R.id.pokemonGameIndexTextView)
        val typesTextView = findViewById<TextView>(R.id.pokemonTypesTextView)
        val heightTextView = findViewById<TextView>(R.id.pokemonHeightTextView)
        val weightTextView = findViewById<TextView>(R.id.pokemonWeightTextView)

        // Recuperar dados do Intent
        val imageUrl = intent.getStringExtra("IMAGE_URL") ?: ""
        val name = intent.getStringExtra("NAME") ?: "Unknown"
        val abilities = intent.getStringExtra("ABILITIES") ?: "Unknown"
        val gameIndex = intent.getIntExtra("GAME_INDEX", 0)
        val types = intent.getStringExtra("TYPES") ?: "Unknown"
        val height = intent.getIntExtra("HEIGHT", 0)
        val weight = intent.getIntExtra("WEIGHT", 0)

        // Configurar ViewModel
        viewModel.setPokemonDetails(imageUrl, name, abilities, gameIndex, types, height, weight)

        // Observar as mudanças nos dados do ViewModel e atualizar a UI
        viewModel.imageUrl.observe(this, Observer {
            Glide.with(this).load(it).into(imageView)
        })
        viewModel.name.observe(this, Observer {
            nameTextView.text = it
        })
        viewModel.abilities.observe(this, Observer {
            abilitiesTextView.text = it
        })
        viewModel.gameIndex.observe(this, Observer {
            gameIndexTextView.text = getString(R.string.game_index, it)
        })
        viewModel.types.observe(this, Observer {
            typesTextView.text = it
        })
        viewModel.height.observe(this, Observer {
            heightTextView.text = getString(R.string.height, it)
        })
        viewModel.weight.observe(this, Observer {
            weightTextView.text = getString(R.string.weight, it)
        })
    }
}
