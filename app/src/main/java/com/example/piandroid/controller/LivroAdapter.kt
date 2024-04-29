package com.example.piandroid.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.piandroid.databinding.ItemLivroBinding
import com.example.piandroid.model.Livro

class LivroAdapter (
    private val onEdit: (Livro) -> Unit,
    private val onDelete: (Livro) -> Unit
): ListAdapter<Livro, LivroAdapter.LivroViewHolder>(LivroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val binding = ItemLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LivroViewHolder(binding, onEdit, onDelete)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = getItem(position)
        holder.bind(livro)
    }

    class LivroViewHolder(
        private val binding: ItemLivroBinding,
        private val onEdit: (Livro) -> Unit,
        private val onDelete: (Livro) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(livro: Livro) {
            binding.apply {
                txtNomeLivro.text = livro.nome
                txtIdLivro.text = livro.id.toString()
                txtPaginas.text = livro.paginas.toString()
                txtPaginasLidas.text = livro.paginasLidas.toString()

                val porcentagem = (livro.paginasLidas.toString().toDouble() / livro.paginas.toString().toDouble()) * 100
                progressBar.progress = porcentagem.toInt()

                imgBtnEditar.setOnClickListener { onEdit(livro) }
                imgBtnDeletar.setOnClickListener { onDelete(livro) }
            }
        }
    }
}

class LivroDiffCallback : DiffUtil.ItemCallback<Livro>() {
    override fun areItemsTheSame(oldItem: Livro, newItem: Livro): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Livro, newItem: Livro): Boolean {
        return oldItem.id == newItem.id
    }
}
