package com.example.piandroid.view

import androidx.lifecycle.LiveData
import com.example.piandroid.model.Livro
import com.example.piandroid.model.LivroDao

class LivroRepository(private val livroDao: LivroDao) {

    val todosLivros: LiveData<List<Livro>> = livroDao.todosLivros()
    val todosLivrosOrdPorFavoritos: LiveData<List<Livro>> = livroDao.todosLivrosOrdPorFavoritos()

    //Metodos
    suspend fun inserirLivro(livro: Livro) {
        livroDao.inserirLivro(livro)

    }
    suspend fun atualizarLivro(livro: Livro) {
        livroDao.atualizarLivro(livro)
    }

    suspend fun deletarLivro(livro: Livro) {
        livroDao.deletarLivro(livro)
    }

    //fun todosLivros() = db.todosLivros()

    fun procuraLivro(query: String?) = livroDao.procuraLivro(query)

}