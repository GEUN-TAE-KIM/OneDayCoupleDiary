package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.R
import jp.co.archive_asia.onedaycouplediary.databinding.ItemCalendarBinding
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.dateToString
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarAdapter(
    private var dayList: ArrayList<LocalDate?>,
    private var diaryList: List<Diary>? = null,
    private val onItemListener: OnItemListener
) :
    RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>() {

    private lateinit var binding: ItemCalendarBinding

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
            binding.executePendingBindings()
        }

        /*fun bind(date: String, diary: Diary?) {
            binding.dayText.text = date
            binding.write = diary
            binding.executePendingBindings()
        }*/

        override fun onClick(view: View) {
            onItemListener.onItemClick(adapterPosition, days[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = ItemCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onItemListener, dayList)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        /* binding.write = writeList[position]
        val yyyyMMdd = day?.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        val write = writeList?.find {
            dateFormat.format(it.date) == yyyyMMdd
        }*/

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

    fun update(dayList: ArrayList<LocalDate?>, diaryList: List<Diary>? = null) {
        this.dayList = dayList
        this.diaryList = diaryList
        notifyDataSetChanged()
        //notifyItemRangeChanged(0,40)

    }

    fun changeSelectedCell() {
        notifyDataSetChanged()
    }
}

