package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.databinding.ItemCalendarBinding
import jp.co.archive_asia.onedaycouplediary.model.CalendarData

class CalendarAdapter : ListAdapter<CalendarData, CalendarAdapter.ItemViewHolder>(
    CalendarAdapterDiffCallback()
) {
    class ItemViewHolder private constructor(
        private val binding: ItemCalendarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val dateNumber = binding.dayText

        fun bind(item: CalendarData) {
            binding.calendardata = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCalendarBinding.inflate(
                    layoutInflater, parent, false
                )
                return ItemViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item.dayOfMonth == 0) {
            holder.dateNumber.visibility = View.GONE
        }
        holder.bind(item)
    }
}

class CalendarAdapterDiffCallback : DiffUtil.ItemCallback<CalendarData>() {
    override fun areItemsTheSame(oldItem: CalendarData, newItem: CalendarData): Boolean {
        return oldItem.dayOfMonth == newItem.dayOfMonth
    }

    override fun areContentsTheSame(oldItem: CalendarData, newItem: CalendarData): Boolean {
        return oldItem == newItem
    }

}



