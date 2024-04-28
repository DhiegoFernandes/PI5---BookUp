package com.example.piandroid.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

//Instancia e retorna view model
class ViewModelFactory (val app: Application, private val livroRepository: LivroRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LivroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LivroViewModel(app, livroRepository) as T
        }
        throw IllegalArgumentException("Classe de viewmodel desconhecida")
    }
}