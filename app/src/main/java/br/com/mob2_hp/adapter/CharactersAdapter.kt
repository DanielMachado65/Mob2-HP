package br.com.mob2_hp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mob2_hp.R
import br.com.mob2_hp.model.Character
import com.bumptech.glide.Glide

class CharactersAdapter(
    private val characters: List<Character>,
    private val onItemClick: (Character) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idTextView: TextView = view.findViewById(R.id.tvIdCharacterId)
        val nameTextView: TextView = view.findViewById(R.id.tvCharacterName)
        val houseTextView: TextView = view.findViewById(R.id.tvCharacterHouse)
        val characterImageView: ImageView = view.findViewById(R.id.ivCharacterImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.idTextView.text = "#${character.id}"
        holder.nameTextView.text = character.name
        holder.houseTextView.text = character.house ?: "Sem Casa"
        holder.itemView.setOnClickListener { onItemClick(character) }

        // Carrega a imagem usando Glide (ou qualquer biblioteca de carregamento de imagens)
        Glide.with(holder.characterImageView.context)
            .load(character.image) // URL da imagem do personagem
            .placeholder(R.drawable.placeholder_image) // Placeholder enquanto carrega
            .error(R.drawable.error_image) // Imagem caso ocorra erro
            .into(holder.characterImageView)
    }

    override fun getItemCount() = characters.size
}