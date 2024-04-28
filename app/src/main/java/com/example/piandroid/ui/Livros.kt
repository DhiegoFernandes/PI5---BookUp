package com.example.piandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentLivrosBinding
import com.example.piandroid.databinding.FragmentPrincipalBinding
import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro
import com.example.piandroid.model.LivroDao
import com.example.piandroid.view.LivroRepository
import com.example.piandroid.view.LivroViewModel
import com.example.piandroid.view.LivroViewModelFactory


class Livros : Fragment() {

    private var _binding: FragmentLivrosBinding? = null
    private val binding get() = _binding!!
    private lateinit var livroViewModel: LivroViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLivrosBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciarViewModel()
        iniciaListeners()


    }





    private fun iniciarViewModel() {
        val livroRepository = LivroRepository(AppDatabase(requireContext()))
        val viewModelProviderFactory = LivroViewModelFactory(requireActivity().application, livroRepository)
        livroViewModel = ViewModelProvider(this, viewModelProviderFactory).get(LivroViewModel::class.java)
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


//    private fun iniciaRecyclerView(livroList: List<Livro>){
//        livroAdapter = LivroAdapter(livroList)
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.setHasFixedSize(true)
//        binding.recyclerView.adapter = livroAdapter
//    }


}