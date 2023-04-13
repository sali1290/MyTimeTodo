package com.example.mytimetodo.utility.alarmScheduler

import com.example.domain.model.Work

interface AlarmScheduler {

    fun schedule(work: Work)

    fun cancel(work: Work)

}