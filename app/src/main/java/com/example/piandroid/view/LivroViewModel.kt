package com.example.piandroid.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.piandroid.model.Livro
import kotlinx.coroutines.launch

class LivroViewModel(app: Application, private val livroRepository: LivroRepository): AndroidViewModel(app) {

    fun inserirLivro(livro: Livro) = viewModelScope.launch {
        livroRepository.inserirLivro(livro)
    }
    fun atualizaLivro(livro: Livro) = viewModelScope.launch {
        livroRepository.atualizarLivro(livro)
    }
    fun deletaLivro(livro: Livro) = viewModelScope.launch {
        livroRepository.deletarLivro(livro)
    }

    fun todosLivros() = livroRepository.todosLivros()
    fun procuraLivro(query: String?) = livroRepository.procuraLivro(query)

}