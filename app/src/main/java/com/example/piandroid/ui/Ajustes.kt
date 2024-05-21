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

        createNotificationChannel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  ADICIONAR O GERENCIAMENTO DE HORARIO DE NOTIFICACOES

        iniciaListeners()




        // TODO: pegar o livro[0] prioridade e criar a notificacao diaria baseada nele
        binding.btnAtualizaAlarme.setOnClickListener {
            livroViewModel.todosLivrosOrdPorFavoritos.observe(viewLifecycleOwner) { livrosEncontrados ->
                if (livrosEncontrados.isNotEmpty()) {
                    //pegar o primeiro livro da lista
                    val primeiroLivro = livrosEncontrados[0]

//                    val idLivro = primeiroLivro.id
//                    val nomeLivro = primeiroLivro.nome
//                    val paginaLivro = primeiroLivro.paginas
//                    val paginasLidas = primeiroLivro.paginasLidas
//                    val favorito = primeiroLivro.favorito

                    val hora = binding.editHora.text.toString().toInt()
                    val minuto = binding.editMinuto.text.toString().toInt()

                    val nomeLivro = primeiroLivro.nome ?: "Livro"
                    scheduleDailyNotification(nomeLivro, "Continue lendo $nomeLivro!", hora, minuto)
                    Toast.makeText(context, "Notificação agendada para $nomeLivro", Toast.LENGTH_SHORT).show()

                    //Toast.makeText(context, "ID: $idLivro Nome: $nomeLivro P:$paginasLidas R:$paginaLivro" , Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Nenhum livro encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Canal de Notificação"
            val descriptionText = "Canal para notificações diárias"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleDailyNotification(title: String, message: String, hora: Int, minuto: Int) {
        val intent = Intent(requireContext(), Notification::class.java).apply {
            putExtra(tituloExtra, title)
            putExtra(mensagemExtra, message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificatioID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hora) // Define a hora que deseja agendar a notificação
            set(Calendar.MINUTE, minuto)
            set(Calendar.SECOND, 0)
        }

        // Se a hora já passou, agende para o próximo dia
        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
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