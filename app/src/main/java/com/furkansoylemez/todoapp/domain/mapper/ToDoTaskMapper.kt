package com.furkansoylemez.todoapp.domain.mapper

import com.furkansoylemez.todoapp.data.model.ToDoItem
import com.furkansoylemez.todoapp.domain.model.ToDoTask

object ToDoTaskMapper {

    fun toDomainModel(dataModel: ToDoItem): ToDoTask =
        ToDoTask(
            id = dataModel.id,
            title = dataModel.title,
            description = dataModel.description,
            isCompleted = dataModel.isCompleted
        )

    fun toDataModel(domainModel: ToDoTask): ToDoItem =
        ToDoItem(
            id = domainModel.id,
            title = domainModel.title,
            description = domainModel.description,
            isCompleted = domainModel.isCompleted
        )
}