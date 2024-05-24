package com.example.piandroid.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.piandroid.R
import com.example.piandroid.controller.LivroAdapter
import com.example.piandroid.databinding.FragmentPopUpAtualizaBinding
import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro
import com.example.piandroid.view.LivroRepository
import com.example.piandroid.view.LivroViewModel
import com.example.piandroid.view.LivroViewModelFactory
import com.google.android.material.snackbar.Snackbar


class PopUpFragmentAtualiza : DialogFragment() {

    private var _binding: FragmentPopUpAtualizaBinding? = null
    private val binding get() = _binding!!
    private lateinit var livroViewModel: LivroViewModel
    private val args: PopUpFragmentAtualizaArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopUpAtualizaBinding.inflate(inflater, container, false)

        val dao = AppDatabase.getDatabase(requireContext()).livroDao()
        val repository = LivroRepository(dao)
        val factory = LivroViewModelFactory(repository)
        livroViewModel = ViewModelProvider(this, factory).get(LivroViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val livroinfo = args.livro
        binding.txtNomeLivroAtualiza.setText("${livroinfo.nome}")
        binding.editPaginasQueLi.setHint("Páginas que li (${livroinfo.paginasLidas})")
        binding.editNomeLivro.setText(livroinfo.nome)

        binding.btnAtualizaPaginas.setOnClickListener {
            val livroId = livroinfo.id
            val nome = binding.editNomeLivro.text.toString()
            val paginas = livroinfo.paginas
            val paginasLidas = binding.editPaginasQueLi.text.toString()
            val favorito = livroinfo.favorito


            if (paginasLidas.isNotEmpty() && nome.isNotEmpty()){
                if (paginasLidas.toInt() >= 1) {
                    if (paginasLidas.toInt() <= paginas!!) {
                        val livroAtualizado = Livro(livroId, nome, paginas, paginasLidas.toInt(), favorito)
                        livroViewModel.atualizarLivro(livroAtualizado)

                        Toast.makeText(context, "Livro atualizado", Toast.LENGTH_SHORT).show()
                        //UPDATE
                        findNavController().popBackStack()
                        findNavController().popBackStack()
                        findNavController().navigate(R.id.action_principal_to_livros)
                        dismiss()//Fecha pop up
                    }else{
                        Toast.makeText(context, "A quantidade de páginas é maior que a quantidade de páginas do livro", Toast.LENGTH_LONG).show()
                    }
                } else{
                    Toast.makeText(context, "Você deve ter lido pelo menos uma página.", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(context, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }




        }
    }

}