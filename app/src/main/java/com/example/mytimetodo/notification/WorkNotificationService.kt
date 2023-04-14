package com.example.mytimetodo.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.domain.model.Work
import com.example.mytimetodo.R
import com.example.mytimetodo.activity.MainActivity

class WorkNotificationService(
    private val context: Context
) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(work: Work) {
        //create pending intent for clicking on notification
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context, 0, activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle(work.title)
            setContentText(work.body)
            color = work.color.toInt()
            setContentIntent(activityPendingIntent)
        }.build()

        notificationManager.notify(
            work.id, notification
        )
    }

    companion object {
        const val channelId = "WORK_NOTIFICATIONS"
    }
}