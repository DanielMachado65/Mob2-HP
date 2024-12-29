package br.com.mob2_hp

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
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
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getStudents(house)
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = StudentsAdapter(response)
                }
            } catch (e: Exception) {
                // Tratar erros se necessário
            }
        }
    }
}