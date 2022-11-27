package br.com.up.pokedex

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.up.pokedex.adapter.PokeAdapter
import br.com.up.pokedex.model.Pokemon
import com.google.android.material.textfield.TextInputEditText
import br.com.up.pokedex.network.Api


class MainActivity : AppCompatActivity() {

    private var pokemonList: List<Pokemon>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        val recyclerView : RecyclerView =
            findViewById(R.id.recycler_pokemons)

        val inputTextSearchPokemon : TextInputEditText =
            findViewById(R.id.text_input_search_pokemon)

        inputTextSearchPokemon.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(value: CharSequence?,p1: Int, p2: Int, p3: Int) {

                    if(pokemonList != null){
                        val filteredList = pokemonList!!
                            .filter { pokemon->
                                pokemon.name.contains(value!!)
                            }
                        recyclerView.adapter = PokeAdapter(filteredList)
                        { pokemon ->
                            callPokemonDetail(pokemon)
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {}
            }
        )

        recyclerView.layoutManager =
            GridLayoutManager(this, 3)

        Api().getPokemons{ pokemons ->
            pokemonList = pokemons
            recyclerView.adapter  =
                PokeAdapter(pokemons!!) { pokemon ->
                    callPokemonDetail(pokemon)
                }
        }
    }

    fun callPokemonDetail(pokemon:Pokemon){

        val intent = Intent(this,CapturaPokemonActivity::class.java)
        var id = pokemon.url.dropLast(1)
        id = id.takeLastWhile { it.isDigit() }

        intent.putExtra("id",id)
        startActivity(intent)
    }

}