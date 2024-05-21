package com.example.piandroid.ui

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.piandroid.controller.Notification
import com.example.piandroid.controller.channelID
import com.example.piandroid.controller.mensagemExtra
import com.example.piandroid.controller.notificatioID
import com.example.piandroid.controller.tituloExtra
import com.example.piandroid.databinding.FragmentAjustesBinding
import com.example.piandroid.model.AppDatabase
import com.example.piandroid.model.Livro
import com.example.piandroid.view.LivroRepository
import com.example.piandroid.view.LivroViewModel
import com.example.piandroid.view.LivroViewModelFactory
import java.util.Calendar
import java.util.Date


class Ajustes : Fragment() {

    private var _binding: FragmentAjustesBinding? = null
    private val binding get() = _binding!!
    private lateinit var livroViewModel: LivroViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAjustesBinding.inflate(inflater, container, false)

        val dao = AppDatabase.getDatabase(requireContext()).livroDao()
        val repository = LivroRepository(dao)
        val factory = LivroViewModelFactory(repository)
        livroViewModel = ViewModelProvider(this, factory).get(LivroViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  ADICIONAR O GERENCIAMENTO DE HORARIO DE NOTIFICACOES

        iniciaListeners()

        //adicionar nova variavel Prioridade, e entao pegar o livro prioridade e criar a notificacao diaria baseada nele
        binding.btnAtualizaAlarme.setOnClickListener {
            val query = binding.ediTeste.text.toString()
            Toast.makeText(context, "$query", Toast.LENGTH_SHORT).show()
            livroViewModel.procuraLivro(query).observe(viewLifecycleOwner) { livrosEncontrados ->
                if (livrosEncontrados.isNotEmpty()) {
                    //pegar o primeiro livro da lista
                    val primeiroLivro = livrosEncontrados[0]
                    val idLivro = primeiroLivro.id
                    val nomeLivro = primeiroLivro.nome
                    val paginaLivro = primeiroLivro.paginas
                    val paginasLidas = primeiroLivro.paginasLidas
                    Toast.makeText(context, "ID: $idLivro Nome: $nomeLivro P:$paginasLidas R:$paginaLivro" , Toast.LENGTH_SHORT).show()
                    binding.teste.setText(primeiroLivro.toString())
                } else {
                    Toast.makeText(context, "Nenhum livro encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }


    private fun iniciaListeners(){
        //Pedir permissoes
        binding.btnPermissao.setOnClickListener {
            val intent = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}