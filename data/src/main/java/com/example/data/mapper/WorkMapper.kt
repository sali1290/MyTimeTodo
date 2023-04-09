package com.example.data.mapper

import com.example.data.model.WorkModel
import com.example.domain.model.Work
import javax.inject.Inject

class WorkMapper @Inject constructor() {

    fun toWork(workModel: WorkModel): Work {
        return Work(
            id = workModel.id,
            title = workModel.title,
            body = workModel.body,
            time = workModel.time,
            color = workModel.color,
        )
    }

    fun toWorkModel(work: Work): WorkModel {
        return WorkModel(
            id = work.id,
            title = work.title,
            body = work.body,
            time = work.time,
            color = work.color,
        )
    }


}