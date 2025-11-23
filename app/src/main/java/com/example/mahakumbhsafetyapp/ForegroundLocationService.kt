package com.example.mahakumbhsafetyapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ForegroundLocationService : Service() {

    companion object {
        const val CHANNEL_ID = "foreground_location_channel"
        const val NOTIF_ID = 1101
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("SimhSaathi — Tracking")
            .setContentText("Location tracking active")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // safe platform icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        startForeground(NOTIF_ID, notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Location", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Foreground location tracking"
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
