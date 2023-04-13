package com.example.mytimetodo.broadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.widget.Toast


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val workTitle = intent?.getStringExtra("WORK_TITLE") ?: return

        Toast.makeText(context, workTitle, Toast.LENGTH_LONG).show()
        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }
        // setting default ringtone
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
        // play ringtone
        ringtone.play()
    }
}