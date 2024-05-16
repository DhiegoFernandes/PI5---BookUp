package com.example.piandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.controller.LivroAdapter
import com.example.piandroid.databinding.FragmentPrincipalBinding
import com.example.piandroid.model.Livro
import com.example.piandroid.view.LivroViewModel


class Principal : Fragment() {

    private var _binding: FragmentPrincipalBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrincipalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarListeners()
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





    private fun iniciarListeners(){
        binding.btnCadastrarLivro.setOnClickListener{
            val livro = Livro(nome = "", paginas = 0, paginasLidas = 0)

            val action = LivroListFragmentDirections.actionGlobalCadastroLivro(livro)
            findNavController().navigate(action)
        }
        binding.btnMeusLivros.setOnClickListener{
            findNavController().navigate(R.id.action_principal_to_livros)
        }
        binding.btnDicasDeLeitura.setOnClickListener {
            findNavController().navigate(R.id.action_global_dicasDeLeitura)
        }
        binding.btnFragLivros.setOnClickListener{
            findNavController().navigate(R.id.action_principal_to_livros)
        }


        // Deixa o icone Home Highlited
        val menu = binding.bottomNavigationViewPrincipal.menu
        val homeItem = menu.findItem(R.id.btnHome)
        homeItem.isChecked = true

        //Bottom Navigation Principal
        binding.bottomNavigationViewPrincipal.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnVoltar ->{
                    //Fecha app
                    activity?.finish()
                    true
                }
                R.id.btnHome -> {
                    //Não faz nada
                    true
                }
                R.id.btnLivros -> {
                    //Vai pro fragment Livros
                    findNavController().navigate(R.id.action_principal_to_livros)
                    true
                }
                else -> false
            }
        }
    }



}