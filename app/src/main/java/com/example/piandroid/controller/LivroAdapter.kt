package com.example.piandroid.controller

import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.piandroid.R
import com.example.piandroid.databinding.ItemLivroBinding
import com.example.piandroid.model.Livro


class LivroAdapter(
    private val onEdit: (Livro) -> Unit,
    private val onDelete: (Livro) -> Unit,
    private val onFavorite: (Livro) -> Unit,
) : ListAdapter<Livro, LivroAdapter.LivroViewHolder>(LivroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val binding = ItemLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LivroViewHolder(binding, onEdit, onDelete, onFavorite)
    }


    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = getItem(position)
        holder.bind(livro)
        /*
                // Verifica se o livro é favorito e define o background de acordo
                if (livro.favorito == 1) {
                    holder.itemView.setBackgroundColor(Color.YELLOW)
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT)
                }*/
    }

    class LivroViewHolder(
        private val binding: ItemLivroBinding,
        private val onEdit: (Livro) -> Unit,
        private val onDelete: (Livro) -> Unit,
        private val onFavorite: (Livro) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(livro: Livro) {
            binding.apply {
                txtNomeLivro.text = livro.nome
                txtIdLivro.text = livro.id.toString()
                txtPaginas.text = livro.paginas.toString()
                txtPaginasLidas.text = livro.paginasLidas.toString()


                if (livro.favorito == 1) {//se favorito deixa amarelo
                    val vermelho = ContextCompat.getColor(root.context, R.color.vermelho)
                    //val amareloTransparente = Color.argb(128, Color.red(amarelo), Color.green(amarelo), Color.blue(amarelo))

                    imgFavorito.setColorFilter(vermelho)
                    txtNomeLivro.setTypeface(null, Typeface.BOLD)
                    txtNomeLivro.isAllCaps = true


                }

                //Porcentagem
                val porcentagem =
                    (livro.paginasLidas.toString().toDouble() / livro.paginas.toString()
                        .toDouble()) * 100
                progressBar.progress = porcentagem.toInt()

                imgBtnEditar.setOnClickListener { onEdit(livro) }
                imgBtnDeletar.setOnClickListener { onDelete(livro) }
                imgFavorito.setOnClickListener { onFavorite(livro) }
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
