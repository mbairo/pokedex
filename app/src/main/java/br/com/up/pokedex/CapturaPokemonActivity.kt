package br.com.up.pokedex
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import br.com.up.pokedex.network.Api
import com.squareup.picasso.Picasso
import androidx.appcompat.app.AppCompatActivity

class CapturaPokemonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captura_pokemon)
        supportActionBar!!.hide()

        val nome : TextView = findViewById(R.id.textView_pokeNome)
        val imagem : ImageView = findViewById(R.id.img_pokemon)
        val tipo : TextView = findViewById(R.id.textView_pokeTipo)
        val stats : TextView = findViewById(R.id.textView_pokeStats)
        val habilidades : TextView = findViewById(R.id.textView_pokeHabilidades)
        val movimentos : TextView = findViewById(R.id.textView_pokeMovimentos)

        val id = intent.getStringExtra("id")
        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
        Picasso.get().load(url).into(imagem)

//        - Nome;
//        - Imagem; xxxxx
//        - Tipos;
//        - Stats;
//        - Habilidades;
//        - Movimentos

        Api().getPokemonDetailsById(id!!){
                details ->
            if(details != null){

                var tipoStr = "Tipo: "
                var statsStr = "Status:\n"
                var habilidadeStr = "Abilidades:\n"
                var movimentosStr = "Movimentos:\n"

                nome.text = "Nome: " + details.name

//                details.img.forEach {
//                    imgStr = imgStr + it.img.name + "  "
//                }

                details.types.forEach {
                    tipoStr = tipoStr + it.type.name + "  "
                }
                tipo.text = tipoStr

                details.stats.forEach{
                    statsStr = statsStr + it.stat.name + ": " + it.base_stat + "\n"
                }
                stats.text = statsStr

                details.abilities.forEach {
                    habilidadeStr = habilidadeStr + it.ability.name + "  "
                }
                habilidades.text = habilidadeStr

                details.moves.forEach {
                    movimentosStr = movimentosStr + it.move.name + "\n"
                }
                movimentos.text = movimentosStr
            }
        }
    }
}