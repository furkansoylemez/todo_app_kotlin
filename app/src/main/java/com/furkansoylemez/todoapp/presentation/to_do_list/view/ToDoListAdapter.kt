package com.furkansoylemez.todoapp.presentation.to_do_list.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.furkansoylemez.todoapp.R
import com.furkansoylemez.todoapp.databinding.ItemToDoTaskBinding
import com.furkansoylemez.todoapp.domain.model.ToDoTask

class ToDoListAdapter(private val onCheckClick: (ToDoTask) -> Unit) :
    ListAdapter<ToDoTask, ToDoListAdapter.ToDoTaskViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoTaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemToDoTaskBinding.inflate(layoutInflater, parent, false)
        return ToDoTaskViewHolder(binding, onCheckClick)
    }

    override fun onBindViewHolder(holder: ToDoTaskViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }

    class ToDoTaskViewHolder(
        private val binding: ItemToDoTaskBinding,
        private val onCheckClick: (ToDoTask) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: ToDoTask) {
            binding.taskTitle.text = task.title
            binding.taskDescription.text = task.description
            binding.taskStatus.setImageResource(
                if (task.isCompleted) R.drawable.ic_check_circle_24
                else R.drawable.ic_check_circle_outline_24
            )
            binding.taskStatus.setOnClickListener {
                onCheckClick(task)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<ToDoTask>() {
        override fun areItemsTheSame(oldItem: ToDoTask, newItem: ToDoTask): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDoTask, newItem: ToDoTask): Boolean {
            return oldItem == newItem
        }
    }
}