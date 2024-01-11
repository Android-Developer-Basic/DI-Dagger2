package ru.otus.di.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.otus.di.R
import ru.otus.di.domain.data.Employee


class EmployeeAdapter(private val onClick: (Int) -> Unit) : ListAdapter<Employee, EmployeeAdapter.Holder>(EmployeeDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.viewholder_employee, parent, false)
    )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class Holder(itemView: View) : ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val salary: TextView = itemView.findViewById(R.id.salary)

        private var id: Int = -1

        init {
            itemView.setOnClickListener {
                onClick(id)
            }
        }

        fun bind(employee: Employee) {
            id = employee.id
            name.text = employee.name
            salary.text = employee.salary.toString()
        }
    }
}

object EmployeeDiffCallback : DiffUtil.ItemCallback<Employee>() {
    override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
        return oldItem == newItem
    }
}