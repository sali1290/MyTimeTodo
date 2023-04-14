package com.example.mytimetodo.alarmScheduler

import com.example.domain.model.Work

interface AlarmScheduler {

    fun schedule(work: Work)

    fun cancel(work: Work)

}