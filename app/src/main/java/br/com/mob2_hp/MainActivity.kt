package br.com.mob2_hp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botão para Listar Personagem por ID
        val btnListCharacter: Button = findViewById(R.id.btnListCharacter)
        btnListCharacter.setOnClickListener {
            val intent = Intent(this, ListCharactersActivity::class.java)
            startActivity(intent)
        }

        // Botão para Listar Professores
        val btnListProfessors: Button = findViewById(R.id.btnListProfessors)
        btnListProfessors.setOnClickListener {
            val intent = Intent(this, ListProfessorsActivity::class.java)
            startActivity(intent)
        }

        // Botão para Listar Estudantes por Casa
        val btnListStudents: Button = findViewById(R.id.btnListStudents)
        btnListStudents.setOnClickListener {
            val intent = Intent(this, ListStudentsActivity::class.java)
            startActivity(intent)
        }

        // Botão para Sair
        val btnExit: Button = findViewById(R.id.btnExit)
        btnExit.setOnClickListener {
            finish()
        }
    }
}