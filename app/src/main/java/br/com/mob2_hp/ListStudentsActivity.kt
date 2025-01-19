package br.com.mob2_hp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mob2_hp.adapter.StudentsAdapter
import br.com.mob2_hp.utils.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListStudentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_students)

        val houseGroup = findViewById<RadioGroup>(R.id.rgHouses)
        val recyclerView = findViewById<RecyclerView>(R.id.rvStudents)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<Button>(R.id.btnFetchStudents).setOnClickListener {
            val selectedHouse = when (houseGroup.checkedRadioButtonId) {
                R.id.rbGryffindor -> "Gryffindor"
                R.id.rbSlytherin -> "Slytherin"
                R.id.rbHufflepuff -> "Hufflepuff"
                R.id.rbRavenclaw -> "Ravenclaw"
                else -> null
            }

            if (selectedHouse != null) {
                fetchStudents(selectedHouse, recyclerView)
            }
        }
    }

    private fun fetchStudents(house: String, recyclerView: RecyclerView) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val emptyMessage = findViewById<TextView>(R.id.tvEmptyMessage)

        progressBar.visibility = View.VISIBLE
        emptyMessage.visibility = View.GONE

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getStudents(house)
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    if (response.isNotEmpty()) {
                        emptyMessage.visibility = View.GONE
                        recyclerView.adapter = StudentsAdapter(response)
                    } else {
                        emptyMessage.visibility = View.VISIBLE
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    progressBar.visibility = View.GONE
                    emptyMessage.text = "Erro ao carregar dados."
                    emptyMessage.visibility = View.VISIBLE
                }
            }
        }
    }

}