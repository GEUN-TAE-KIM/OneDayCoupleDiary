package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.ItemCalendarBinding
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.WriteUtils
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
        private val binding: ItemCalendarBinding,
        private val onItemListener: OnItemListener,
        private val days: ArrayList<LocalDate?>
    ) :

        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(date: String) {
            binding.dayText.text = date
        }

        override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, days[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val binding =
            ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onItemListener, dayList)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        // 日を入る
        val day: LocalDate? = dayList[position]

        if (day == null) {
            holder.bind(date = "")

        } else {
            //該当する日を入る
            holder.bind(date = day.dayOfMonth.toString())

            if (day == CalendarUtils.selectedDate) {
                holder.itemView.setBackgroundResource(R.color.pink_300)
            }

        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    fun update(dayList: ArrayList<LocalDate?>) {
        this.dayList = dayList
        notifyDataSetChanged()
        //notifyItemRangeChanged(0,40)

    }

    fun deco(color: Color) {


    }

}