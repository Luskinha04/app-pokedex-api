package com.lucas.pokedexapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.pokedexapp.data.model.Pokemon
import com.lucas.pokedexapp.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun searchPokemon(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val call = RetrofitClient.instance.getPokemonByName(name.lowercase())
            call.enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _pokemonList.postValue(listOf(it))
                        }
                    } else {
                        _errorMessage.postValue("Pokémon não encontrado.")
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                    _errorMessage.postValue("Erro: ${t.message}")
                }
            })
        }
    }

    fun loadInitialPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemons = mutableListOf<Pokemon>()
            for (i in 1..20) {
                val response = RetrofitClient.instance.getPokemonByName(i.toString()).execute()
                if (response.isSuccessful) {
                    response.body()?.let { pokemons.add(it) }
                }
            }
            _pokemonList.postValue(pokemons)
        }
    }

    fun loadMorePokemons(offset: Int, onLoaded: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newPokemons = mutableListOf<Pokemon>()
                for (i in offset + 1..offset + 20) {
                    val response = RetrofitClient.instance.getPokemonByName(i.toString()).execute()
                    if (response.isSuccessful) {
                        response.body()?.let {
                            newPokemons.add(it)
                        }
                    }
                }
                val updatedList = _pokemonList.value.orEmpty().toMutableList().apply {
                    addAll(newPokemons)
                }
                _pokemonList.postValue(updatedList)
            } catch (e: Exception) {
                _errorMessage.postValue("Erro ao carregar mais Pokémon: ${e.message}")
            } finally {
                onLoaded()
            }
        }
    }
}
