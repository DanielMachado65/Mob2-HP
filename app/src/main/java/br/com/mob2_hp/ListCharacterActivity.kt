package br.com.mob2_hp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mob2_hp.adapter.CharactersAdapter
import br.com.mob2_hp.utils.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListCharactersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_characters)

        val rvCharacters = findViewById<RecyclerView>(R.id.rvCharacters)

        rvCharacters.layoutManager = LinearLayoutManager(this)

        // Configurar RecyclerView com a lista de todos os personagens
        fetchAllCharacters(rvCharacters)
    }

    fun onClickFetchCharacter(view: View) {
        val etCharacterId = findViewById<EditText>(R.id.etCharacterId)

        val id = etCharacterId.text.toString().trim()
        if (id.isNotEmpty()) {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = ApiClient.service.getCharacter(id) // Agora retorna uma lista
                    withContext(Dispatchers.Main) {
                        if (response.isNotEmpty()) {
                            val character = response[0] // Pega o primeiro elemento
                            startActivity(
                                Intent(this@ListCharactersActivity, CharacterProfileActivity::class.java).apply {
                                    putExtra("name", character.name)
                                    putExtra("house", character.house)
                                    putExtra("actor", character.actor)
                                    putExtra("image", character.image)
                                }
                            )
                        } else {
                            Toast.makeText(this@ListCharactersActivity, "Personagem não encontrado.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("onClickFetchCharacter", "Erro ao buscar personagem", e)
                        Toast.makeText(this@ListCharactersActivity, "Erro ao buscar personagem. Verifique o ID.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Por favor, insira um ID válido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchAllCharacters(recyclerView: RecyclerView) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getAllCharacters()
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = CharactersAdapter(response) { character ->
                        // Redirecionar para CharacterProfileActivity
                        Log.d("API", character.toString());
                        val intent = Intent(this@ListCharactersActivity, CharacterProfileActivity::class.java)
                        intent.putExtra("name", character.name)
                        intent.putExtra("house", character.house)
                        intent.putExtra("actor", character.actor)
                        intent.putExtra("image", character.image)
                        startActivity(intent)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
