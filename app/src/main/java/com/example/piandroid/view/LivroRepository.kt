package com.example.piandroid.view

import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro

class LivroRepository(private val db: AppDatabase) {

    //Metodos
    suspend fun inserirLivro(livro: Livro) = db.livroDao().inserirLivro(livro)
    suspend fun atualizarLivro(livro: Livro) = db.livroDao().atualizarLivro(livro)
    suspend fun deletarLivro(livro: Livro) = db.livroDao().deletarLivro(livro)

    fun todosLivros() = db.livroDao().todosLivros()

    fun procuraLivro(query: String?) = db.livroDao().procuraLivro(query)

}