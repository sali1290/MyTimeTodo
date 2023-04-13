package com.example.mytimetodo.utility.alarmScheduler

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.domain.model.Work
import com.example.mytimetodo.broadCastReceiver.AlarmReceiver

class AndroidAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    override fun schedule(work: Work) {

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("WORK_TITLE", work.title)
        }

        // calculate time
        var time = (work.time!!.time - (work.time!!.time % 60000))
        if (System.currentTimeMillis() > time) {
            // setting time as AM and PM
            time += (1000 * 60 * 60 * 24)
        }


        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            PendingIntent.getBroadcast(
                context,
                work.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(work: Work) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                work.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}