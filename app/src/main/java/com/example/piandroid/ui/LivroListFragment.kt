package com.example.piandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piandroid.R
import com.example.piandroid.controller.LivroAdapter
import com.example.piandroid.databinding.FragmentLivroListBinding
import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro
import com.example.piandroid.view.LivroRepository
import com.example.piandroid.view.LivroViewModel
import com.example.piandroid.view.LivroViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LivroListFragment : Fragment() {

    private var _binding: FragmentLivroListBinding? = null
    private val binding get() = _binding!!
    private lateinit var livroViewModel: LivroViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLivroListBinding.inflate(inflater, container, false)
        val dao = AppDatabase.getDatabase(requireContext()).livroDao()
        val repository = LivroRepository(dao)
        val factory = LivroViewModelFactory(repository)
        livroViewModel = ViewModelProvider(this, factory).get(LivroViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciaListeners()
        val adapter = LivroAdapter(
            onEdit = { livro ->
                // Ação quando o botão de editar é pressionado
                val action = LivroListFragmentDirections.actionGlobalCadastroLivro(livro)
                findNavController().navigate(action)
            },
            onDelete = { livro ->
                // Ação quando o botão de deletar é pressionado
                Snackbar.make(binding.root, "Gostaria de Deletar o Livro?", Snackbar.LENGTH_LONG)
                    .setAction("Confirmar") { deleteUsuario(livro) }
                    .show()
            }
        )

        binding.recyclerViewLivros.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        livroViewModel.todosLivros.observe(viewLifecycleOwner) { livros ->
            adapter.submitList(livros)
        }

        binding.floatingBtnAddLivro.setOnClickListener {

            val livro = Livro(nome = "", paginas = 0, paginasLidas = 0)

            val action = LivroListFragmentDirections.actionGlobalCadastroLivro(livro)
            findNavController().navigate(action)

        }
    }


    private fun deleteUsuario(livro: Livro) {
        livroViewModel.deletarLivro(livro)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun iniciaListeners() {

        //Bottom Navigation Livros
        binding.bottomNavigationViewLivros.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnVoltar -> {
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


}