package com.lucas.pokedexapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucas.pokedexapp.ui.PokemonAdapter
import com.lucas.pokedexapp.viewmodel.PokemonViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModels()
    private lateinit var adapter: PokemonAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: ImageButton
    private lateinit var buttonClear: ImageButton // <-- novo botão

    private var isLoading = false
    private var offset = 20
    private val limit = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewPokemons)
        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        buttonClear = findViewById(R.id.buttonClear) // <-- atribuição do botão de limpar

        adapter = PokemonAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (!isLoading && lastVisibleItem == totalItemCount - 1) {
                    isLoading = true
                    viewModel.loadMorePokemons(offset) {
                        isLoading = false
                        offset += limit
                    }
                }
            }
        })

        viewModel.pokemonList.observe(this) { pokemons ->
            adapter.updateData(pokemons)
        }

        viewModel.errorMessage.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        buttonSearch.setOnClickListener {
            val query = editTextSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.searchPokemon(query)
            } else {
                Toast.makeText(this, "Digite o nome de um Pokémon", Toast.LENGTH_SHORT).show()
            }
        }

        // ✅ ação do botão de limpar
        buttonClear.setOnClickListener {
            editTextSearch.text.clear()
            recyclerView.scrollToPosition(0)
            offset = 20
            isLoading = false
            viewModel.loadInitialPokemons()
        }

        viewModel.loadInitialPokemons()
    }
}
