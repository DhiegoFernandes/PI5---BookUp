package com.example.piandroid.ui

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.piandroid.R
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

    override fun onResume() {
        super.onResume()

        updatePermissionStatus()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciaListeners()
        updatePermissionStatus()

        binding.lottieAnimationView.setMinProgress(0.0f)
        binding.lottieAnimationView.setMaxProgress(1.0f)
        binding.lottieAnimationView.repeatCount = 3
        binding.lottieAnimationView.repeatMode = LottieDrawable.RESTART
        binding.lottieAnimationView.playAnimation()

        binding.animacaoNotificacao.setMinProgress(0.0f)
        binding.animacaoNotificacao.setMaxProgress(1.0f)
        binding.animacaoNotificacao.repeatCount = 3
        binding.animacaoNotificacao.repeatMode = LottieDrawable.RESTART
        binding.animacaoNotificacao.playAnimation()

        binding.btnAtualizaAlarme.setOnClickListener {

            val selectedRadioButtonId = binding.radioGroupPeriodos.checkedRadioButtonId

            val periodoSelecionado = when (selectedRadioButtonId) {
                R.id.rdbManha -> "8"
                R.id.rdbTarde -> "14"
                R.id.rdbNoite -> "18"
                else -> ""
            }

            if (periodoSelecionado.isNotEmpty()) {
                livroViewModel.todosLivrosOrdPorFavoritos.observe(viewLifecycleOwner) { livrosEncontrados ->
                    if (livrosEncontrados.isNotEmpty()) {
                        //pega o primeiro livro da lista
                        val primeiroLivro = livrosEncontrados[0]

                        val hora = periodoSelecionado.toInt()
                        val minuto = "00".toInt()

                        val paginas = primeiroLivro.paginas
                        val paginasLidas = primeiroLivro.paginasLidas
                        val nomeLivro = primeiroLivro.nome ?: "Livro"
                        val titulo = "Chegou a hora de ler ${primeiroLivro.nome}!"
                        val mensagem = "Não esqueça de atualizar as páginas que você já leu! ($paginasLidas/$paginas)"

                        marcarNotificacaoDiaria(nomeLivro, mensagem, hora, minuto)

                        AlertDialog.Builder(context)
                            .setTitle("Horário de notificações alterado!")
                            .setMessage(
                                "Próxima Notificação: " + titulo +
                                        "\nMensagem: " + mensagem +
                                        "\nHorário: " + hora + ":" + "00"
                            )
                            .setPositiveButton("Ok!") { _, _ -> }
                            .show()

                        //Toast.makeText(context, "ID: $idLivro Nome: $nomeLivro P:$paginasLidas R:$paginaLivro" , Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Cadastre um livro antes de receber notificações personalizadas!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Caso nenhum período seja selecionado
                Toast.makeText(context, "Escolha um período para receber notificações personalizadas!", Toast.LENGTH_SHORT).show()
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

    private fun marcarNotificacaoDiaria(title: String, message: String, hora: Int, minuto: Int) {
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

    private fun pedirPermissao(){
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        } else {
            Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
                }
            }
        }
        startActivity(intent)
    }

    private fun updatePermissionStatus() {
        val notificationManager = NotificationManagerCompat.from(requireContext())
        val hasNotificationPermission = notificationManager.areNotificationsEnabled()

        if (hasNotificationPermission) {
            binding.txtPermitiu.text = "Concedida."
            binding.switch1.isChecked = true
        } else {
            binding.txtPermitiu.text = "Negada."
            binding.switch1.isChecked = false
        }
    }

    private fun iniciaListeners() {
        //Pedir permissoes

        binding.switch1.setOnClickListener{
            pedirPermissao()
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}