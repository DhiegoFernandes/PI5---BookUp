package com.example.piandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.piandroid.R
import com.example.piandroid.databinding.FragmentDicasDeLeituraBinding

class DicasDeLeitura : Fragment() {

    private var _binding: FragmentDicasDeLeituraBinding? = null
    private val binding get() = _binding!!

    private val categoriaConteudos = mapOf(
        "Categoria 1" to "Conteúdo para a Categoria 1",
        "Categoria 2" to "Conteúdo para a Categoria 2"
        // Adicione mais categorias e conteúdos conforme necessário
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDicasDeLeituraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaListeners()

        binding.button.setOnClickListener {
            atualizarConteudoCategoria("Categoria 1")
        }

        binding.button2.setOnClickListener {
            atualizarConteudoCategoria("Categoria 2")
        }


    }

    private fun atualizarConteudoCategoria(categoria: String) {
        val conteudo = categoriaConteudos[categoria]
        conteudo?.let {
            // Atualize os TextViews ou outros componentes do layout com o conteúdo da categoria
            binding.textView.text = it // Exemplo: atualizar um TextView com o conteúdo
        } ?: run {
            // exibir uma mensagem de erro se a categoria não for encontrada
            Toast.makeText(context, "Categoria não encontrada", Toast.LENGTH_SHORT).show()
        }
    }


    private fun iniciaListeners(){
        binding.btnYouGlish.setOnClickListener {
            findNavController().navigate(R.id.action_dicasDeLeitura_to_youGlish)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}