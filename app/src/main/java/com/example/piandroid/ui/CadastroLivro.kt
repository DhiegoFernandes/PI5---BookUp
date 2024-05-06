package com.example.piandroid.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentCadastroLivroBinding
import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro
import com.example.piandroid.view.LivroRepository
import com.example.piandroid.view.LivroViewModel
import com.example.piandroid.view.LivroViewModelFactory
import com.google.android.material.snackbar.Snackbar


class CadastroLivro : Fragment() {

    private var _binding: FragmentCadastroLivroBinding? = null
    private val binding get() = _binding!!
    private lateinit var livroViewModel: LivroViewModel
    private val args: CadastroLivroArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroLivroBinding.inflate(inflater, container, false)
        val dao = AppDatabase.getDatabase(requireContext()).livroDao()
        val repository = LivroRepository(dao)
        val factory = LivroViewModelFactory(repository)
        livroViewModel = ViewModelProvider(this, factory).get(LivroViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val livroinfo = args.livro //INFORMACOES DO LIVRO
        loadLivro(livroinfo) //Carrega o livro nos campos de texto
        //Se livro não foi cadastrado
        if (binding.editNomeLivro.text.toString().isEmpty()) {

            Snackbar.make(binding.root, "Livro NOVO", Snackbar.LENGTH_LONG).show()

            //Muda parametros do layout
            binding.txtPaginasLidas.visibility = View.INVISIBLE
            binding.editPaginasLidas.visibility = View.INVISIBLE


            binding.btnCadastrarLivro.setOnClickListener {

                val nome = binding.editNomeLivro.text.toString()
                val paginas = binding.editPaginas.text.toString()
                val paginasLidas = 0 //Por padrão começa em 0

                if (nome.isNotEmpty() && paginas.isNotEmpty()) {
                    val livro =
                        Livro(nome = nome, paginas = paginas.toInt(), paginasLidas = paginasLidas)
                    livroViewModel.inserirLivro(livro)
                    Snackbar.make(binding.root, "Livro Cadastrado", Snackbar.LENGTH_LONG).show()
                    // Navegar de volta à lista após a inserção
                    findNavController().popBackStack()
                } else {
                    Snackbar.make(binding.root, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        } else { //Atualiza
            Snackbar.make(binding.root, "Livro Já CADASTRADO", Snackbar.LENGTH_LONG).show()

            //Muda parametros do layout
            binding.btnCadastrarLivro.setText("Atualizar")
            //Não deixa clicar
            binding.editPaginas.isFocusable = false
            binding.editPaginas.isFocusableInTouchMode = false
            binding.editPaginas.isClickable  = false
            //Muda cor
            binding.editPaginas.setBackgroundColor(Color.parseColor("#393939"))
            binding.editPaginas.setTextColor(Color.parseColor("#fefefe"))

            binding.btnCadastrarLivro.setOnClickListener {

                val livroId = livroinfo.id
                val nome = binding.editNomeLivro.text.toString()
                val paginas = binding.editPaginas.text.toString()
                val paginasLidas = binding.editPaginasLidas.text.toString()

                if (nome.isNotEmpty() && paginas.isNotEmpty() && paginasLidas.isNotEmpty()) {
                    val paginasInt = paginas.toIntOrNull()
                    val paginasLidasInt = paginasLidas.toIntOrNull()

                    if (paginasInt != null && paginasLidasInt != null) {
                        if (paginasLidasInt <= paginasInt) {
                            val livro = Livro(livroId, nome, paginasInt, paginasLidasInt)
                            livroViewModel.atualizarLivro(livro)

                            Snackbar.make(binding.root, "Livro Atualizado", Snackbar.LENGTH_LONG)
                                .show()
                            findNavController().popBackStack()
                        } else {
                            Snackbar.make(
                                binding.root,
                                "Páginas Lidas não pode ser maior que a quantidade de páginas do livro",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Os campos de páginas e páginas lidas devem ser números válidos",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Snackbar.make(binding.root, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                        .show()
                }
                //teste
                Log.d(
                    "atualizaLivro",
                    "Livro atualizado: Livro:" + livroId.toString() + " " + nome + " " + "lidas:" + paginasLidas.toInt() + " tot:" + paginas
                )

            }

        }


    }

    private fun loadLivro(livro: Livro) {
        binding.editNomeLivro.setText(livro.nome)
        binding.editPaginas.setText(livro.paginas.toString())
        binding.editPaginasLidas.setText(livro.paginasLidas.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}