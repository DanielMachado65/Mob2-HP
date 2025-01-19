package br.com.mob2_hp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
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
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val id = etCharacterId.text.toString().trim()
        if (id.isNotEmpty()) {
            progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = ApiClient.service.getCharacter(id)
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        if (response.isNotEmpty()) {
                            val character = response[0]
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
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@ListCharactersActivity, "Erro ao buscar personagem. Verifique o ID.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "Por favor, insira um ID válido.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun fetchAllCharacters(recyclerView: RecyclerView) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val emptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)

        // Exibir o loader e esconder outras views
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyMessage.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getAllCharacters()
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE

                    if (response.isNotEmpty()) {
                        emptyMessage.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        recyclerView.adapter = CharactersAdapter(response) { character ->
                            // Redirecionar para CharacterProfileActivity
                            val intent = Intent(this@ListCharactersActivity, CharacterProfileActivity::class.java)
                            intent.putExtra("name", character.name)
                            intent.putExtra("house", character.house)
                            intent.putExtra("actor", character.actor)
                            intent.putExtra("image", character.image)
                            startActivity(intent)
                        }
                    } else {
                        recyclerView.visibility = View.GONE
                        emptyMessage.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    emptyMessage.text = "Erro ao carregar personagens."
                    emptyMessage.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }
    }


}
