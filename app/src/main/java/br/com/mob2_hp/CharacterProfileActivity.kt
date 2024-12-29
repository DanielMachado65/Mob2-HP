package br.com.mob2_hp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide


class CharacterProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_profile)

        val name = intent.getStringExtra("name")
        val house = intent.getStringExtra("house")
        val actor = intent.getStringExtra("actor")
        val image = intent.getStringExtra("image")

        findViewById<TextView>(R.id.tvCharacterName).text = "Nome: $name"
        findViewById<TextView>(R.id.tvCharacterHouse).text = "Casa: ${house ?: "Desconhecida"}"
        findViewById<TextView>(R.id.tvCharacterActor).text = "Ator: ${actor ?: "Desconhecido"}"

        Glide.with(this)
            .load(image)
            .placeholder(R.drawable.placeholder_image) // Substitua pelo recurso correto
            .into(findViewById(R.id.ivCharacterImage))
    }
}