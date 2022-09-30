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
private var dayList: ArrayList<LocalDate?>,
private val onItemListener: OnItemListener
) :
RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: LocalDate?)
    }

    class ItemViewHolder(
        itemView: View,
        private val onItemListener: OnItemListener,
        private val days: ArrayList<LocalDate?>
    ) :

        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val dayText: TextView

        override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, days[adapterPosition])
        }

        init {
            dayText = itemView.findViewById(R.id.dayText)
            itemView.setOnClickListener(this)

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

            if (day == CalendarUtils.selectedDate) {
                holder.itemView.setBackgroundResource(R.color.pink_300)
            }
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    fun update (dayList: ArrayList<LocalDate?>) {
        this.dayList = dayList
        notifyDataSetChanged()

    }
}