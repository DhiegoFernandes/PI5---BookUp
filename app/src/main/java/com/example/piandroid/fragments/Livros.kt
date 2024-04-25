package com.example.piandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piandroid.R
import com.example.piandroid.controller.LivroAdapter
import com.example.piandroid.databinding.FragmentLivrosBinding
import com.example.piandroid.model.Livro


class Livros : Fragment() {
    private var _binding: FragmentLivrosBinding? = null
    private val binding get() = _binding!!

    private lateinit var livroAdapter: LivroAdapter

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

        iniciaListeners()
        iniciaRecyclerView(livrosProvisorios())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun iniciaRecyclerView(livroList: List<Livro>){
        livroAdapter = LivroAdapter(livroList)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = livroAdapter
    }




    private fun iniciaListeners(){
        binding.recyclerView.setOnClickListener {
            //Abre livro ao clicar no recycler view ##### ENTRAR POR ID ######
            findNavController().navigate(R.id.action_livros_to_principal)

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

    private fun livrosProvisorios() = listOf<Livro>(
        Livro(0, "harry potter e o calice de fogo", 1, 1),
        Livro(1, "livro 2", 2 , 1),
        Livro(2, "livro 3", 3, 1),
        Livro(3, "livro 4", 4 , 1),
        Livro(4, "livro 5", 5, 1),

    )
}