package com.example.mytimetodo.broadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.domain.model.Work
import com.example.mytimetodo.notification.WorkNotificationService


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        val work = intent?.getSerializableExtra("WORK") as Work

        val service = WorkNotificationService(context)
        service.showNotification(work)
//        val workTitle = intent.getStringExtra("WORK_TITLE") ?: return
//        val workBody = intent.getStringExtra("WORK_BODY") ?: return
//        val workColor = intent.getStringExtra("WORK_COLOR") ?: return
//        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        }
//        // setting default ringtone
//        val ringtone = RingtoneManager.getRingtone(context, alarmUri)
//        // play ringtone
//        ringtone.play()

    }
}