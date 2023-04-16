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

    }
}