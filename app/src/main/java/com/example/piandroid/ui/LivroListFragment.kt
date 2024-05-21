package com.example.piandroid.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch

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



  /*      binding.btnPesquisa.setOnClickListener {
            val query = binding.editPesquisa.text.toString()
            Toast.makeText(context, "$query", Toast.LENGTH_SHORT).show()
            livroViewModel.procuraLivro(query).observe(viewLifecycleOwner) { livrosEncontrados ->
                // Aqui você pode lidar com os livros encontrados
                if (livrosEncontrados.isNotEmpty()) {
                    // Por exemplo, você pode pegar o primeiro livro da lista
                    val primeiroLivro = livrosEncontrados[0]
                    // Agora você pode usar o ID do livro
                    val idDoLivro = primeiroLivro.id
                    Toast.makeText(context, "ID do livro: $idDoLivro", Toast.LENGTH_SHORT).show()
                    // Ou fazer qualquer outra coisa com os dados do livro
                    binding.txtTelaLivros.setText(primeiroLivro.toString())
                } else {
                    Toast.makeText(context, "Nenhum livro encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }*/


        val adapter = LivroAdapter(
            onEdit = { livro ->
                // TODO: POPUP ao inves de fragment 
                // Ação quando o botão de editar é pressionado
                val action = LivroListFragmentDirections.actionGlobalCadastroLivro(livro)
                findNavController().navigate(action)

            },
            onDelete = { livro ->
                // Ação quando o botão de deletar é pressionado
                Snackbar.make(binding.root, "Gostaria de Deletar o Livro?", Snackbar.LENGTH_LONG)
                    .setAction("Confirmar") { deleteUsuario(livro) }
                    .show()
            },
            onFavorite = { livro ->
                lifecycleScope.launch {//Assincrono
                    // Obtenha o livro correspondente
                    val livro = // Obtenha o livro correspondente
                        if (livro.favorito == 0) {
                            livroViewModel.marcarComoFavorito(livro)
                            Toast.makeText(context, "Livro marcado como favorito", Toast.LENGTH_SHORT).show()
                        } else {
                            livroViewModel.desmarcarComoFavorito(livro)
                            Toast.makeText(context, "Livro removido dos favoritos", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )
        //Inicia recyclerView
        binding.recyclerViewLivros.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            livroViewModel.todosLivrosOrdPorFavoritos.observe(viewLifecycleOwner) { livros ->
                adapter.submitList(livros)
            }
        }
    }


    private fun deleteUsuario(livro: Livro) {
        livroViewModel.deletarLivro(livro)
    }


    private fun iniciaListeners() {
        // Deixa o icone Home Highlited
        val menu = binding.bottomNavigationViewLivros.menu
        val livroItem = menu.findItem(R.id.btnLivros)
        livroItem.isChecked = true

        binding.recyclerViewLivros.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        //botao flutuante
        binding.floatingBtnAddLivro.setOnClickListener {

            val livro = Livro(nome = "", paginas = 0, paginasLidas = 0, favorito = 0)

            val action = LivroListFragmentDirections.actionGlobalCadastroLivro(livro)
            findNavController().navigate(action)

        }

        //Pesquisa livro assim que algo é digitado
        binding.editPesquisa.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Não é necessário implementar este método neste caso
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Chamada da função de pesquisa quando o texto é alterado
                val query = s.toString()
                livroViewModel.procuraLivro(query).observe(viewLifecycleOwner) { livros ->
                    // Atualiza a RecyclerView com os livros pesquisados
                    (binding.recyclerViewLivros.adapter as LivroAdapter).submitList(livros)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Não é necessário implementar este método neste caso
            }
        })

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}