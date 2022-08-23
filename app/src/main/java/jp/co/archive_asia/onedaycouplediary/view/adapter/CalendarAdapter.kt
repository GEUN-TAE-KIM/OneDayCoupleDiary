package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import java.time.LocalDate

class CalendarAdapter(private val dayList: ArrayList<LocalDate?>) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dayText: TextView = itemView.findViewById(R.id.dayText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // 日を入る
        var day = dayList[holder.adapterPosition]

        if (day == null) {
            holder.dayText.text = ""
        } else {
            //該当する日を入る
            holder.dayText.text = day.dayOfMonth.toString()

            // 現在の日を色塗り
            if (day == LocalDate.now())
                holder.itemView.setBackgroundColor(Color.GRAY)
        }

        // 日、クリックイベント
        holder.itemView.setOnClickListener {

            Log.d("click", "day click")

        }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}