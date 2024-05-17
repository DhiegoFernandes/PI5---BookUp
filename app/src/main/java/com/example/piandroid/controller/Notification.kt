package com.example.piandroid.controller

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.example.piandroid.R

const val notificatioID = 1
const val channelID = "canal1"
const val tituloExtra = "tituloExtra"
const val mensagemExtra = "mensagemExtra"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.bookup_branco)
            .setContentTitle(intent.getStringExtra(tituloExtra))
            .setContentText(intent.getStringExtra(mensagemExtra))
            .build()

        //manager envia notificação
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificatioID, notification)
    }
}