package com.example.piandroid.view

import androidx.lifecycle.LiveData
import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro
import com.example.piandroid.model.LivroDao

class LivroRepository(private val db: AppDatabase) {

    //Metodos
    suspend fun inserirLivro(livro: Livro) = db.getLivroDao().inserirLivro(livro)
    suspend fun atualizarLivro(livro: Livro) = db.getLivroDao().atualizarLivro(livro)
    suspend fun deletarLivro(livro: Livro) = db.getLivroDao().deletarLivro(livro)

    fun todosLivros() = db.getLivroDao().todosLivros()

    fun procuraLivro(query: String?) = db.getLivroDao().procuraLivro(query)

}