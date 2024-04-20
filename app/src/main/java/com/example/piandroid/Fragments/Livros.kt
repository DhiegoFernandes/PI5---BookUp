package com.example.piandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentLivrosBinding


class Livros : Fragment() {
    private var _binding: FragmentLivrosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLivrosBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFragPrincipal.setOnClickListener {
            findNavController().navigate(R.id.action_livros_to_principal)
        }
        binding.btnAbreLivro.setOnClickListener {
            findNavController().navigate(R.id.action_livros_to_livro)
        }

        //Bottom Navigation Livros
        binding.bottomNavigationViewLivros.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnVoltar ->{
                    findNavController().popBackStack()
                    true
                }
                R.id.btnHome -> {
                    findNavController().navigate(R.id.action_livros_to_principal)
                    true
                }
                R.id.btnLivros -> {
                    //Não faz nada
                    true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}