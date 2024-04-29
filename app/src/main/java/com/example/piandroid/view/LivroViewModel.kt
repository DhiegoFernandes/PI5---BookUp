package com.example.piandroid.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.piandroid.model.Livro
import kotlinx.coroutines.launch

class LivroViewModel(private val repository: LivroRepository): ViewModel() {

    val todosLivros: LiveData<List<Livro>> = repository.todosLivros

    fun inserirLivro(livro: Livro) = viewModelScope.launch {
        repository.inserirLivro(livro)
        Log.d("LivroViewModel", "Livro cadastrado: $livro")
    }
    fun atualizarLivro(livro: Livro) = viewModelScope.launch {
        repository.atualizarLivro(livro)
        Log.d("LivroViewModel", "Livro atualizado: $livro")
    }
    fun deletarLivro(livro: Livro) = viewModelScope.launch {
        repository.deletarLivro(livro)
    }

    //fun todosLivros() = livroRepository.todosLivros()
    fun procuraLivro(query: String?) = repository.procuraLivro(query)

}