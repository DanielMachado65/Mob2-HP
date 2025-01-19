package br.com.mob2_hp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mob2_hp.adapter.ProfessorsAdapter
import br.com.mob2_hp.utils.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListProfessorsActivity : AppCompatActivity() {

    private lateinit var adapter: ProfessorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_professors)

        val recyclerView = findViewById<RecyclerView>(R.id.rvProfessors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProfessorsAdapter(emptyList())
        recyclerView.adapter = adapter

        fetchProfessors()
    }

    private fun fetchProfessors() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getStaff()

                // Log para depuração
                Log.d("API_RESPONSE", "Professores recebidos: ${response.size}")
                response.forEach { Log.d("API_RESPONSE", "Professor: ${it.name}") }

                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isNotEmpty()) {
                        adapter.updateData(response)
                    } else {
                        // Se a lista estiver vazia, exibir algo opcionalmente
                        adapter.updateData(emptyList())
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    Log.e("API_ERROR", "Erro ao buscar professores", e)
                    adapter.updateData(emptyList()) // Lista vazia em caso de erro
                }
            }
        }
    }

}