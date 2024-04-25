package com.example.piandroid.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.piandroid.databinding.ItemLivroBinding
import com.example.piandroid.model.Livro

class LivroAdapter (
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
}

