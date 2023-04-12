package com.example.mytimetodo.broadCastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if ((Intent.ACTION_BOOT_COMPLETED) == intent!!.action) {
            // reset all alarms
        } else {
            // perform your scheduled task here (eg. send alarm notification)
        }


    }
}