package com.example.piandroid.view

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
//Instancia e retorna view model
class LivroViewModelFactory(private val repository: LivroRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LivroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LivroViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}