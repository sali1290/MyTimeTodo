package com.example.mytimetodo.app

import android.app.Application
import com.example.mytimetodo.utility.createNotificationChannel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyTimeTodoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
    }

}