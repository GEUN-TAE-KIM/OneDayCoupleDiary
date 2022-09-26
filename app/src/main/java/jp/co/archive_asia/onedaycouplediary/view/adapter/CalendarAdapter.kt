package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import java.time.LocalDate

class CalendarAdapter(
    private val dayList: ArrayList<LocalDate?>,
    private val onItemListener: OnItemListener
) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: LocalDate?)
    }

    class ItemViewHolder(
        itemView: View,
        onItemListener: OnItemListener,
        days: ArrayList<LocalDate?>
    ) :

        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val dayList: ArrayList<LocalDate?>
        val dayText: TextView
        private val onItemListener: OnItemListener

        override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, dayList[adapterPosition])
        }

        init {
            dayText = itemView.findViewById(R.id.dayText)
            this.onItemListener = onItemListener
            itemView.setOnClickListener(this)
            this.dayList = days

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar, parent, false)

        return ItemViewHolder(view, onItemListener, dayList)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // 日を入る
        val day: LocalDate? = dayList[position]

        if (day == null) {
            holder.dayText.text = ""
        } else {
            //該当する日を入る
            holder.dayText.text = day.dayOfMonth.toString()

            // 現在の日を色塗り
            if (day == CalendarUtils.selectedDate) {
                holder.itemView.setBackgroundResource(R.color.pink_300)
            }
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}