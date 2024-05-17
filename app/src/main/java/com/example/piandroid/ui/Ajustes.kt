package com.example.piandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentAjustesBinding


class Ajustes : Fragment() {

    private var _binding: FragmentAjustesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAjustesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: ADICIONAR O GERENCIAMENTO DE HORARIO DE NOTIFICACOES
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}