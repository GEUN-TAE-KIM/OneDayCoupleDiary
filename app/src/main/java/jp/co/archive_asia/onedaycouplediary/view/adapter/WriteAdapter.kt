package jp.co.archive_asia.onedaycouplediary.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import jp.co.archive_asia.onedaycouplediary.databinding.ItemDiaryListBinding
import jp.co.archive_asia.onedaycouplediary.model.Diary
import jp.co.archive_asia.onedaycouplediary.view.fragment.CalendarFragmentDirections
import jp.co.archive_asia.onedaycouplediary.view.util.CalendarUtils
import jp.co.archive_asia.onedaycouplediary.view.util.dateToString
import jp.co.archive_asia.onedaycouplediary.viewmodel.CalendarViewModel
import java.util.Date

class WriteAdapter(private val calendarViewModel: CalendarViewModel) :
    RecyclerView.Adapter<WriteAdapter.MyViewHolder>() {

    private var monthlyDiaryList = emptyList<Diary>()
    private var currentDay = CalendarUtils.selectedDate.toString()
    private val dailyDiaryList: List<Diary>
        get() {
            return monthlyDiaryList.filter {
                val date = Date(it.date)
                date.dateToString("yyyy-MM-dd") == currentDay
            }
        }

    class MyViewHolder(private var binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var diary: Diary
        lateinit var calendarViewModel: CalendarViewModel

        fun bind(diary: Diary, calendarViewModel: CalendarViewModel) {
            binding.diary = diary
            this.calendarViewModel = calendarViewModel
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypde: Int): MyViewHolder {
        val binding =
            ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dailyDiaryList[position], calendarViewModel)
        holder.itemView.setOnClickListener {
            val action = CalendarFragmentDirections.actionCalendarFragmentToDiaryFragment(
                monthlyDiaryList[position]
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dailyDiaryList.size
    }

    fun setData(diary: List<Diary>) {
        monthlyDiaryList = diary
        notifyDataSetChanged()
    }

    fun update(current: String) {
        this.currentDay = current
        notifyDataSetChanged()
    }

}