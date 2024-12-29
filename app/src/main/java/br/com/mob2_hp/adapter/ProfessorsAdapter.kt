package br.com.mob2_hp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mob2_hp.R
import br.com.mob2_hp.model.Character


class ProfessorsAdapter(private var professors: List<Character>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // View types para diferenciar entre item de dado e mensagem vazia
    private val VIEW_TYPE_DATA = 1
    private val VIEW_TYPE_EMPTY = 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvProfessorName)
    }

    class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emptyMessageTextView: TextView = view.findViewById(R.id.tvEmptyMessage)
    }

    override fun getItemViewType(position: Int): Int {
        return if (professors.isEmpty()) VIEW_TYPE_EMPTY else VIEW_TYPE_DATA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DATA) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_professor, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_empty, parent, false)
            EmptyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val professor = professors[position]
            holder.nameTextView.text = professor.name
        } else if (holder is EmptyViewHolder) {
            holder.emptyMessageTextView.text = "Nenhum professor encontrado."
        }
    }

    override fun getItemCount(): Int {
        return if (professors.isEmpty()) 1 else professors.size
    }

    fun updateData(newProfessors: List<Character>) {
        professors = newProfessors
        notifyDataSetChanged()
    }
}
