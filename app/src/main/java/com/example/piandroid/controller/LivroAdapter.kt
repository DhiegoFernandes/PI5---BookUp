package com.example.piandroid.controller

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.piandroid.R
import com.example.piandroid.databinding.ItemLivroBinding
import com.example.piandroid.model.Livro
import com.example.piandroid.ui.LivroFragmento
import com.example.piandroid.ui.Livros

class LivroAdapter : RecyclerView.Adapter<LivroAdapter.LivroViewHolder>(){

                                            //################
    class LivroViewHolder(val itemBinding: ItemLivroBinding): RecyclerView.ViewHolder(itemBinding.root)

    // Determina diferença entre duas classes
    private val differCallback = object: DiffUtil.ItemCallback<Livro>(){
        override fun areItemsTheSame(oldItem: Livro, newItem: Livro): Boolean {
            return oldItem.id == newItem.id && //Compara id antigo com o novo
                    oldItem.nome == newItem.nome &&
                    oldItem.paginas == newItem.paginas &&
                    oldItem.paginasLidas == newItem.paginasLidas
        }

        override fun areContentsTheSame(oldItem: Livro, newItem: Livro): Boolean {
            return oldItem.id == newItem.id
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        return LivroViewHolder(
            //################
            ItemLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livroAtual = differ.currentList[position]

        holder.itemBinding.txtIdLivro.text = livroAtual.id.toString()
        holder.itemBinding.txtNomeLivro.text = livroAtual.nome
        //### LIVRO DEVE EXIBIR A PORCENTAGEM #############
        holder.itemBinding.txtPorcentagem.text = livroAtual.paginas.toString()

        holder.itemView.setOnClickListener{
            it.findNavController().navigate(R.id.action_livros_to_livro)
        }
    }
}

/*class LivroAdapter (


    private val livroList: List<Livro>
): RecyclerView.Adapter<LivroAdapter.MyViewHolder>(){

    inner class MyViewHolder(val binding: ItemLivroBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        return MyViewHolder(ItemLivroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount() = livroList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val livro = livroList[position]

        //txtNomeLivro recebe nome
        holder.binding.txtNomeLivro.text = livro.nome
        //txtNomeLivro recebe id
        holder.binding.txtIdLivro.text = livro.id.toString()
    }
}*/

